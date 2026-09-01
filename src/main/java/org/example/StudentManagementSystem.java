package org.example;
import java.util.Map;
import java.util.Scanner;

public class StudentManagementSystem {
    private static final Scanner input = new Scanner(System.in);
    private static final StudentService studentService = new StudentService();

    public static void main(String[] args) {
        showMenu();
    }

    private static void showMenu() {
        int choice;

        do {
            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. Display All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Remove Student");
            System.out.println("6. Display Students by Department");
            System.out.println("7. Display Top Student");
            System.out.println("8. Display Student Map (HashMap)");
            System.out.println("9. Search Student Using Map");
            System.out.println("10. Display Students Using LinkedHashMap");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = input.nextInt();
                input.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                input.nextLine();
                choice = -1;
            }

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    displayAllStudents();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    removeStudent();
                    break;
                case 6:
                    displayStudentsByDepartment();
                    break;
                case 7:
                    displayTopStudent();
                    break;
                case 8:
                    displayStudentMap();
                    break;
                case 9:
                    searchStudentUsingMap();
                    break;
                case 10:
                    displayLinkedHashMap();
                    break;
                case 0:
                    System.out.println("Exiting Student Management System.");
                    break;
                default:
                    if (choice != -1) {
                        System.out.println("Invalid choice.");
                    }
            }
        } while (choice != 0);
    }

    private static void addStudent() {
        try {
            System.out.print("Enter Student ID: ");
            int id = input.nextInt();
            input.nextLine();

            System.out.print("Enter Student Name: ");
            String name = input.nextLine();

            System.out.print("Enter Age: ");
            int age = input.nextInt();
            input.nextLine();

            System.out.print("Enter Department: ");
            String department = input.nextLine();

            System.out.print("Enter Marks: ");
            double marks = input.nextDouble();
            input.nextLine();

            Student student = new Student(id, name, age, department, marks);
            studentService.addStudent(student);
            System.out.println("Student added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid input format.");
            input.nextLine();
        }
    }

    private static void displayAllStudents() {
        System.out.println("---------------------------------------------------------");
        System.out.printf("%-8s %-12s %-6s %-16s %-5s%n", "ID", "Name", "Age", "Department", "Marks");
        System.out.println("---------------------------------------------------------");

        for (Student student : studentService.getAllStudentRecords()) {
            System.out.printf("%-8d %-12s %-6d %-16s %.1f%n",
                    student.getId(), student.getName(), student.getAge(), student.getDepartment(), student.getMarks());
        }

        System.out.println("---------------------------------------------------------");
    }

    private static void searchStudent() {
        System.out.print("Enter Student ID: ");
        int id = input.nextInt();
        input.nextLine();

        Student student = studentService.getStudentById(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Student Found");
        System.out.println();
        System.out.println("ID         : " + student.getId());
        System.out.println("Name       : " + student.getName());
        System.out.println("Age        : " + student.getAge());
        System.out.println("Department : " + student.getDepartment());
        System.out.println("Marks      : " + student.getMarks());
    }

    private static void updateStudent() {
        try {
            System.out.print("Enter Student ID: ");
            int id = input.nextInt();
            input.nextLine();

            if (studentService.getStudentById(id) == null) {
                System.out.println("Student not found.");
                return;
            }

            System.out.print("Enter New Name: ");
            String name = input.nextLine();

            System.out.print("Enter New Age: ");
            int age = input.nextInt();
            input.nextLine();

            System.out.print("Enter New Department: ");
            String department = input.nextLine();

            System.out.print("Enter New Marks: ");
            double marks = input.nextDouble();
            input.nextLine();

            studentService.updateStudentById(id, name, age, department, marks);
            System.out.println("Student updated successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid input format.");
            input.nextLine();
        }
    }

    private static void removeStudent() {
        System.out.print("Enter Student ID: ");
        int id = input.nextInt();
        input.nextLine();

        if (studentService.removeStudent(id)) {
            System.out.println("Student removed successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void displayStudentsByDepartment() {
        System.out.print("Enter Department: ");
        String department = input.nextLine();

        System.out.println("--------- " + department.toUpperCase() + " STUDENTS ---------");
        for (Student student : studentService.getStudentsByDepartment(department)) {
            System.out.printf("%-5d %-8s %-8s %.1f%n",
                    student.getId(), student.getName(), student.getDepartment(), student.getMarks());
        }
    }

    private static void displayTopStudent() {
        Student student = studentService.getTopStudent();
        if (student == null) {
            System.out.println("No student records available.");
            return;
        }

        System.out.println("====================================");
        System.out.println("          TOP STUDENT");
        System.out.println("====================================");
        System.out.println();
        System.out.println("Student ID : " + student.getId());
        System.out.println("Name       : " + student.getName());
        System.out.println("Department : " + student.getDepartment());
        System.out.println("Marks      : " + student.getMarks());
        System.out.println();
        System.out.println("====================================");
    }

    private static void displayStudentMap() {
        System.out.println("====================================");
        System.out.println("          STUDENT MAP");
        System.out.println("====================================");

        for (Map.Entry<Integer, String> entry : studentService.getStudentMap().entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    private static void searchStudentUsingMap() {
        System.out.print("Enter Student ID: ");
        int id = input.nextInt();
        input.nextLine();

        Map<Integer, String> studentMap = studentService.getStudentMap();
        if (!studentMap.containsKey(id)) {
            System.out.println("Student not found.");
            return;
        }

        Student student = studentService.getStudentById(id);
        System.out.println("Student Found");
        System.out.println();
        System.out.println("ID         : " + student.getId());
        System.out.println("Name       : " + student.getName());
        System.out.println("Department : " + student.getDepartment());
        System.out.println("Marks      : " + student.getMarks());
    }

    private static void displayLinkedHashMap() {
        System.out.println("Insertion Order Maintained");
        for (Map.Entry<Integer, String> entry : studentService.getStudentLinkedHashMap().entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}
