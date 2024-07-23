package com.stileeducation.markr.controller;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.stileeducation.markr.dto.AggregatedTestResultsDTO;
import com.stileeducation.markr.dto.MCQTestResultsDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static com.stileeducation.markr.controller.TestResultsController.IMPORT_ENDPOINT;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestResultsControllerTest {

  @Autowired
  private XmlMapper xmlMapper;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void testImport() throws Exception {
    // Given
    MCQTestResultsDTO request = new MCQTestResultsDTO();
    String requestXml = xmlMapper.writeValueAsString(request);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_XML);

    // When
    HttpEntity<String> entity = new HttpEntity<>(requestXml, headers);
    ResponseEntity<String> response = restTemplate.postForEntity(IMPORT_ENDPOINT, entity, String.class);

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  public void testAggregate() throws Exception {
    // Given
    String testId = "123"; // Example test ID
    String url = "/results/" + testId + "/aggregate";

    // When
    ResponseEntity<AggregatedTestResultsDTO> response = restTemplate.getForEntity(url, AggregatedTestResultsDTO.class);

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    AggregatedTestResultsDTO results = response.getBody();
    assertThat(results).isNotNull();
    assertThat(results.getMean()).isEqualTo(65.0);
    assertThat(results.getStddev()).isEqualTo(0.0);
    assertThat(results.getMin()).isEqualTo(65.0);
    assertThat(results.getMax()).isEqualTo(65.0);
    assertThat(results.getP25()).isEqualTo(65.0);
    assertThat(results.getP50()).isEqualTo(65.0);
    assertThat(results.getP75()).isEqualTo(65.0);
    assertThat(results.getCount()).isEqualTo(1);
  }

}
