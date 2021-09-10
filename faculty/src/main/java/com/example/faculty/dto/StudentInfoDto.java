package com.example.faculty.dto;

import com.example.faculty.entety.Student;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class StudentInfoDto {

    Student student;
    Integer mark;
    Timestamp recordingTime;

}
