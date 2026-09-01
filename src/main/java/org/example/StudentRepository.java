package org.example;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface StudentRepository {
    void addStudent(Student student);
    List<Student> getAllStudents();
    Student getStudentById(int id);
    void updateStudent(int id, Student updatedStudent);
    boolean removeStudent(int id);
    List<Student> getStudentsByDepartment(String department);
    Student getTopStudent();
    Map<Integer, String> getStudentMap();
    LinkedHashMap<Integer, String> getStudentLinkedHashMap();
}
