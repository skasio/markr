package com.stileeducation.markr.repository;

import com.stileeducation.markr.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
  Optional<Student> findByStudentNumber(String studentNumber);
}
