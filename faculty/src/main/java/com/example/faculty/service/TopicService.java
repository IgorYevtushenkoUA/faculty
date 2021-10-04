package com.example.faculty.service;

import com.example.faculty.entety.Topic;
import com.example.faculty.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;

    public Topic findById(int id) {
        return topicRepository.findById(id).orElse(null);
    }

    public List<Topic> findAll() {
        return topicRepository.findAll();
    }

}
