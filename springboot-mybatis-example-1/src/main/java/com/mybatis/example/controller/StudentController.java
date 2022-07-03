package com.mybatis.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mybatis.example.domain.Student;
import com.mybatis.example.service.StudentService;

@RestController
public class StudentController {
	@Autowired
	private StudentService studentService;

	/*
	 * 查询所有用户
	 */
	@RequestMapping("/findAllStudent")
	public List<Student> findAllStudent() {
		List<Student> findAllStudent = studentService.findAllStudent();
		System.out.println("查询所有用户的数据...."+findAllStudent);
		return findAllStudent;

	}

	/*
	 * 根据id查询用户
	 */
	@RequestMapping("/showStudent/{id}")
	public String selectStudent(@PathVariable int id) {
		return studentService.selectStudent(id).toString();

	}

	/*
	 * 根据学习名称模糊查询
	 */
	@RequestMapping("/findStudentByNmae/{studentname}")
	public List<Student> findStudentByNmae(@PathVariable String studentname) {
		return studentService.findStudentByNmae(studentname);

	}

	/*
	 * 保存学生信息
	 */
	@RequestMapping("/saveStudent")
	public String saveStudent(Student student) {
		return studentService.saveStudent(student);

	}

}