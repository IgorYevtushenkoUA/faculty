package com.example.faculty.service;

import com.example.faculty.entety.Student;
import com.example.faculty.entety.Teacher;
import com.example.faculty.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepo;

    @Mock
    BCryptPasswordEncoder cryptPasswordEncoder;

    @InjectMocks
    UserService userService;

    @Before
    public void setUp() {
        userService = new UserService(userRepo, cryptPasswordEncoder);
    }

    @Test
    public void findById() {
        when(userService.findById(any(Integer.class))).thenAnswer(i -> i.getMock());
    }

    @Test
    public void findByEmail_Return_Null() {
        System.out.println(userService.findByEmail("notExistedemail@email.com"));
        when(userService.findByEmail("notExistedemail@email.com")).thenReturn(null);
    }

}
