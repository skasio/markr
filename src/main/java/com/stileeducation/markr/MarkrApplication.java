package com.stileeducation.markr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MarkrApplication {

  @GetMapping(name = "/")
  public String index() {
    return "Mark - Marking as a Service";
  }

  public static void main(String[] args) {
    SpringApplication.run(MarkrApplication.class, args);
  }

}
