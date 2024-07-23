package com.stileeducation.markr.repository;

import com.stileeducation.markr.entity.Student;
import com.stileeducation.markr.entity.Test;
import com.stileeducation.markr.entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {

  Optional<TestResult> findByStudentAndTest(Student student, Test test);

  @Query("SELECT tr FROM TestResult tr WHERE tr.test.testId = :testId")
  List<TestResult> findAllByTestId(@Param("testId") String testId);
}
