package com.example.faculty.entety;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="teacher")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Teacher extends User{

    @OneToMany(mappedBy = "topic")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Course> courses;
}
