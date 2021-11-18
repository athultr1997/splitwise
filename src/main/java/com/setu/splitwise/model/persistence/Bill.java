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

@Entity
@Table(name = "bill")
@SequenceGenerator(name = "bill-seq", sequenceName = "bill_id_seq", allocationSize = 1)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Bill {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "bill-seq")
  @Column(name = "id")
  private Long id;

  @Column(name = "split_id", insertable = false, updatable = false)
  private Long splitId;

  @Column(name = "debit_from")
  private Long debitFrom;

  @Column(name = "credit_to")
  private Long creditTo;

  @Column(name = "amount")
  private BigDecimal amount;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "split_id")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @JsonBackReference
  private Split split;

}
