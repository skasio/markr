package com.stileeducation.markr.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "test_results")
public class TestResult {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id", nullable = false)
  private Student student;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "test_id", nullable = false)
  private Test test;

  @Column(name = "marks_awarded", nullable = false)
  private Integer marksAwarded;

  @Transient
  private boolean created = false;

  @Transient
  private boolean updated = false;

  public TestResult() {
  }

  public TestResult(Long id, Student student, Test test, Integer marksAwarded) {
    this.id = id;
    this.student = student;
    this.test = test;
    this.marksAwarded = marksAwarded;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public Test getTest() {
    return test;
  }

  public void setTest(Test test) {
    this.test = test;
  }

  public Integer getMarksAwarded() {
    return marksAwarded;
  }

  public void setMarksAwarded(Integer marksAwarded) {
    this.marksAwarded = marksAwarded;
  }

  public boolean isCreated() {
    return created;
  }

  public void setCreated(boolean created) {
    this.created = created;
  }

  public boolean isUpdated() {
    return updated;
  }

  public void setUpdated(boolean updated) {
    this.updated = updated;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TestResult that = (TestResult) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(student, that.student) &&
        Objects.equals(test, that.test) &&
        Objects.equals(marksAwarded, that.marksAwarded) &&
        Objects.equals(created, that.created) &&
        Objects.equals(updated, that.updated);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, student, test, marksAwarded);
  }

  @Override
  public String toString() {
    return "TestResult{" +
        "id=" + id +
        ", student=" + student +
        ", test=" + test +
        ", marksAwarded=" + marksAwarded +
        ", created=" + created +
        ", updated=" + updated +
        '}';
  }
}