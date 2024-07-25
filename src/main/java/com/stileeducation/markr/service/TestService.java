package com.stileeducation.markr.service;

import com.stileeducation.markr.entity.Test;
import com.stileeducation.markr.repository.TestRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TestService {

  final
  TestRepository testRepository;

  public TestService(TestRepository testRepository) {
    this.testRepository = testRepository;
  }

  public Optional<Test> findTest(String testId) {
    return testRepository.findByTestId(testId);
  }

  public Test findOrCreateTest(String testId, Integer marksAvailable) {
    Optional<Test> optionalTest = testRepository.findByTestId(testId);
    if (optionalTest.isPresent()) {
      Test test = optionalTest.get();
      if (test.getMarksAvailable() < marksAvailable) {
        test.setMarksAvailable(marksAvailable);
        test.setUpdated(true);
        testRepository.save(test);
      } else {
        test.setUpdated(false);
      }
      return test;
    } else {
      Test test = new Test();
      test.setTestId(testId);
      test.setMarksAvailable(marksAvailable);
      test.setCreated(true);
      return testRepository.save(test);
    }
  }
}
