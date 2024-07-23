package com.stileeducation.markr.controller;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.stileeducation.markr.dto.MCQTestResultsDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static com.stileeducation.markr.controller.TestResultsController.IMPORT_ENDPOINT;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestResultsControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private XmlMapper xmlMapper;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void testImport() throws Exception {
    MCQTestResultsDTO request = new MCQTestResultsDTO();
    String requestXml = xmlMapper.writeValueAsString(request);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_XML);

    HttpEntity<String> entity = new HttpEntity<>(requestXml, headers);
    ResponseEntity<String> response = restTemplate.postForEntity(IMPORT_ENDPOINT, entity, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

}
