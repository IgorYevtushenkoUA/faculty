package com.example.faculty;

import com.example.faculty.entety.Role;
import com.example.faculty.enums.ROLE;
import com.example.faculty.repository.StudentHasCourseRepository;
import com.example.faculty.service.CourseService;
import com.example.faculty.service.RoleService;
import com.example.faculty.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "messages.properties", encoding = "UTF-8")
public class FacultyApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(FacultyApplication.class, args);
		test(applicationContext);
	}

	private static void test(ApplicationContext applicationContext) {

		CourseService courseService = applicationContext.getBean(CourseService.class);
		UserService userService = applicationContext.getBean(UserService.class);
		RoleService roleService = applicationContext.getBean(RoleService.class);
		StudentHasCourseRepository studentHasCourseRepository = applicationContext.getBean(StudentHasCourseRepository.class);

	}

}
