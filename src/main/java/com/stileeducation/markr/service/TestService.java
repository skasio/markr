package com.stileeducation.markr.service;

import com.stileeducation.markr.entity.Test;
import com.stileeducation.markr.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TestService {

  @Autowired
  TestRepository testRepository;

  public Test findOrCreateTest(String testId, Integer marksAvailable) {
    Optional<Test> optionalTest = testRepository.findByTestId(testId);
    if (optionalTest.isPresent()) {
      return optionalTest.get();
    } else {
      Test test = new Test();
      test.setTestId(testId);
      test.setMarksAvailable(marksAvailable);
      return testRepository.save(test);
    }
  }

}
