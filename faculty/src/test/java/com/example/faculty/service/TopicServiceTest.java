package com.example.faculty.service;

import com.example.faculty.repository.TopicRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class TopicServiceTest {

    @Mock
    TopicRepository topicRepo;

    @InjectMocks
    TopicService topicService;

    @Before
    public void setUp() {
        topicService = new TopicService(topicRepo);
    }

    @Test
    public void findById(){
        when(topicService.findById(any(Integer.class))).thenAnswer(i -> i.getMock());
    }
}
