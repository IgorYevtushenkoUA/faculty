package com.example.faculty.repository;

import com.example.faculty.entety.Student;
import com.example.faculty.entety.Teacher;
import com.example.faculty.entety.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    Teacher findTeacherById(int id);

    @Query("select u from User u where lower(concat(u.lastName,' ',u.firstName,' ',u.secondName))=lower(:pib) and u.role.id=2")
    Teacher findTeacherByPIB(String pib);

    @Query("select u from User u where lower(concat(u.lastName,' ',u.firstName,' ',u.secondName)) like lower(concat('%',:pib,'%') )")
    List<Teacher> findTeachersByPIB(@Param("pib") String pib);

    @Query("select u  from User u where u.role.id=2")
    List<Teacher> findAllTeacher();

    @Query("select u  from User u where u.role.id=3")
    List<Student> findAllStudent();

    @Query("select u " +
            "from User u " +
            "inner join StudentHasCourse shc on shc.studentCourseId.studentId=u.id " +
            "   and shc.studentCourseId.courseId=:courseId " +
            "where u.id=:studentId ")
    Student findStudentInfoByIdAndCourseId(@Param("studentId") int studentId, @Param("courseId") int courseId);

    @Query("select u from User u where u.id=:studentId")
    Student findStudentById(@Param("studentId") int studentId);
}
