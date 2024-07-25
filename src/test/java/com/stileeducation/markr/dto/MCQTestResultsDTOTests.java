package com.stileeducation.markr.dto;

import com.stileeducation.markr.MarkrApplication;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = MarkrApplication.class)
@ActiveProfiles("test")
public class MCQTestResultsDTOTests {

  private static MCQTestResultsDTO sampleResults;

  @BeforeAll
  static void setup() throws JAXBException, IOException {
    ClassPathResource resource = new ClassPathResource("sample-results.xml");
    try (InputStream inputStream = resource.getInputStream()) {
      JAXBContext jaxbContext = JAXBContext.newInstance(MCQTestResultsDTO.class);
      Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
      sampleResults = (MCQTestResultsDTO) unmarshaller.unmarshal(inputStream);
    }
  }

  @Test
  void givenSampleResultsXMLShouldUnmarshall() throws IOException {
    assertThat(sampleResults).isNotNull();
  }

}
