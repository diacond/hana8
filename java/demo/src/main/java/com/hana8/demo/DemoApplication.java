package com.hana8.demo;

import com.hana8.demo.controller.HelloController;
import com.hana8.demo.service.GreetingService;
import com.hana8.demo.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {

    ConfigurableApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);

    HelloController helloBean = ctx.getBean(HelloController.class);
    log.debug("hello = {}", helloBean.hello());

    HelloService helloService = ctx.getBean(HelloService.class);
    log.debug("helloService = {}", helloService.sayHello());

    GreetingService greetingService = ctx.getBean(GreetingService.class);
    log.debug("greetingService = {}", greetingService.call());
  }

}
