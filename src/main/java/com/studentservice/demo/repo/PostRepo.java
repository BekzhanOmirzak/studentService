package com.studentservice.demo.repo;

import com.studentservice.demo.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepo extends JpaRepository<Post, Long> {


    @Query("SELECT post FROM Post post ORDER BY RANDOM()")
    List<Post> getListOfPostsRandomly(Pageable pageable);


}
