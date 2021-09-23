package com.studentservice.demo.repo;

import com.studentservice.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
@Transactional
public interface StudentRepo extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);


    @Modifying
    @Query("UPDATE Student  SET enabled=true WHERE email=:email")
    void enableStudent(String email);


    Optional<Student> findByEmailAndPassword(String email, String password);

    void deleteByEmail(String email);





}
