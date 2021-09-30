package com.example.faculty.entety;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="status")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Status {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "status")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<StudentHasCourse> studentHasCourses;

}
