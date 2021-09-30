package com.example.faculty.service;

import com.example.faculty.entety.Status;
import com.example.faculty.repository.StatusRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class StatusServiceTest {


    @InjectMocks
    StatusService statusService;
    @Mock
    StatusRepository statusRepo;


    @Before
    public void setUp() {
        statusService = new StatusService(statusRepo);
    }

    @Test
    public void findByName_Return_Null() {
        when(statusService.findByName("hello")).thenReturn(null);
    }

    @Test
    public void findByName_Return_Result() {
        Status status = new Status();
        status.setId(1);
        status.setName("NOT_STARTED");
        when(statusService.findByName("NOT_STARTED")).thenReturn(status);
    }
}
