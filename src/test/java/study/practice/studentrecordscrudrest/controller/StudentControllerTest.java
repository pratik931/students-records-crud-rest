package study.practice.studentrecordscrudrest.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
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
	public void whenGetStudentbyIdAndStudentDoesNotExist_thenReturnBadRequestStatus() throws Exception {
		Student student = null;
		Mockito.when(studentService.getStudentByID(1)).thenReturn(student);
		mockMvc.perform(get("/students/1").contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isBadRequest());
		
		Mockito.verify(studentService).getStudentByID(1);
	}
	
	@Test
	public void whenCreateStudent_thenReturnStudentWithCreatedStatus() throws Exception {
		Student student = new Student(1,"Pratik","CS");
		Gson gson = new Gson();
		String studentJSON = gson.toJson(student);
		Mockito.when(studentService.createNewStudent(student)).thenReturn(student);
		
		
		mockMvc.perform(post("/students").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(studentJSON))
				.andExpect(status().isCreated());
				//.andExpect(content().json(studentJSON));
				//.andExpect(content().json("{\"id\":1,\"name\":\"Pratik\",\"department\":\"CS\"}"));
		
		Mockito.verify(studentService).createNewStudent(student);
			
	}
	
	@Test
	public void whenUpdateStudent_thenReturnUpdatedStudentWithAcceptedStatus() throws Exception {
		Gson gson = new Gson();
		Student student = new Student(1,"Pratik","CS");
		String studentJSON = gson.toJson(student);
		Student updatedStudent = new Student(1,"Pratik","IT");
		String updatedStudentJSON = gson.toJson(updatedStudent);
		Mockito.when(studentService.getStudentByID(1)).thenReturn(student);
		Mockito.when(studentService.updateStudent(1, student)).thenReturn(updatedStudent);
	
		mockMvc.perform(put("/students/1").contentType(MediaType.APPLICATION_JSON_VALUE).
				content(studentJSON))
			.andExpect(status().isAccepted());
			//.andExpect(content().json(updatedStudentJSON));
		
		Mockito.verify(studentService).getStudentByID(1);
		Mockito.verify(studentService).updateStudent(1, student);
			
	}
	
	@Test
	public void whenRemoveStudent_thenReturnOkStatus() throws Exception {
		Mockito.doNothing().when(studentService).removeStudent(1);
		mockMvc.perform(delete("/students/1").contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk());
		
		Mockito.verify(studentService).removeStudent(1);
	}
	
	
	
	
	
	
	
	
}
