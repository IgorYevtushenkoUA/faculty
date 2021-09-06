package com.example.faculty.service;

import com.example.faculty.entety.Role;
import com.example.faculty.entety.Student;
import com.example.faculty.entety.Teacher;
import com.example.faculty.entety.User;
import com.example.faculty.enums.ROLE;
import com.example.faculty.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByEmail(email);
        System.out.println("was loaded user");
        if (user == null)
            throw new UsernameNotFoundException("user not found");
        System.out.println(user);
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

    public Teacher findTeacherByPIB(String PIB) {
        return userRepository.findTeacherByPIB(PIB);
    }

    public List<Teacher> findTeachersByPIB(String PIB) {
        return userRepository.findTeachersByPIB(PIB);
    }


    public List<Teacher> findAllTeacher() {
        return userRepository.findAllTeacher();
    }

    public List<Student> findAllStudent() {
        return userRepository.findAllStudent();
    }

    /*
    todo
    findAllTeacher
    findAllStudent
    updateTeacher
    updateStudent
    deleteStudent
    deleteTeacher
     */

}