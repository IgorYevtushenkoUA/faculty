package com.example.faculty.entety;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "course")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "semester_start")
    private int semesterStart;

    @Column(name = "semester_duration")
    private int semesterDuration;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Column(name = "name")
    @Pattern(regexp = "^\\w+( +\\w+)*", message = "Not valid course name")
    private String name;
}
