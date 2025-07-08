package com.eazybytes.example18;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@SpringBootApplication(exclude = {AopAutoConfiguration.class, WebServiceTemplateAutoConfiguration.class})
@SpringBootApplication
@EnableJpaRepositories("com.eazybytes.example18.Repository")
@EntityScan("com.eazybytes.example18.Model")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class EazySchoolWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(EazySchoolWebApplication.class, args);
	}
}
