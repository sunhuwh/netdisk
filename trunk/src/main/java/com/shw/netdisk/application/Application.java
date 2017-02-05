package com.shw.netdisk.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.shw.netdisk.config.BeetlProperties;
import com.shw.netdisk.storage.StorageProperties;
import com.shw.netdisk.storage.StorageService;


@Configuration
@ComponentScan(basePackages = {"com.shw.netdisk.controller","com.shw.netdisk.storage", "com.shw.netdisk.application",
		"com.shw.netdisk.serviceImpl"})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, ThymeleafAutoConfiguration.class})
@EnableConfigurationProperties({StorageProperties.class, BeetlProperties.class})
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
            storageService.deleteAll();
            storageService.init();
		};
	}
	
}
