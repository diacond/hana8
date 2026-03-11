package com.hana8.demo.config;

// 💡 수정됨: annotations 패키지가 아닌 models 패키지로 전부 교체!

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 💡 수정됨: 스프링 설정 클래스임을 명시
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI() {
    Server devServer = new Server().url("/").description("개발 서버");
    Server prodServer = new Server().url("/api").description("운영 서버");

    return new OpenAPI()
        .servers(List.of(devServer, prodServer))
        .info(getInfo())
        // JWT 토큰 인증 전역 설정
        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
        .components(new Components()
            .addSecuritySchemes("bearerAuth",
                new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
            )
        );
  }

  private Info getInfo() {
    return new Info()
        .version("0.1.0")
        .title("SpringDemo APIs")
        .description("Hanaro 8 Project API Documents")
        .contact(new Contact().name("Hana8").email("hana8@hanabank.com"))
        .license(new License().name("Apache 2.0"));
  }
}
