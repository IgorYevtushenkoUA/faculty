package com.example.faculty;

import com.example.faculty.service.CourseService;
import com.example.faculty.service.StudentService;
import com.example.faculty.service.TeacherService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class FacultyApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(FacultyApplication.class, args);
		test(applicationContext);
	}

	private static void test(ApplicationContext applicationContext) {

		CourseService courseService = applicationContext.getBean(CourseService.class);
		TeacherService teacherService = applicationContext.getBean(TeacherService.class);
		StudentService studentService = applicationContext.getBean(StudentService.class);
	}

}
