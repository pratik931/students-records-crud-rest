package study.practice.studentrecordscrudrest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import study.practice.studentrecordscrudrest.model.Student;
import study.practice.studentrecordscrudrest.service.StudentService;

@RestController
public class StudentController {
	@Autowired
	StudentService studentService;
	
	@GetMapping(path = "/students")
	public ResponseEntity<List<Student>> getStudents() {
		return ResponseEntity.ok().body(studentService.getAllStudents());
	}
	
	@GetMapping("/students/{id}")
	public ResponseEntity<Student> getStudentByID(@PathVariable(value="id") int id){
		Student student = studentService.getStudentByID(id);
		if(student == null)
			return ResponseEntity.badRequest().build();
		return ResponseEntity.ok().body(student);
	}
	
	@PostMapping(path = "/students", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Student> createStudent(@RequestBody Student student) {
		return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createNewStudent(student));
	}
	
	@PutMapping(path = "/students/{id}", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Student> updateStudent(@PathVariable(value="id") int id, @RequestBody Student student) {
		Student studentCheck = studentService.getStudentByID(id);
		if(studentCheck == null)
			return ResponseEntity.badRequest().build();
		
		return ResponseEntity.accepted().body(studentService.updateStudent(id, student));
	}
	
	@DeleteMapping("/students/{id}")
	public ResponseEntity<String> removeStudent(@PathVariable(value="id") int id){
		studentService.removeStudent(id);
		String response = "Student with id: " + id + " is deleted";
		return ResponseEntity.ok().body(response);
	}
}
