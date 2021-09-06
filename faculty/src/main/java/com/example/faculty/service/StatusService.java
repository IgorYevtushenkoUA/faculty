package com.example.faculty.service;

import com.example.faculty.entety.Status;
import com.example.faculty.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StatusService {

    private final StatusRepository statusRepository;

    public Status findByName(String name){
        return statusRepository.findByName(name);
    }

}
