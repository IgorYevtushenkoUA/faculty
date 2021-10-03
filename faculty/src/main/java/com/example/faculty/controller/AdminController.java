package com.example.faculty.controller;

import com.example.faculty.entety.Course;
import com.example.faculty.entety.Student;
import com.example.faculty.entety.Teacher;
import com.example.faculty.enums.ROLE;
import com.example.faculty.service.CourseService;
import com.example.faculty.service.RoleService;
import com.example.faculty.service.TopicService;
import com.example.faculty.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.faculty.util.Methods.getRole;

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

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("role", getRole());
    }

    @GetMapping("/admin")
    public String adminGet() {
        return "/users/admin/adminPersonalPage";
    }

    @GetMapping("/admin/teachers")
    public String teachersGet(Model model, @RequestParam(value = "name", defaultValue = "") String name) {

        List<Teacher> teachers = name.isEmpty()
                ? userService.findAllTeacher()
                : userService.findTeachersByPIB(name);

        model.addAttribute("teachers", teachers);
        return "/users/admin/teachersList";
    }

    @GetMapping("/admin/teachers/{teacherId}")
    public String teacherGet(Model model,
                             @PathVariable("teacherId") int teacherId) {
        model.addAttribute("courses", courseService.findAllTeacherCourses(teacherId));
        model.addAttribute("coursesList", courseService.findCoursesWithoutTeacher());
        model.addAttribute("teacher", userService.findTeacherById(teacherId));
        return "/users/admin/teacherInfo";
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
        return "/users/admin/create/teacherRegister";
    }

    @PostMapping("/admin/teachers/register")
    public String registerTeacherPost(@ModelAttribute("teacherForm") @Valid Teacher teacherForm, BindingResult bindingResult, Model model) {
        System.out.println("teacherForm : " + teacherForm);
        if (bindingResult.hasErrors()) {
            System.out.println("teacherFormError : " + teacherForm);
            return "/users/admin/create/teacherRegister";
        }
        if (userService.findByEmail(teacherForm.getEmail()) == null) {
            userService.save(teacherForm, roleService.findByName(ROLE.ROLE_TEACHER.name()));
            return "redirect:/admin";
        }
        model.addAttribute("message", "User with this email is exist");
        return "/users/admin/create/teacherRegister";
    }

    @GetMapping("/admin/courses")
    public String coursesGet(Model model,
                             @RequestParam(value = "courseName", defaultValue = "") String courseName,
                             @RequestParam(value = "duration", defaultValue = "0") Integer duration,
                             @RequestParam(value = "capacity", defaultValue = "0") Integer capacity,
                             @RequestParam(value = "topic", defaultValue = "...") String topic,
                             @RequestParam(value = "teacher", defaultValue = "") String teacher,
                             @RequestParam(value = "sortType", defaultValue = "ASC") String sortType,
                             @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                             @RequestParam(value = "size", required = false, defaultValue = "2") int size) {
        model.addAttribute("courseName", courseName);
        model.addAttribute("duration", duration);
        model.addAttribute("capacity", capacity);
        model.addAttribute("topic", topic);
        model.addAttribute("teacher", teacher);
        model.addAttribute("sortType", sortType);
        model.addAttribute("courses", courseService.getPage(courseName, duration, capacity, topic, teacher, pageNumber, size, sortType));
        return "/users/admin/coursesList";
    }

    @GetMapping("/admin/courses/create")
    public String createCourseGet(Model model) {
        model.addAttribute("topics", topicService.findAll());
        model.addAttribute("teachers", userService.findAllTeacher());
        model.addAttribute("courseForm", new Course());
        return "/users/admin/create/courseCreate";
    }

    @PostMapping("/admin/courses/create")
    public String createCoursePost(Model model,
                                   @RequestParam("topicId") int topicId,
                                   @RequestParam("teacherId") int teacherId,
                                   @ModelAttribute("courseForm") @Valid Course courseForm, BindingResult bindingResult) {

        System.out.println("bindingResult.hasErrors() :" + bindingResult.hasErrors());
        if (bindingResult.hasErrors()) {
            model.addAttribute("topics", topicService.findAll());
            model.addAttribute("teachers", userService.findAllTeacher());
            return "users/admin/create/courseCreate";
        }

        courseForm.setTopic(topicService.findById(topicId));
        courseForm.setTeacher(userService.findTeacherById(teacherId));
        courseService.save(courseForm);
        return "redirect:/admin";
    }

    @GetMapping("/admin/courses/{id}")
    public String courseInfoGet(Model model,
                                @PathVariable("id") Integer id) {
        model.addAttribute("course", courseService.findById(id));
        model.addAttribute("topics", topicService.findAll());
        model.addAttribute("teachers", userService.findAllTeacher());
        return "/users/admin/courseInfo";
    }

    @PostMapping(value = "/admin/courses/{id}", params = "action=save")
    public String courseInfoEditPost(@PathVariable("id") int id,
                                     @RequestParam("name") String name,
                                     @RequestParam("topicId") int topicId,
                                     @RequestParam("capacity") int capacity,
                                     @RequestParam("semesterStart") int semesterStart,
                                     @RequestParam("semesterDuration") int semesterDuration,
                                     @RequestParam("description") String description,
                                     @RequestParam("teacherId") int teacherId) {
        Course course = courseService.findById(id);
        course.setName(name);
        course.setTopic(topicService.findById(topicId));
        course.setCapacity(capacity);
        course.setSemesterStart(semesterStart);
        course.setSemesterDuration(semesterDuration);
        course.setDescription(description);
        course.setTeacher(userService.findTeacherById(teacherId));
        courseService.save(course);
        return "redirect:/courses/{id}";
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
        return "/users/admin/studentList";
    }

    @GetMapping("/admin/students/{id}")
    public String studentGet(Model model, @PathVariable("id") int id) {
        model.addAttribute("student", userService.findStudentById(id));
        return "/users/admin/studentInfo";
    }

    @PostMapping("/admin/students/{id}")
    public String studentPost(@PathVariable("id") int id) {
        Student student = userService.findStudentById(id);
        student.setEnable(!student.isEnable());
        userService.save(student);
        return "redirect:/admin/students";
    }

    @GetMapping("/admin/students/create")
    public String studentRegisterGet(Model model) {
        model.addAttribute("userForm", new Student());
        return "/users/admin/create/studentRegister";
    }

    @PostMapping("/admin/students/create")
    public String studentRegisterPost(@ModelAttribute("userForm") @Valid Student userForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/users/admin/create/studentRegister";
        }
        if (userService.findByEmail(userForm.getEmail()) == null) {
            userForm.setEnable(true);
            userService.save(userForm, roleService.findByName(ROLE.ROLE_STUDENT.name()));
            return "redirect:/admin/students/create";
        }
        model.addAttribute("message", "User with this email exists");
        return "/users/admin/create/studentRegister";
    }
}
