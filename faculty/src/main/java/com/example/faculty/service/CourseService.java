package com.example.faculty.service;

import com.example.faculty.entety.Course;
import com.example.faculty.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public Page<Course> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable);
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


}
