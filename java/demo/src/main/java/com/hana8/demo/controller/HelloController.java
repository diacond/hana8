package com.hana8.demo.controller;

// 1. 반드시 org.slf4j 패키지의 Logger를 사용해야 합니다.

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HelloController {
//  // 2. LoggerFactory와 일치하는 SLF4J Logger 인터페이스를 사용합니다.
//  private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

  @RequestMapping("/")
  public String index() {
    return "Hana8 Demo kk";
  }

  @GetMapping("/hello")
  public String hello() {
    return "Hello, world!";
  }

  @GetMapping("/hello-servlet")
  public String helloServlet(String name) {
    // 3. 로그 작성 시 중괄호 {} 를 사용하여 성능과 가독성을 챙깁니다.
    log.info("INFO: name={} - code={}", name, 123);
    log.debug("DEBUG: ");
    log.warn("WARN: warning");
    log.error("ERROR: Critical error happened!");
    return "Hello, " + name + "!!!";
  }
}
