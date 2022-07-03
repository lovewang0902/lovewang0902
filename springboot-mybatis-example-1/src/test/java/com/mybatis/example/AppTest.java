package com.mybatis.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.mybatis.example.domain.Student;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class AppTest {
	
	@Test
    void contextLoads() {
        System.out.println("contextLoadsd");
    }
    /**
     * 使用断言
     */
    @Test
    public void test2() {
    	String stu = new Student().toString();
    }
}

