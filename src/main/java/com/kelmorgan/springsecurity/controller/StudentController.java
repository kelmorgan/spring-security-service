package com.kelmorgan.springsecurity.controller;

import com.kelmorgan.springsecurity.domain.Student;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {


    private static List<Student> STUDENTS = List.of(Student.builder().id(1).name("John James").build()
            , Student.builder().id(2).name("James Doe").build()
            , Student.builder().id(3).name("Sarah Jones").build()
            , Student.builder().id(4).name("Peter Steve").build()
            , Student.builder().id(5).name("Philip Lockwood").build());

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Student getStudent(@PathVariable int id) {
        return STUDENTS.stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Student with id: " + id + " does not exists"));
    }


}
