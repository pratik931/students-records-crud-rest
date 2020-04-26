package study.practice.studentrecordscrudrest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<Student> getStudents() {
		return studentService.getAllStudents();
	}
	
	@GetMapping("/students/{id}")
	public ResponseEntity<Student> getStudentByID(@PathVariable(value="id") int id){
		Student student = studentService.getStudentByID(id);
		return ResponseEntity.ok().body(student);
	}
	
	@PostMapping(path = "/students", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public Student createStudent(@RequestBody Student student) {
		return studentService.createNewStudent(student);
	}
	
	@PutMapping("/students/{id}")
	public Student updateStudent(@PathVariable(value="id") int id, @RequestBody Student student) {
		return studentService.updateStudent(id, student);
	}
	
	@DeleteMapping("/students/{id}")
	public Map<String,Boolean> removeStudent(@PathVariable(value="id") int id){
		studentService.removeStudent(id);
		Map<String,Boolean> response = new HashMap<>();
		response.put("Deleted",true);
		return response;
	}
}
