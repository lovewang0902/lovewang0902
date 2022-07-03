package com.mybatis.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mybatis.example.domain.Student;
import com.mybatis.example.mapper.StudentMapper;

@Service
public class StudentService{

	@Autowired
    private StudentMapper studentMapper;
	
	public Student selectStudent(int id) {
        return studentMapper.selectStudent(id);
    }
	public List<Student> findAllStudent() {
		return studentMapper.findAllStudent();
	}
	public List<Student> findStudentByNmae(String studentname) {
		return studentMapper.findStudentByNmae(studentname);
	}
	public String saveStudent(Student student) {
		return studentMapper.saveStudent(student);
	}
	
}