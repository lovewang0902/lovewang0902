package com.mybatis.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@MapperScan(basePackages = "com.mybatis.example.mapper")
public class SprintBootRunner {
	
	public static void main(String[] args) {
		
		SpringApplication.run(SprintBootRunner.class,args);
	}
	
	
} 