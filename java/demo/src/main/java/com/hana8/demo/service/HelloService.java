package com.hana8.demo.service;

import org.springframework.stereotype.Component;

@Component
public class HelloService {

  public String hello() {
    return "Good Morning";
  }

  public String sayHello() {
    return "Hello Service";
  }
}
