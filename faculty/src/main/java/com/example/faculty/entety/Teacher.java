package com.example.faculty.entety;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "teacher")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Teacher extends User {

    @OneToMany(mappedBy = "topic")
    @EqualsAndHashCode.Exclude
    private List<Course> courses;

    public String toString() {
        return "Teacher:\n" + super.toString() + "\n";
    }


}