package com.setu.splitwise.service.manager;

import com.setu.splitwise.model.persistence.Split;
import com.setu.splitwise.service.repo.SplitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SplitManager {

  @Autowired
  private SplitRepository splitRepository;

  public void save(Split split) {
    splitRepository.save(split);
  }

}
