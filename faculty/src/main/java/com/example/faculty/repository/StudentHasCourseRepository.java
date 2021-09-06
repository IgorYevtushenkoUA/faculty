package com.example.faculty.repository;

import com.example.faculty.entety.StudentHasCourse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentHasCourseRepository extends JpaRepository<StudentHasCourse, Integer> {

}
