package com.estee.na.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@SpringBootApplication
@ComponentScan({
        "com.estee.na.service",
        "com.estee.na.web.rest",
        "com.estee.na.app",
        "com.estee.na.config"
})
@EnableJpaRepositories("com.estee.na.repository")
@EntityScan(basePackages = "com.estee.na.domain")
public class Application {

	private static ConfigurableApplicationContext ctx;

	public static void main(String[] args) {
		ctx = SpringApplication.run(Application.class, args);

		String databaseUrl = ctx.getEnvironment().getProperty("spring.datasource.url");
		log.info("Using database: {}", databaseUrl);

		log.info("Application started");
	}
}
