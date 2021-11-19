package com.setu.splitwise.model.persistence;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Optional;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payment")
@SequenceGenerator(name = "payment-seq", sequenceName = "payment_id_seq", allocationSize = 1)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "payment-seq")
  @Column(name = "id")
  private Long id;

  @Column(name = "created_by")
  private Long createdBy;

  @Column(name = "debit_from")
  private Long debitFrom;

  @Column(name = "credit_to")
  private Long creditTo;

  @Column(name = "amount")
  private BigDecimal amount;

  @Column(name = "currency")
  private Currency currency;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime createdAt;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime updatedAt;

  public String getCurrencySymbol() {
    return Optional.ofNullable(this.currency).map(Currency::getSymbol).orElse(null);
  }

}
