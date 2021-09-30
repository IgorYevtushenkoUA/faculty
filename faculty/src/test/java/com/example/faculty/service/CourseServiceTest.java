package com.example.faculty.service;

import com.example.faculty.repository.CourseRepository;
import com.example.faculty.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class CourseServiceTest {

    @Mock
    CourseRepository courseRepo;

    @Mock
    UserRepository userRepo;

    @InjectMocks
    CourseService courseService;

    @Before
    public void setUp(){
        courseService = new CourseService(courseRepo, userRepo);
    }

    @Test
    public void findById() {
        when(courseService.findById(any(Integer.class))).thenAnswer(i -> i.getMock());
    }
}
