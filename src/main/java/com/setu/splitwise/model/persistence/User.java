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
@Table(name = "user")
@SequenceGenerator(name = "user-seq", sequenceName = "user_id_seq", allocationSize = 1)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "user-seq")
  @Column
  private Long id;

  @Column
  private String username;

  @Column
  private String email;

}
