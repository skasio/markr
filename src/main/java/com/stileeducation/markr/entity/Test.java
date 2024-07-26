package com.stileeducation.markr.entity;

import jakarta.persistence.*;

import java.util.Objects;

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

  @Transient
  private boolean created = false;

  @Transient
  private boolean updated = false;

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
    Test test = (Test) o;
    return Objects.equals(id, test.id) &&
        Objects.equals(testId, test.testId) &&
        Objects.equals(marksAvailable, test.marksAvailable);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, testId, marksAvailable);
  }

  @Override
  public String toString() {
    return "Test{" +
        "id=" + id +
        ", testId='" + testId + '\'' +
        ", marksAvailable=" + marksAvailable +
        ", created=" + created +
        ", updated=" + updated +
        '}';
  }
}
