package com.setu.splitwise;

import com.setu.splitwise.model.persistence.Split;
import com.setu.splitwise.service.repo.SplitRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = SplitWiseApplication.class)
class SampleTest {

  @Autowired
  private SplitRepository splitRepository;

  @Test
  void test() {
    Logger logger = LoggerFactory.getLogger(SampleTest.class);
    logger.info("#####");
    Split split = Split.builder()
        .amount(BigDecimal.valueOf(100.0D))
        .createdAt(LocalDateTime.now())
        .build();
    splitRepository.save(split);
    Assertions.assertNotNull(splitRepository.getById(1L));
  }

}
