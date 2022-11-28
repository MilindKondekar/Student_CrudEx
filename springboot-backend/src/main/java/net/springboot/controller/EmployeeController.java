package net.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.springboot.exception.ResourceNotFoundException;
import net.springboot.model.Student;
import net.springboot.repository.StudentRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController 
{
	@Autowired
	private StudentRepository studentsRepostiory;
	
	//get all Student rest api
	
	@GetMapping("/student")
	public List<Student> getAllEmployess()
	{
		return  studentsRepostiory.findAll();
		
	}
	
	//create Student rest api
	@PostMapping("/student")
	public Student createStudent(@RequestBody Student student)
	{
		return studentsRepostiory.save(student);
		
	}
   
	//get student by id api
	@GetMapping("/student/{id}")
	public ResponseEntity <Student> getStudentbyId(@PathVariable long id)
	{
		Student student = studentsRepostiory.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Student no exist with id"+id));
		return ResponseEntity.ok(student);
		
	}
	
	//update student rest api
	@PutMapping("/student/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable long id, @RequestBody Student studentDetails)
	{
		Student student = studentsRepostiory.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Student no exist with id"+id));
		student.setFirstName(studentDetails.getFirstName());
		student.setLastName(studentDetails.getLastName());
		student.setEmailId(studentDetails.getEmailId());
		
		Student updatedStudent = studentsRepostiory.save(student);
		return ResponseEntity.ok(student);
	}
	
	//delete student rest api
	@DeleteMapping("/student/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteStudent(@PathVariable long id)
	{
		Student student = studentsRepostiory.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Student no exist with id"+id));
		
		studentsRepostiory.delete(student);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
		
	}
	
}
