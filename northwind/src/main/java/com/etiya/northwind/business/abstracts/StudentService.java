package com.etiya.northwind.business.abstracts;

import com.etiya.northwind.entities.concretes.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentService {
    List<Student> students=new ArrayList<Student>();

    public StudentService(){
        students.add(new Student(1,"Engin"));
        students.add(new Student(2,"mert"));
        students.add(new Student(3,"sehl"));
        students.add(new Student(4,"burcu"));
    }
    public void addStudents(Student student){
        if(!checkIfStudentName(student.getName())) {
            students.add(student);

        }

    }

    public boolean checkIfStudentName(String name){
        boolean exits=false;
        for(Student student:students){
            if(student.getName()==name){
                exits=true;
            }
        }
        return exits;
    }

    public int size(){
        return this.students.size();
    }


}
