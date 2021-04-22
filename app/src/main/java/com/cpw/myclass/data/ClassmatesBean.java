package com.cpw.myclass.data;

public class ClassmatesBean {
    public String name;
    public String phone_number;
    public String student_number;
    public ClassmatesType type;
    public char firstLetter;

    public ClassmatesBean(String name, String phone_number, String student_number, ClassmatesType type) {
        this.name = name;
        this.phone_number = phone_number;
        this.student_number = student_number;
        this.type = type;
    }

    public char getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(char firstLetter) {
        this.firstLetter = firstLetter;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(ClassmatesType type) {
        this.type = type;
    }

    public ClassmatesType getType() {
        return type;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getStudent_number() {
        return student_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setStudent_number(String student_number) {
        this.student_number = student_number;
    }

}
