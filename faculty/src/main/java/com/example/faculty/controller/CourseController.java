package com.example.faculty.controller;

import com.example.faculty.entety.Course;
import com.example.faculty.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.example.faculty.util.Constants.EMPTY_INTEGER_VALUE;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/courses")
    public String coursesGet(
            Model model,
            @RequestParam(value = "courseName", defaultValue = "") String courseName,
            @RequestParam(value = "duration", defaultValue = "-1") Integer duration,
            @RequestParam(value = "capacity", defaultValue = "-1") Integer capacity,
            @RequestParam(value = "topic", defaultValue = "") String topic,
            @RequestParam(value = "teacher", defaultValue = "") String teacher
    ) {
        List<Course> courses = setCourses(courseName, duration, capacity, topic, teacher);
        model.addAttribute("courses", courses);
        System.out.println(courses);
        return "courses";
    }

    private List<Course> setCourses(String courseName, Integer duration, Integer capacity, String topic, String teacher) {
        if (courseName.isEmpty() && duration == EMPTY_INTEGER_VALUE && capacity == EMPTY_INTEGER_VALUE && topic.isEmpty() && teacher.isEmpty())
            return courseService.findAll();
        System.out.println(setCourseNameParam(courseName));
        System.out.println(setDurationParam(duration));
        System.out.println(setCapacityParam(capacity));
        System.out.println(setTopicsParam(topic));
        System.out.println(setTeacherNameParam(teacher));
        return courseService.findAllByParams(setCourseNameParam(courseName), setDurationParam(duration),
                setCapacityParam(capacity), setTopicsParam(topic), setTeacherNameParam(teacher));
    }

    private List<String> setCourseNameParam(String courseName) {
        return courseName.isEmpty() ? courseService.findAllCourseNames() : courseService.findCourseNameByName(courseName);
    }

    private List<Integer> setDurationParam(Integer duration) {
        return duration == EMPTY_INTEGER_VALUE ? courseService.findAllDurations() : List.of(duration);
    }

    private List<Integer> setCapacityParam(Integer capacity) {
        return capacity == EMPTY_INTEGER_VALUE ? courseService.findAllCapacities() : List.of(capacity);
    }

    private List<String> setTopicsParam(String topics) {
        return topics.isEmpty() ? courseService.findAllTopics() : List.of(topics);
    }

    private List<Integer> setTeacherNameParam(String teacherName) {
        return teacherName.isEmpty() ? courseService.findAllTeacherNames() : courseService.findTeacherIdByName(teacherName);
    }
}
