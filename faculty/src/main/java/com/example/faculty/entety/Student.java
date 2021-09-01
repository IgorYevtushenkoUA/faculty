package com.example.faculty.entety;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="student")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Student extends User{

    @Column(name="course_num")
    private int courseNum;

    @Column(name="enable")
    private boolean enable;

}
