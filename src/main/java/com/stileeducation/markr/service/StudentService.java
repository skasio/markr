package com.stileeducation.markr.service;

import com.stileeducation.markr.entity.Student;
import com.stileeducation.markr.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {

  private final StudentRepository studentRepository;

  public StudentService(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  @Transactional
  public Student findOrCreateStudent(String firstName, String lastName, String studentNumber) {
    Optional<Student> optionalStudent = studentRepository.findByStudentNumber(studentNumber);
    if (optionalStudent.isPresent()) {
      Student student = optionalStudent.get();
      // Reset transients
      student.setCreated(false);
      student.setUpdated(false);
      return student;
    } else {
      Student student = new Student();
      student.setFirstName(firstName);
      student.setLastName(lastName);
      student.setStudentNumber(studentNumber);
      student.setCreated(true);
      return studentRepository.save(student);
    }
  }
}
