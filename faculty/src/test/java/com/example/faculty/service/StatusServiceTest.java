package com.example.faculty.service;

import com.example.faculty.entety.Status;
import com.example.faculty.enums.STATUS;
import com.example.faculty.repository.StatusRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class StatusServiceTest {

    Status status;

    @InjectMocks
    StatusService statusService;

    @Mock
    StatusRepository statusRepo;

    @Before
    public void setUp() {
        statusService = new StatusService(statusRepo);
        status = new Status();
        status.setId(1);
        status.setName("NOT_STARTED");
    }

    @Test
    public void findByName_Should_Return_Null() {
        when(statusRepo.findByName(any())).thenReturn(null);
        Status s = statusService.findByName(STATUS.NOT_STARTED.name());
        Assert.assertNull(s);
    }

    @Test
    public void findByName_Should_Return_NOT_NULL(){
        when(statusRepo.findByName(STATUS.NOT_STARTED.name())).thenReturn(status);
        Status s = statusService.findByName(STATUS.NOT_STARTED.name());
        Assert.assertNotNull(s);
    }
}
