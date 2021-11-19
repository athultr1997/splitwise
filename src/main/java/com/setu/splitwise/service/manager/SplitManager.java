package com.setu.splitwise.service.manager;

import com.setu.splitwise.model.persistence.Split;
import com.setu.splitwise.service.repo.SplitRepository;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SplitManager {

  @Autowired
  private SplitRepository splitRepository;

  public void save(Split split) {
    splitRepository.save(split);
  }

  public Split getById(Long id) {
    if (id == null) {
      return null;
    }
    return splitRepository.getById(id);
  }

  public Set<Split> findAllInvolvingUserId(Long userId) {
    return splitRepository.findAllInvolvingUserId(userId);
  }

}
