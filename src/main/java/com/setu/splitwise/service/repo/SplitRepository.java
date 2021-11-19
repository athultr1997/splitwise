package com.setu.splitwise.service.repo;

import com.setu.splitwise.model.persistence.Split;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SplitRepository extends JpaRepository<Split, Long> {

  @Query(value = "SELECT * FROM split INNER JOIN transaction ON transaction.split_id = split.id WHERE transaction.user_id = :user_id", nativeQuery = true)
  Set<Split> findAllInvolvingUserId(@Param("user_id") Long userId);

}
