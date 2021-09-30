package com.example.faculty.service;

import com.example.faculty.repository.StatusRepository;
import com.example.faculty.repository.StudentHasCourseRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class StudentHasCourseServiceTest {

    @Mock
    StudentHasCourseRepository shcRepo;

    @Mock
    StatusRepository statusRepo;

    @InjectMocks
    StudentHasCourseService shcService;

    @Before
    public void setUp() {
        shcService = new StudentHasCourseService(shcRepo, statusRepo);
    }


}
