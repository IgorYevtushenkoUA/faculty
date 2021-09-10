package com.example.faculty.repository;

import com.example.faculty.entety.StudentHasCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentHasCourseRepository extends JpaRepository<StudentHasCourse, Integer> {

    @Query(value = "select * " +
            "from student_has_course shc " +
            "where shc.course_id=:courseId " +
            "and date_part('year', shc.recording_time) = :year ", nativeQuery = true)
    List<StudentHasCourse> findAllStudentsByCourseAndYear(@Param("courseId") int courseId, @Param("year") int year);

}
