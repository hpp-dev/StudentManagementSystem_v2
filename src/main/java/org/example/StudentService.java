package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StudentService implements StudentRepository {
    private final Map<Integer, Student> studentStore = new HashMap<>();

    public StudentService() {
    }

    @Override
    public void addStudent(Student student) {
        validateStudent(student);

        if (studentStore.containsKey(student.getId())) {
            throw new IllegalArgumentException("Student ID already exists.");
        }

        studentStore.put(student.getId(), student);
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>(studentStore.values());

        // Sort students by student ID in ascending order using classic Java comparator logic
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                if (s1.getId() < s2.getId()) {
                    return -1;
                } else if (s1.getId() > s2.getId()) {
                    return 1;
                }
                return 0;
            }
        });

        return students;
    }

    @Override
    public Student getStudentById(int id) {
        return studentStore.get(id);
    }

    @Override
    public void updateStudent(int id, Student updatedStudent) {
        if (studentStore.containsKey(id) && updatedStudent != null) {
            updatedStudent.setId(id);
            studentStore.put(id, updatedStudent);
        }
    }

    @Override
    public boolean removeStudent(int id) {
        return studentStore.remove(id) != null;
    }

    @Override
    public List<Student> getStudentsByDepartment(String department) {
        List<Student> filteredStudents = new ArrayList<>();
        for (Student student : studentStore.values()) {
            if (student.getDepartment().equalsIgnoreCase(department)) {
                filteredStudents.add(student);
            }
        }

        // Sort matching department students by student ID using classic comparator logic
        Collections.sort(filteredStudents, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                if (s1.getId() < s2.getId()) {
                    return -1;
                } else if (s1.getId() > s2.getId()) {
                    return 1;
                }
                return 0;
            }
        });

        return filteredStudents;
    }

    @Override
    public Student getTopStudent() {
        if (studentStore.isEmpty()) {
            return null;
        }

        Student topStudent = null;
        for (Student student : studentStore.values()) {
            if (topStudent == null || student.getMarks() > topStudent.getMarks()) {
                topStudent = student;
            }
        }
        return topStudent;
    }

    @Override
    public Map<Integer, String> getStudentMap() {
        Map<Integer, String> studentMap = new HashMap<>();
        for (Student student : getAllStudents()) {
            studentMap.put(student.getId(), student.getName());
        }
        return studentMap;
    }

    @Override
    public LinkedHashMap<Integer, String> getStudentLinkedHashMap() {
        LinkedHashMap<Integer, String> linkedMap = new LinkedHashMap<>();
        for (Student student : getAllStudents()) {
            linkedMap.put(student.getId(), student.getName());
        }
        return linkedMap;
    }

    public void updateStudentById(int id, String newName, int newAge, String newDepartment, double newMarks) {
        Student existingStudent = studentStore.get(id);
        if (existingStudent == null) {
            throw new IllegalArgumentException("Student not found.");
        }

        Student updatedStudent = new Student();
        updatedStudent.setId(id);
        updatedStudent.setName(newName);
        updatedStudent.setAge(newAge);
        updatedStudent.setDepartment(newDepartment);
        updatedStudent.setMarks(newMarks);

        validateStudent(updatedStudent);
        studentStore.put(id, updatedStudent);
    }

    public List<Student> getAllStudentRecords() {
        return getAllStudents();
    }

    private void validateStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Invalid Student ID.");
        }

        if (student.getId() <= 0) {
            throw new IllegalArgumentException("Invalid Student ID.");
        }

        if (student.getName() == null || student.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }

        if (student.getAge() <= 0 || student.getAge() > 120) {
            throw new IllegalArgumentException("Invalid Age.");
        }

        if (student.getMarks() < 0 || student.getMarks() > 100) {
            throw new IllegalArgumentException("Marks should be between 0 and 100.");
        }
    }
}
