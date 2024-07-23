package com.stileeducation.markr.dto;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Objects;

@XmlRootElement(name = "summary-marks")
public class SummaryMarksDTO {
  private int available;
  private int obtained;

  @XmlAttribute(name = "available")
  public int getAvailable() {
    return available;
  }

  public void setAvailable(int available) {
    this.available = available;
  }

  @XmlAttribute(name = "obtained")
  public int getObtained() {
    return obtained;
  }

  public void setObtained(int obtained) {
    this.obtained = obtained;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SummaryMarksDTO that = (SummaryMarksDTO) o;
    return available == that.available && obtained == that.obtained;
  }

  @Override
  public int hashCode() {
    return Objects.hash(available, obtained);
  }

  @Override
  public String toString() {
    return "SummaryMarksDTO{" +
        "available=" + available +
        ", obtained=" + obtained +
        '}';
  }
}
