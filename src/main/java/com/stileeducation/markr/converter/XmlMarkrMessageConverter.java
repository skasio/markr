package com.stileeducation.markr.converter;

import com.stileeducation.markr.dto.MCQTestResultsDTO;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.xml.AbstractXmlHttpMessageConverter;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import java.util.Collections;
import java.util.List;

public class XmlMarkrMessageConverter extends AbstractXmlHttpMessageConverter<MCQTestResultsDTO> {

  public static final MediaType MEDIA_TYPE = new MediaType("text", "xml+markr");

  @Override
  protected MCQTestResultsDTO readFromSource(Class<? extends MCQTestResultsDTO> clazz, HttpHeaders headers, Source source) throws Exception {
    JAXBContext jaxbContext = JAXBContext.newInstance(MCQTestResultsDTO.class);
    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    return (MCQTestResultsDTO) unmarshaller.unmarshal(source);
  }

  @Override
  protected void writeToResult(MCQTestResultsDTO testResultsDTO, HttpHeaders headers, Result result) throws Exception {
    JAXBContext jaxbContext = JAXBContext.newInstance(MCQTestResultsDTO.class);
    Marshaller marshaller = jaxbContext.createMarshaller();
    marshaller.marshal(testResultsDTO, result);
  }

  @Override
  protected boolean supports(Class<?> clazz) {
    return MCQTestResultsDTO.class.isAssignableFrom(clazz);
  }

  @Override
  public List<MediaType> getSupportedMediaTypes() {
    return Collections.singletonList(MEDIA_TYPE);
  }
}
