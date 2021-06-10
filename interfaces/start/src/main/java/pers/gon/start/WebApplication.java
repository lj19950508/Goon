package pers.gon.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@Import(cn.hutool.extra.spring.SpringUtil.class)
@EnableJpaAuditing
@EnableJpaRepositories (basePackages = {"pers.gon.domain"})
@EntityScan(basePackages = {"pers.gon.domain"})
@SpringBootApplication(scanBasePackages = {"pers.gon.manage","pers.gon.infrastructure","pers.gon.domain","pers.gon.application","pers.gon.api"})
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
