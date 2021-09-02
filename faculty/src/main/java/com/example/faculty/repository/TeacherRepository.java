package com.example.faculty.repository;

import com.example.faculty.entety.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    List<Teacher> findAll();

}
