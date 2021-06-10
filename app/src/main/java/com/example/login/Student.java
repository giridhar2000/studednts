package com.example.login;

import org.jetbrains.annotations.NotNull;

public class Student {
    String name;
    String roll_no;
    String date_of_birth;
    String class_section;
    String class_teacher;


    public Student(String name, String roll_no, String date_of_birth, String class_section, String class_teacher) {
        this.name = name;
        this.roll_no = roll_no;
        this.date_of_birth = date_of_birth;
        this.class_section = class_section;
        this.class_teacher = class_teacher;
    }
    public Student(){}


    public String getName() {
        return name;
    }

    public String getroll_no() {
        return roll_no;
    }

    public String getdate_of_birth() {
        return date_of_birth;
    }

    public String getclass_section() {
        return class_section;
    }

    public String getclass_teacher() {
        return class_teacher;
    }

}
