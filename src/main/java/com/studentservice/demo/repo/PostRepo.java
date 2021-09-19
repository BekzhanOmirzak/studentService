package com.studentservice.demo.repo;

import com.studentservice.demo.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepo extends JpaRepository<Post,Long> {
}
