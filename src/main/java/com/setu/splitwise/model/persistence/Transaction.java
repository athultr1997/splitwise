package com.setu.splitwise.model.persistence;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "transaction")
@SequenceGenerator(name = "transaction-seq", sequenceName = "transaction_id_seq", allocationSize = 1)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transaction extends TransactionTransient {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "transaction-seq")
  @Column(name = "id")
  private Long id;

  @Column(name = "split_id", insertable = false, updatable = false)
  private Long splitId;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "paid")
  private BigDecimal paid;

  @Column(name = "owed")
  private BigDecimal owed;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "split_id")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @JsonBackReference
  private Split split;

  public BigDecimal getAmountToSettle() {
    if (this.amountToBeSettled == null) {
      this.amountToBeSettled = paid.subtract(owed);
    }
    return this.amountToBeSettled;
  }

}
