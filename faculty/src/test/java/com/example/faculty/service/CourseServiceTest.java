package com.example.faculty.service;

import com.example.faculty.entety.Course;
import com.example.faculty.entety.Topic;
import com.example.faculty.repository.CourseRepository;
import com.example.faculty.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class CourseServiceTest {

    Course course, tempCourse;

    @Mock
    CourseRepository courseRepo;

    @Mock
    UserRepository userRepo;

    @InjectMocks
    CourseService courseService;

    @Before
    public void setUp() {
        courseService = new CourseService(courseRepo, userRepo);
        course = new Course();
        course.setId(1);
        course.setTopic(new Topic(1, "INFORMATICS"));
        course.setCapacity(30);
        course.setSemesterStart(2);
        course.setSemesterDuration(1);
        course.setDescription("This subject shows how computer works");
        course.setTeacher(null);
        course.setName("Computer design");
        tempCourse = new Course();
        tempCourse.setId(1);
        tempCourse.setTopic(new Topic(1, "INFORMATICS"));
        tempCourse.setCapacity(30);
        tempCourse.setSemesterStart(2);
        tempCourse.setSemesterDuration(1);
        tempCourse.setDescription("This subject shows how computer works");
        tempCourse.setTeacher(null);
        tempCourse.setName("Computer design");
    }

    @Test
    public void validateCapacity() {
        when(courseRepo.findById(any())).thenReturn(Optional.ofNullable(course));
        Course c = courseService.findById(1);
        Assert.assertTrue(c.getCapacity() >= 10);
    }

    @Test
    public void findById_Should_Return_Course(){
        when(courseRepo.findById(any())).thenReturn(Optional.ofNullable(course));
        Course c = courseService.findById(1);
        Assert.assertNotNull(c);
    }

    @Test
    public void updateCourse(){
        when(courseRepo.findById(any())).thenReturn(Optional.ofNullable(course));
        when(courseRepo.save(any(Course.class))).thenAnswer(i -> i.getArguments()[0]);
        Course c = courseService.save(tempCourse);
        Assert.assertNotNull(c);
        Assert.assertEquals(c.getCapacity(), course.getCapacity());
        Assert.assertEquals(c.getName(), course.getName());
        Assert.assertEquals(c.getDescription(), course.getDescription());
        Assert.assertEquals(c.getSemesterDuration(), course.getSemesterDuration());
        Assert.assertEquals(c.getSemesterStart(), course.getSemesterStart());
        Assert.assertEquals(c.getTopic(), course.getTopic());
        Assert.assertEquals(c.getTeacher(), course.getTeacher());
    }

    @Test
    public void findAll_Should_Return_Empty(){
        when(courseRepo.findAll()).thenReturn(new ArrayList());
        List<Course> courses = courseService.findAll();
        Assert.assertTrue(courses.isEmpty());
    }

}
