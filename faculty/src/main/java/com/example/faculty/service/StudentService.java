package com.example.faculty.service;

import com.example.faculty.entety.Student;
import com.example.faculty.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    public List<Student> findAll(){return studentRepository.findAll();}
}
