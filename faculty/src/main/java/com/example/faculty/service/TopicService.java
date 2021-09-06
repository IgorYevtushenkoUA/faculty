package com.example.faculty.service;

import com.example.faculty.entety.Topic;
import com.example.faculty.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;

    public Topic findByName(String name){
        return topicRepository.findByName(name);
    }

}
