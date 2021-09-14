package com.example.faculty.service;

import com.example.faculty.entety.Course;
import com.example.faculty.entety.paging.Paged;
import com.example.faculty.entety.paging.Paging;
import com.example.faculty.repository.CourseRepository;
import com.example.faculty.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import javax.transaction.Transactional;
import java.util.List;

import static com.example.faculty.util.Constants.EMPTY_INTEGER_VALUE;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    public Page<Course> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course findById(int id) {
        return courseRepository.findById(id);
    }

    public void save(Course course) {
        courseRepository.save(course);
    }

    public void update(Course course) {
        Course c = courseRepository.findById(course.getId());
        if (c != null) {
            c.setTopic(course.getTopic());
            c.setCapacity(course.getCapacity());
            c.setSemesterStart(course.getSemesterStart());
            c.setSemesterDuration(course.getSemesterDuration());
            c.setDescription(course.getDescription());
            c.setTeacher(course.getTeacher());
            c.setName(course.getName());
            courseRepository.save(c);
        }
        // todo send response that can not save
    }

    public Page<Course> findAllByParams(List<String> courseName, List<Integer> duration,
                                        List<Integer> capacity, List<String> topic,
                                        List<Integer> teacherId, Pageable pageable) {
        return courseRepository.findAllByParams(courseName, duration, capacity,
                topic, teacherId, pageable);
    }

    public List<Course> findAllTeacherCourses(int id) {
        return courseRepository.findAllTeacherCourses(id);
    }

    public List<String> findAllCourseNames() {
        return courseRepository.findAllCourseNames();
    }

    public List<Integer> findAllDurations() {
        return courseRepository.findAllDurations();
    }

    public List<Integer> findAllCapacities() {
        return courseRepository.findAllCapacities();
    }

    public List<String> findAllTopics() {
        return courseRepository.findAllTopics();
    }

    public List<Integer> findAllTeacherNames() {
        return courseRepository.findAllTeacherNames();
    }

    public List<Integer> findTeacherIdByName(String name) {
        return courseRepository.findTeacherIdByName(name);
    }

    public List<String> findCourseNameByName(String name) {
        return courseRepository.findCourseNameByName(name);
    }

    public Paged<Course> getPage(String courseName, Integer duration, Integer capacity, String topic, String teacher,int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber - 1, size);
        Page<Course> postPage = setCourses(courseName, duration, capacity, topic, teacher,request);
        return new Paged<>(postPage, Paging.of(postPage.getTotalPages(), pageNumber, size));
    }

    public Page<Course> setCourses(String courseName, Integer duration, Integer capacity, String topic, String teacher, Pageable pageable) {
        if (courseName.isEmpty() && duration == EMPTY_INTEGER_VALUE && capacity == EMPTY_INTEGER_VALUE
                && topic.isEmpty() && teacher.isEmpty()) {
            return findAll(pageable);
        }
        return findAllByParams(setCourseNameParam(courseName), setDurationParam(duration), setCapacityParam(capacity),
                setTopicsParam(topic), setTeacherNameParam(teacher), pageable);
    }

    public List<String> setCourseNameParam(String courseName) {
        return courseName.isEmpty() ? findAllCourseNames() : findCourseNameByName(courseName);
    }

    public List<Integer> setDurationParam(Integer duration) {
        return duration == EMPTY_INTEGER_VALUE ? findAllDurations() : List.of(duration);
    }

    public List<Integer> setCapacityParam(Integer capacity) {
        return capacity == EMPTY_INTEGER_VALUE ? findAllCapacities() : List.of(capacity);
    }

    public List<String> setTopicsParam(String topics) {
        return topics.isEmpty() ? findAllTopics() : List.of(topics);
    }

    public List<Integer> setTeacherNameParam(String teacherName) {
        return teacherName.isEmpty()
                ? findAllTeacherNames()
                : findTeacherIdByName(teacherName);
    }

    public Course deleteTeacherFromCourse(int id) {
        Course course = courseRepository.findById(id);
        if (course != null)
            course.setTeacher(null);
        return course;
    }


    public Course addTeacherToCourse(int courseId, int teacherId){
        Course course = courseRepository.findById(courseId);
        if (course != null)
            course.setTeacher(userRepository.findTeacherById(teacherId));
        return course;
    }

    public void deleteCourseById(int id){
        courseRepository.deleteById(id);
    }

    public List<Course> findAllStudentCoursesByType(int id, String statusName){
        return courseRepository.findAllStudentCoursesByType(id, statusName);
    }



}
