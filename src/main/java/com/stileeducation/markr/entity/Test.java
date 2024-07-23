package com.stileeducation.markr.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tests")
public class Test {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "test_id", nullable = false, unique = true)
  private String testId;

  @Column(name = "marks_available", nullable = false)
  private Integer marksAvailable;

  @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<TestResult> testResults = new HashSet<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTestId() {
    return testId;
  }

  public void setTestId(String testId) {
    this.testId = testId;
  }

  public Integer getMarksAvailable() {
    return marksAvailable;
  }

  public void setMarksAvailable(Integer marksAvailable) {
    this.marksAvailable = marksAvailable;
  }

  public Set<TestResult> getTestResults() {
    return testResults;
  }

  public void setTestResults(Set<TestResult> testResults) {
    this.testResults = testResults;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Test test = (Test) o;
    return Objects.equals(id, test.id) && Objects.equals(testId, test.testId) && Objects.equals(marksAvailable, test.marksAvailable) && Objects.equals(testResults, test.testResults);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, testId, marksAvailable, testResults);
  }

  @Override
  public String toString() {
    return "Test{" +
        "id=" + id +
        ", testId='" + testId + '\'' +
        ", marksAvailable=" + marksAvailable +
        ", testResults=" + testResults +
        '}';
  }
}
