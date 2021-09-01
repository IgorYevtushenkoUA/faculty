package com.example.faculty.entety;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="student_has_course")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class StudentHasCourse {

    @EmbeddedId
    private StudentCourseId studentCourseId;

    @Column(name="mark")
    private int mark;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="status_id")
    private Status status;

}

