package com.example.faculty.repository;

import com.example.faculty.entety.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
