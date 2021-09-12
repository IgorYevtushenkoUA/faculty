package com.example.faculty.repository;

import com.example.faculty.entety.StudentHasCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentHasCourseRepository extends JpaRepository<StudentHasCourse, Integer> {

    @Query(value = "select * " +
            "from student_has_course shc " +
            "where shc.course_id = :courseId " +
            "  and date_part('year', shc.recording_time) = :year ", nativeQuery = true)
    List<StudentHasCourse> findAllStudentsByCourseAndYearAndName(@Param("courseId") int courseId,
                                                                 @Param("year") int year);

    @Query(value = "select * " +
            "from student_has_course shc " +
            "where shc.course_id=:courseId ", nativeQuery = true)
    List<StudentHasCourse> findAllStudentsByCourse(@Param("courseId") int courseId);

    @Query("select shc.mark " +
            "from StudentHasCourse shc " +
            "where shc.studentCourseId.studentId=:studentId " +
            "   and shc.studentCourseId.courseId=:courseId")
    Integer getMarkForCourse(@Param("studentId") int studentId,
                             @Param("courseId") int courseId);

}
