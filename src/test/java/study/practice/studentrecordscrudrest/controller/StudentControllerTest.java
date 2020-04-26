package study.practice.studentrecordscrudrest.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.assertj.core.util.Arrays;
import org.hibernate.query.criteria.internal.expression.SearchedCaseExpression.WhenClause;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;


import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;

import study.practice.studentrecordscrudrest.model.Student;
import study.practice.studentrecordscrudrest.service.StudentService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {
	
	@Mock
	private StudentService studentService;
	
	@InjectMocks
	private StudentController studentController;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
	}
	
	@Test
	public void whenGetStudents_thenReturnJSONValueAndOkStatus() throws Exception {
		List<Student> students = java.util.Arrays.asList(new Student(1,"Pratik","CS"),
								new Student(2,"ABC","IT"),
								new Student(3,"Xyz","CIVIL"));
		Mockito.when(studentService.getAllStudents()).thenReturn(students);
		
		mockMvc.perform(get("/students").contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(3)))
			.andExpect(content().json("[{'id':1,'name':'Pratik','department':'CS'},"
					+ "{'id':2,'name':'ABC','department':'IT'},"
					+ "{'id':3,'name':'Xyz','department':'CIVIL'}]"));
		
		Mockito.verify(studentService).getAllStudents();
	}
	
	@Test
	public void whenGetStudentbyId_thenReturnJSONValueAndOkStatus() throws Exception {
		Student student = new Student(1,"Pratik","CS");
		Mockito.when(studentService.getStudentByID(1)).thenReturn(student);
		mockMvc.perform(get("/students/1").contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(content().json("{'id':1,'name':'Pratik','department':'CS'}"));
		
		Mockito.verify(studentService).getStudentByID(1);
	}
	
	@Test
	public void whenCreateStudent_thenReturnStudent() throws Exception {
		Student student = new Student(1,"Pratik","CS");
		Gson gson = new Gson();
		String studentJSON = gson.toJson(student);
		System.out.println(studentJSON);
		Mockito.when(studentService.createNewStudent(student)).thenReturn(student);
		
		
		mockMvc.perform(post("/students").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(studentJSON))
				.andExpect(status().isOk())
				.andExpect(content().json("{\"id\":1,\"name\":\"Pratik\",\"department\":\"CS\"}"));
			
	}
	
	
	
	
	
	
	
	
}
