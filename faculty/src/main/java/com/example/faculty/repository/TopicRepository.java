package com.example.faculty.repository;

import com.example.faculty.entety.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Integer> {
    Topic findByName(String name);
}
