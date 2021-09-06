package com.example.faculty.repository;

import com.example.faculty.entety.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Integer> {

    Status findByName(String name);

}
