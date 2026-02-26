package com.hana8.demo.service;

public class GreetingService {

  private HelloService service;

  public String call() {
    return service.sayHello();
  }

}
