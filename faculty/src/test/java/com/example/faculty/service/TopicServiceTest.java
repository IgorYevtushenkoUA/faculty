package com.example.faculty.service;

import com.example.faculty.entety.Topic;
import com.example.faculty.repository.TopicRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class TopicServiceTest {

    Topic topic;

    @Mock
    TopicRepository topicRepo;

    @InjectMocks
    TopicService topicService;

    @Before
    public void setUp() {
        topicService = new TopicService(topicRepo);
        topic = new Topic();
        topic.setId(1);
        topic.setName("INFORMATICS");
        System.out.println("------- topic : " + topic);
    }

    @Test
    public void findById_Should_Return_Null() {
        when(topicRepo.findById(any())).thenReturn(null);
        Topic t = topicService.findById(1);
        Assert.assertNull(t);
    }

    @Test
    public void findById_Should_Return_Topic() {
        when(topicRepo.findById(any())).thenReturn(Optional.ofNullable(topic));
        Topic t = topicService.findById(1);
        Assert.assertNotNull(t);
    }

    @Test
    public void findAllTopics_Should_Return_Empty() {
        when(topicRepo.findAll()).thenReturn(new ArrayList<>());
        Assert.assertTrue(topicService.findAll().isEmpty());
    }

    @Test
    public void findAllTopics_Should_Return_Not_Empty() {
        when(topicRepo.findAll()).thenReturn(List.of(topic));
        Assert.assertFalse(topicService.findAll().isEmpty());
    }

}
