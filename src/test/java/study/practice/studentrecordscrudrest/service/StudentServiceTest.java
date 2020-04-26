package study.practice.studentrecordscrudrest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import study.practice.studentrecordscrudrest.dao.StudentRepo;
import study.practice.studentrecordscrudrest.model.Student;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class StudentServiceTest {
    @Mock
    private StudentRepo studentRepo;
    
    @InjectMocks
    private StudentService studentService;
    

	@Test
	public void whenGetAllStudents_thenReturnListOfStudents() {
		ArrayList<Student> mockStudentList = new ArrayList<Student>();
		mockStudentList.add(new Student(1,"Pratik","IT"));
		Mockito.when(studentRepo.findAll()).thenReturn(mockStudentList);
		List<Student> actualStudentList = studentService.getAllStudents();		
		List<Student> expectedStudentList = List.of(new Student(1,"Pratik","IT"));
		assertEquals(expectedStudentList.size(), actualStudentList.size());
	}
	
	@Test
	public void whenGetStudentWithID_thenReturnStudent() {;
		
	}
	

}
