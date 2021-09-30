package com.example.faculty.service;

import com.example.faculty.entety.Role;
import com.example.faculty.repository.RoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class RoleServiceTest {


    @InjectMocks
    RoleService roleService;

    @Mock
    RoleRepository roleRepository;

    @Before
    public void setUp() {
        roleService = new RoleService(roleRepository);
    }

    @Test
    public void findByName_Return_Null() {
        when(roleService.findByName("Hello")).thenReturn(null);
    }

    @Test
    public void findByName_Return_Result() {
        Role role = new Role();
        role.setId(1);
        role.setName("ROLE_ADMIN");
        when(roleService.findByName("ROLE_ADMIN")).thenReturn(role);
    }

}
