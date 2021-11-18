package com.setu.splitwise.model.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "split")
@SequenceGenerator(name = "tag-seq", sequenceName = "tag_id_seq", allocationSize = 1)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "tag-seq")
  @Column
  private Long id;



}
