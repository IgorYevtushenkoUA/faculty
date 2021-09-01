package com.example.faculty.entety;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class StudentCourseId implements Serializable {

    @Column(name="student_id")
    private int studentId;

    @Column(name="course_id")
    private int courseId;

}

