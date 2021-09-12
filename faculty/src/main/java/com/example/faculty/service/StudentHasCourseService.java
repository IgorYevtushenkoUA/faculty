package com.example.faculty.service;

import com.example.faculty.entety.Student;
import com.example.faculty.entety.StudentCourseId;
import com.example.faculty.entety.StudentHasCourse;
import com.example.faculty.enums.STATUS;
import com.example.faculty.repository.StudentHasCourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class StudentHasCourseService {

    private final StudentHasCourseRepository studentHasCourseRepository;

    @Autowired
    StatusService statusService;

    public void enrollStudentToCourse(int studentId, int courseId) {
        StudentHasCourse shc = new StudentHasCourse(
                new StudentCourseId(studentId, courseId),
                null,
                new Timestamp(System.currentTimeMillis()),
                statusService.findByName(STATUS.NOT_STARTED.name()));
        studentHasCourseRepository.save(shc);
    }

    public List<StudentHasCourse> findAllStudentsByCourseAndYearAndName(int courseId, int year){
        return studentHasCourseRepository.findAllStudentsByCourseAndYearAndName(courseId, year);
    }

    public List<StudentHasCourse> findAllStudentsByCourse(int courseId){
        return studentHasCourseRepository.findAllStudentsByCourse(courseId);
    }

    public Integer getMarkForCourse(int studentId, int courseId){
        return studentHasCourseRepository.getMarkForCourse(studentId, courseId);
    }

    public void save(StudentHasCourse studentHasCourse){
        studentHasCourseRepository.save(studentHasCourse);
    }

    public StudentHasCourse findByStudentAndCourse(int studentId, int courseId){
        return studentHasCourseRepository.findByStudentAndCourse(studentId, courseId);
    }

}
