package com.setu.splitwise.service.repo;

import com.setu.splitwise.model.persistence.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsByIdIn(List<Long> userIds);

}
