package com.example.faculty.entety;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="teacher")
@NoArgsConstructor
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
