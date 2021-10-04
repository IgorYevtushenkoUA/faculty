package com.example.faculty.service;

import com.example.faculty.entety.Role;
import com.example.faculty.entety.User;
import com.example.faculty.enums.ROLE;
import com.example.faculty.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    User user, registerUser;

    @Mock
    UserRepository userRepo;

    @Mock
    BCryptPasswordEncoder cryptPasswordEncoder;

    @InjectMocks
    UserService userService;

    @Before
    public void setUp() {
        userService = new UserService(userRepo, cryptPasswordEncoder);
        Role role = new Role();
        role.setId(1);
        role.setName(ROLE.ROLE_ADMIN.name());
        user = new User(1, "user", "user", "user", "user@gmail.com", "password", role);
        registerUser = new User(2, "user", "user", "user", "user@gmail.com", "password", role);

    }

    @Test
    public void validateUserSuccess() {
        String email = "user@gmail.com";
        when(userRepo.findByEmail(any())).thenReturn(user);
        User tempUser = userService.findByEmail(email);
        Assert.assertTrue(user.equals(tempUser));
    }

    @Test
    public void validateUserFailed() {
        String email = "userError@gmail.com";
        User tempUser = userService.findByEmail(email);
        Assert.assertNull(tempUser);
    }

    @Test
    public void updateUser() {
        when(userRepo.findById(any())).thenReturn(Optional.ofNullable(user));
        when(userRepo.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);
        User u = userService.save(registerUser);
        Assert.assertNotNull(u);
        Assert.assertEquals(u.getEmail(), user.getEmail());
        Assert.assertEquals(u.getPassword(), user.getPassword());
        Assert.assertEquals(u.getFirstName(), user.getFirstName());
        Assert.assertEquals(u.getSecondName(), user.getSecondName());
        Assert.assertEquals(u.getLastName(), user.getLastName());
        Assert.assertEquals(u.getRole(), user.getRole());
    }

    @Test
    public void findById_Should_Return_Null() {
        when(userRepo.findById(any())).thenReturn(Optional.ofNullable(null));
        User u = userService.findById(1);
        Assert.assertNull(u);
    }

    @Test
    public void findById_Should_Return_User() {
        when(userRepo.findById(any())).thenReturn(Optional.ofNullable(user));
        User u = userService.findById(1);
        Assert.assertNotNull(u);
    }

    @Test
    public void findByEmail_Should_Return_Null() {
        when(userRepo.findByEmail(any())).thenReturn(null);
        User u = userService.findByEmail("user1@gmail.com");
        Assert.assertNull(u);
    }

    @Test
    public void findByEmail_Return_User() {
        when(userRepo.findByEmail(any())).thenReturn(user);
        User u = userService.findByEmail("user1@gmail.com");
        Assert.assertNotNull(u);
    }

}
