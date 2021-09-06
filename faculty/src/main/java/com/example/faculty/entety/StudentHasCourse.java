package com.example.faculty.entety;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="student_has_course")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class StudentHasCourse {

    @EmbeddedId
    private StudentCourseId studentCourseId;

    @Column(name="mark")
    private Integer mark;

    @Column(name="recording_time")
    private Timestamp recordingTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="status_id")
    private Status status;

}

