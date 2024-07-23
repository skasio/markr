package com.stileeducation.markr.repository;

import com.stileeducation.markr.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestRepository extends JpaRepository<Test, Long> {
  Optional<Test> findByTestId(String testId);
}
