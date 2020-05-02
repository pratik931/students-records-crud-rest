package study.practice.studentrecordscrudrest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import study.practice.studentrecordscrudrest.dao.StudentRepo;
import study.practice.studentrecordscrudrest.model.Student;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepo studentRepo;
	
	public List<Student> getAllStudents() {
		List<Student> allStudents = (List<Student>) studentRepo.findAll();
		return allStudents;
	}
	
	public Student getStudentByID(int id) {
		Student student = studentRepo.findById(id).orElse(null);
		return student;
	}
	
	public Student createNewStudent(Student student) {
		Student newStudent = studentRepo.save(student);
		return newStudent;
	}
	
	public Student updateStudent(int id, Student student) {
		Student updatedStudent = (Student) studentRepo.findById(id).orElse(new Student());
		updatedStudent.setName(student.getName());
		updatedStudent.setDepartment(student.getDepartment());
		return studentRepo.save(updatedStudent);
	}
	
	public void removeStudent(int id) {
		studentRepo.deleteById(id);
	}
}
