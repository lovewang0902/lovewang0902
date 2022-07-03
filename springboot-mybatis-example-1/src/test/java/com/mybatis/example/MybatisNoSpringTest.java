package com.mybatis.example;

import org.springframework.boot.test.context.SpringBootTest;

import com.mybatis.example.domain.Student;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class MybatisNoSpringTest {

	public static void main(String[] args) {

		Student stu = new Student();
//		stu.setstudentName("wang");
		System.out.println("insert success...");

	}
}
