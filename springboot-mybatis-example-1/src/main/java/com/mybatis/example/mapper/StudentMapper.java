package com.mybatis.example.mapper;

import java.util.List;

import com.mybatis.example.domain.Student;

public interface StudentMapper {
	/*
	 * 查询所以学生
	 */
	List<Student> findAllStudent();

	/*
	 * 通过id查询学生
	 */
	Student selectStudent(int id);

	/*
	 * 通过学生名，模糊查询学生
	 */
	List<Student> findStudentByNmae(String studentname);

	/*
	 * 插入学生信息
	 */
	String saveStudent(Student student);

	/*
	 * 根据id,删除学生
	 */
	String deleteStudentById(Integer id);

	/*
	 * 修改学生信息
	 */
	String updateStudent(Student student);

	// https://www.cnblogs.com/hts-technology/p/8716085.html
}
