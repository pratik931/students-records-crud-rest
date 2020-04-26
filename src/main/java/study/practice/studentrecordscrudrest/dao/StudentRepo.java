package study.practice.studentrecordscrudrest.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import study.practice.studentrecordscrudrest.model.Student;

@Repository
public interface StudentRepo extends CrudRepository<Student, Integer>{

}
