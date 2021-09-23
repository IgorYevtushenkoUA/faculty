package com.example.faculty.repository;

import com.example.faculty.entety.Student;
import com.example.faculty.entety.Teacher;
import com.example.faculty.entety.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    @Query("select u from User u where u.id=:id")
    Teacher findTeacherById(@Param("id") int id);

    @Query("select u from User u where lower(concat(u.lastName,' ',u.firstName,' ',u.secondName))=lower(:pib) and u.role.id=2")
    Teacher findTeacherByPIB(String pib);

    @Query("select u from User u where lower(concat(u.lastName,' ',u.firstName,' ',u.secondName)) like lower(concat('%',:pib,'%') )")
    List<Teacher> findTeachersByPIB(@Param("pib") String pib);

    @Query("select t from Teacher t")
    List<Teacher> findAllTeacher();

    @Query("select u  from User u where u.role.id=3")
    List<Student> findAllStudents();

    @Query("select u " +
            "from User u " +
            "inner join StudentHasCourse shc on shc.studentCourseId.studentId=u.id " +
            "   and shc.studentCourseId.courseId=:courseId " +
            "where u.id=:studentId ")
    Student findStudentInfoByIdAndCourseId(@Param("studentId") int studentId, @Param("courseId") int courseId);

    @Query("select u from User u where u.id=:studentId")
    Student findStudentById(@Param("studentId") int studentId);

    @Query("select s " +
            "from User s " +
            "inner join StudentHasCourse shc on shc.studentCourseId.studentId=s.id " +
            "where shc.studentCourseId.courseId=:courseId ")
    List<Student> findEnrolledStudentToCourse(@Param("courseId") int courseId);

    @Query("select u " +
            "from User u " +
            "where u.role.id = 3 " +
            "and lower(concat(u.lastName,' ',u.firstName,' ',u.secondName)) like lower(concat('%',:name,'%') )")
    List<Student> findStudentsByPIB(String name);

    @Query("select u from User u where u.role.id=3")
    Page<Student> findAllStudents(Pageable pageable);

    @Query("select u from " +
            "User u " +
            "where u.role.id=3 " +
            "and lower(concat(u.lastName,' ',u.firstName,'',u.lastName)) like lower(concat('%',:name,'%'))")
    Page<Student> findStudentsByPIB(@Param("name") String name, Pageable pageable);


}
