package com.example.faculty.controller;

import com.example.faculty.entety.Course;
import com.example.faculty.entety.Student;
import com.example.faculty.entety.Teacher;
import com.example.faculty.entety.paging.Paged;
import com.example.faculty.enums.ROLE;
import com.example.faculty.service.CourseService;
import com.example.faculty.service.RoleService;
import com.example.faculty.service.TopicService;
import com.example.faculty.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.faculty.util.Constants.DEFAULT_PAGE_SIZE;

@Controller
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    CourseService courseService;

    @Autowired
    TopicService topicService;

    @GetMapping("/admin")
    public String adminGet() {
        return "admin/adminPersonalPage";
    }

    @GetMapping("/admin/teachers")
    public String teachersGet(Model model, @RequestParam(value = "name", defaultValue = "") String name) {

        List<Teacher> teachers = name.isEmpty()
                ? userService.findAllTeacher()
                : userService.findTeachersByPIB(name);

        model.addAttribute("teachers", teachers);
        return "admin/teachersList";
    }

    @GetMapping("/admin/teachers/{teacherId}")
    public String teacherGet(Model model,
                             @PathVariable("teacherId") Integer teacherId) {
        model.addAttribute("courses", courseService.findAllTeacherCourses(teacherId));
        model.addAttribute("coursesList", courseService.findAll());
        model.addAttribute("teacherId", teacherId);
        return "/admin/teacherInfo";
    }

    @PostMapping("/admin/teachers/{teacherId}")
    public String teacherPost(@PathVariable("teacherId") Integer teacherId,
                              @RequestParam("courseId") Integer courseId) {
        courseService.addTeacherToCourse(courseId, teacherId);
        return "redirect:/admin/teachers/{teacherId}";
    }

    @GetMapping("/admin/teachers/{teacherId}/course-delete/{courseId}")
    public String teacherDeleteCourseGet(@PathVariable("teacherId") Integer teacherId,
                                         @PathVariable("courseId") Integer courseId) {
        courseService.deleteTeacherFromCourse(courseId);
        return "redirect:/admin/teachers/{teacherId}";
    }


    @GetMapping("/admin/teachers/register")
    public String registerTeacherGet(Model model) {
        model.addAttribute("teacherForm", new Teacher());
        return "admin/teacherRegister";
    }

    @PostMapping("/admin/teachers/register")
    public String registerTeacherPost(@ModelAttribute("teacherForm") Teacher teacherForm) {
        userService.save(teacherForm, roleService.findByName(ROLE.ROLE_TEACHER.name()));
        return "redirect:/admin";
    }

    @GetMapping("/admin/courses")
    public String coursesGet(Model model,
                             @PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable,
                             @RequestParam(value = "courseName", defaultValue = "") String courseName,
                             @RequestParam(value = "duration", defaultValue = "-1") Integer duration,
                             @RequestParam(value = "capacity", defaultValue = "-1") Integer capacity,
                             @RequestParam(value = "topic", defaultValue = "") String topic,
                             @RequestParam(value = "teacher", defaultValue = "") String teacher) {
        Page<Course> pages = courseService.setCourses(courseName, duration, capacity, topic, teacher, pageable);
        model.addAttribute("courses", pages.getContent());
        return "admin/coursesList";
    }

    @GetMapping("/admin/courses/create")
    public String createCourseGet(Model model) {
        return "admin/courseCreate";
    }

    @PostMapping("/admin/courses/create")
    public String createCoursePost(
            @RequestParam("name") String name,
            @RequestParam("topic") String topic,
            @RequestParam("capacity") Integer capacity,
            @RequestParam("semesterStart") Integer semesterStart,
            @RequestParam("semesterDuration") Integer semesterDuration,
            @RequestParam("description") String description,
            @RequestParam("teacherName") String teacherName) {
        Course course = new Course();
        course.setName(name);
        course.setTopic(topicService.findByName(topic));
        course.setCapacity(capacity);
        course.setSemesterStart(semesterStart);
        course.setSemesterDuration(semesterDuration);
        course.setDescription(description);
        course.setTeacher(userService.findTeacherByPIB(teacherName));
        courseService.save(course);
        return "redirect:/admin";
    }

    @GetMapping("/admin/courses/{id}")
    public String courseInfoGet(Model model,
                                @PathVariable("id") Integer id) {
        model.addAttribute("course", courseService.findById(id));
        return "/admin/courseInfo";
    }

    @PostMapping(value = "/admin/courses/{id}", params = "action=save")
    public String courseInfoEditPost(@PathVariable("id") Integer id, @RequestParam("name") String name) {
        Course course = courseService.findById(id);
        course.setName(name);
        courseService.save(course);
        return "redirect:/admin/courses";
    }

    @PostMapping(value = "/admin/courses/{id}", params = "action=delete")
    public String courseInfoDeletePost(@PathVariable("id") Integer id) {
        courseService.deleteCourseById(id);
        return "redirect:/admin/courses";
    }

    @GetMapping("/admin/students")
    public String studentsGet(Model model,
                              @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                              @RequestParam(value = "size", required = false, defaultValue = "2") int size,
                              @RequestParam(value = "name", defaultValue = "") String name) {
        model.addAttribute("name", name);
        model.addAttribute("students", userService.getStudentsPage(name, pageNumber, size));
        return "/admin/studentList";
    }

    @GetMapping("/admin/students/{id}")
    public String studentGet(Model model, @PathVariable("id") int id) {
        model.addAttribute("student", userService.findStudentById(id));
        return "/admin/studentInfo";
    }

    @PostMapping("/admin/students/{id}")
    public String studentPost(@PathVariable("id") int id) {
        Student student = userService.findStudentById(id);
        student.setEnable(!student.isEnable());
        userService.save(student);
        return "redirect:/admin/students";
    }


}
