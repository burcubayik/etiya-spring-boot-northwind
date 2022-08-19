package com.etiya.northwind.businessTests;

import com.etiya.northwind.business.abstracts.MathService;
import com.etiya.northwind.business.abstracts.StudentService;
import com.etiya.northwind.entities.concretes.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StudentServiceTests {
    StudentService studentService;

    @BeforeEach
    public void setup(){
        studentService=new StudentService();
    }

    //yeni öğrenci eklenebilmeli
    @Test
    public void students_size_should_increase_one_when_added(){
     /*   Student student=new Student(5,"Aybüke");
        int expectedId=5;
        String expectedName="Aybüke";

        studentService.addStudents(student);
        int actual1=student.getId();
        String actual2=student.getName();



        //assert//then
        Assertions.assertEquals(expectedId,actual1);
        Assertions.assertEquals(expectedName,actual2);
*/

        Student student=new Student(5,"Aybüke");
        int studentSize=this.studentService.size();
        int expected=studentSize+1;

        this.studentService.addStudents(student);
        int actual=this.studentService.size();

        Assertions.assertEquals(expected,actual);



    }
    //öğenci isimleri aynı olmasın

    @Test
    public void student_name_is_same_with_the_input_name(){
        Student student=new Student(6,"Engin");
        boolean expected=true;


        boolean actual=this.studentService.checkIfStudentName(student.getName());

        Assertions.assertEquals(expected,actual);


        /*student_name_can_not_be_duplicated_when_added()
        hata beklmenin yöntemi
        Student student=new Student(6,"Engin");
        Executable executable=()->this.studentService.add(student);
        Assertions.assertEquals(BusinessException.class,executable);
        */

    }

    //mockito //managerları test et
    //veritabanına gitmek yasak
    //modelmapperı kullanabilirsin
    //repositoy kullanmak yasak
    //mock data yap bunu mockitoya yap
    //
}
