package com.example.faculty.entety;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name = "student")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Student extends User {

    @Column(name = "course_num")
    private int courseNum;

    @Column(name = "enable")
    private boolean enable;

    public String toString() {
        return "Student:\n" + super.toString() + "\nCourseNum: " + getCourseNum() + "\nEnable: " + isEnable();
    }

}
