package com.example.springbootdemo.model;


//import jakarta.persistence.*;


//@Entity
//@Table(name = "user") // 对应数据库里的 student 表
public class Student   {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增
    private  int id;
    private  String name;
    private String email;
    private String password;


    public  Student(){}

    public Student(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
