package com.setu.splitwise.model.persistence;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Optional;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;

@Entity
@Table(name = "split")
@SequenceGenerator(name = "split-seq", sequenceName = "split_id_seq", allocationSize = 1)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Split {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "split-seq")
  @Column(name = "id")
  private Long id;

  @Column(name = "created_by")
  private Long createdBy;

  @Column(name = "title")
  private String title;

  @Column(name = "note")
  private String note;

  @Column(name = "amount")
  private BigDecimal amount;

  @Column(name = "currency")
  private Currency currency;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @JsonManagedReference
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "split", cascade = CascadeType.ALL, orphanRemoval = true)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Set<Transaction> transactions;

  @JsonManagedReference
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "split", cascade = CascadeType.ALL, orphanRemoval = true)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Set<Bill> bills;

  public void setBills(Set<Bill> bills) {
    for (Bill bill : bills) {
      bill.setSplit(this);
    }
    this.bills = bills;
  }

  public void setTransactions(Set<Transaction> transactions) {
    for (Transaction transaction : transactions) {
      transaction.setSplit(this);
    }
    this.transactions = transactions;
  }

  public Integer getNumberOfPeoplePaid() {
    return Optional.ofNullable(this.transactions)
        .filter(CollectionUtils::isNotEmpty)
        .map(transactionsCopy -> transactionsCopy.stream()
            .filter(transaction -> transaction.getPaid() != null &&
                BigDecimal.ZERO.compareTo(transaction.getPaid()) < 1)
            .count())
        .map(Long::intValue)
        .orElse(0);
  }

  public Transaction getTransactionByUserId(Long userId) {
    return Optional.ofNullable(this.transactions)
        .filter(CollectionUtils::isNotEmpty)
        .map(transactionsCopy -> transactionsCopy.stream()
            .filter(transaction -> userId.equals(transaction.getUserId()))
            .findFirst())
        .map(Optional::get)
        .orElse(null);
  }

  public String getCurrencySymbol() {
    return Optional.ofNullable(this.currency).map(Currency::getSymbol).orElse(null);
  }

}
