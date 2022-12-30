package com.kelmorgan.springsecurity.controller;

import com.kelmorgan.springsecurity.domain.Student;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("management/api/v1/student")
public class StudentMangementController {
    private final List<Student> studentList = new ArrayList<>(students());


    @ResponseStatus(OK)
    @GetMapping
    public List<Student> getAllStudents() {
        return studentList;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void registerNewStudent(@RequestBody Student student) {
        student.setId(
                studentList.stream()
                        .map(Student::getId)
                        .filter(id -> id + 1 > studentList.size())
                        .findFirst().get() + 1
        );

        studentList.add(student);
        System.out.println("Student " + student + " has been registered");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public void delete(@PathVariable Integer id) {
        studentList.remove(getStudent(id));
        System.out.println("Student with id " + id + " has been deleted");
    }

    @PutMapping("/{id}")
    @ResponseStatus(ACCEPTED)
    public void updateStudent(@PathVariable Integer id, @RequestBody Student student) {

        Student studentUpdated = getStudent(id);
        studentList.remove(studentUpdated);
        studentUpdated.setName(student.getName());
        studentList.add(studentUpdated);
        System.out.println("Student " + studentUpdated + " has been updated");
    }

    private Student getStudent(int id) {
        return studentList.stream()
                .filter(student -> id == student.getId())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("student with id " + id + " does not exist to be deleted"));
    }

    private List<Student> students() {
        return List.of(Student.builder().id(1).name("John James").build()
                , Student.builder().id(2).name("James Doe").build()
                , Student.builder().id(3).name("Sarah Jones").build()
                , Student.builder().id(4).name("Peter Steve").build()
                , Student.builder().id(5).name("Philip Lockwood").build());
    }


}
