package com.setu.splitwise.service.repo;

import com.setu.splitwise.model.persistence.Split;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SplitRepository extends JpaRepository<Split, Long> {

}
