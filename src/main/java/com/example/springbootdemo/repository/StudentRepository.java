package com.example.springbootdemo.repository;


import com.example.springbootdemo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    // 继承后，你自动拥有了 save(), findAll(), findById() 等方法
}
