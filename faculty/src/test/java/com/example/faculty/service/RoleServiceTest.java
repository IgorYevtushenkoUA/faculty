package com.example.faculty.service;

import com.example.faculty.entety.Role;
import com.example.faculty.enums.ROLE;
import com.example.faculty.repository.RoleRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class RoleServiceTest {

    Role role;

    @InjectMocks
    RoleService roleService;

    @Mock
    RoleRepository roleRepository;

    @Before
    public void setUp() {
        roleService = new RoleService(roleRepository);
        role = new Role();
        role.setId(1);
        role.setName("ROLE_ADMIN");
    }

    @Test
    public void findByName_Should_Return_Null() {
        when(roleRepository.findByName(any())).thenReturn(null);
        Role r = roleService.findByName("ROLE_ADMIN");
        Assert.assertNull(r);
    }

    @Test
    public void findByName_Should_Return_Role() {
        when(roleRepository.findByName(any())).thenReturn(role);
        Role r = roleService.findByName("ROLE_ADMIN");
        Assert.assertNotNull(r);
    }

    @Test
    public void isAdminRole_Should_Return_True() {
        String r = ROLE.ROLE_ADMIN.name();
        Assert.assertTrue(r.equals(role.getName()));
    }

    @Test
    public void isAdminRole_Should_Return_False(){
        String r = ROLE.ROLE_STUDENT.name();
        Assert.assertFalse(r.equals(role.getName()));
    }

}
