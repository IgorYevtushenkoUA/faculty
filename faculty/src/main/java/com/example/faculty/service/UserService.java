package com.example.faculty.service;

import com.example.faculty.entety.*;
import com.example.faculty.entety.paging.Paged;
import com.example.faculty.entety.paging.Paging;
import com.example.faculty.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByEmail(email);
        System.out.println("was loaded user");
        if (user == null)
            throw new UsernameNotFoundException("user not found");
        return user;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public boolean save(User user, Role role) {
        User userFromDB = findByEmail(user.getEmail());
        if (userFromDB != null)
            return false;
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(role);
        userRepository.save(user);
        return true;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public Teacher findTeacherByPIB(String PIB) {
        return userRepository.findTeacherByPIB(PIB);
    }

    public Teacher findTeacherById(int id){return userRepository.findTeacherById(id);}

    public List<Teacher> findTeachersByPIB(String PIB) {
        return userRepository.findTeachersByPIB(PIB);
    }

    public List<Teacher> findAllTeacher() {
        return userRepository.findAllTeacher();
    }

    public List<Student> findAllStudents() {
        return userRepository.findAllStudents();
    }

    public Student findStudentInfoByIdAndCourseId(int studentId, int courseId) {
        return userRepository.findStudentInfoByIdAndCourseId(studentId, courseId);
    }

    public Student findStudentById(int id) {
        return userRepository.findStudentById(id);
    }

    public List<Student> findStudentsByPIB(String name) {
        return userRepository.findStudentsByPIB(name);
    }

    public List<Student> findEnrolledStudentToCourse(int courseId) {
        return userRepository.findEnrolledStudentToCourse(courseId);
    }

    public Paged<Student> getStudentsPage(String name, int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size);
        Page<Student> postPage = setStudents(name, request);
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size));
    }

    private Page<Student> setStudents(String name, Pageable pageable) {
        if (name.isEmpty())
            return findAllStudents(pageable);
        return findStudentsByPIB(name, pageable);
    }

    public Page<Student> findAllStudents(Pageable pageable) {
        return userRepository.findAllStudents(pageable);
    }

    public Page<Student> findStudentsByPIB(String name, Pageable pageable) {
        return userRepository.findStudentsByPIB(name, pageable);
    }
}