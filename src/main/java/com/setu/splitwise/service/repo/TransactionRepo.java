package com.setu.splitwise.service.repo;

import com.setu.splitwise.model.persistence.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {

}
