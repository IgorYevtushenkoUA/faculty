package com.example.faculty.service;

import com.example.faculty.entety.Teacher;
import com.example.faculty.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TeacherService {
    public final TeacherRepository teacherRepository;

    public List<Teacher> findAll(){
        return teacherRepository.findAll();
    }
}
