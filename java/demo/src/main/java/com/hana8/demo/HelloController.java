package com.hana8.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  @GetMapping("/")
  public String index(){
    return "Hana8 Demo kk";
  }
  @GetMapping("/hello")
  public String hello(){
    return "Hello, world!";
  }
  @GetMapping("/hello-servlet")
  public String helloServlet(String name){
    return "Hello, " + name;
  }
}
