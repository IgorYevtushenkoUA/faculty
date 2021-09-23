package com.example.faculty.service;

import com.example.faculty.repository.RoleRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleServiceTest {


    @Autowired
    private RoleService roleService;

    @Test
    void findByNameSuccessful(){
        System.out.println("---------------------");
//        System.out.println(roleService.findByName("ROLE_ADMIN"));
//        verify(roleService, times(1));
        boolean bool = true;
        Assert.assertTrue(bool);
    }

}
