package com.studentservice.demo.repo;

import com.studentservice.demo.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
@Transactional
public interface TokenRepo extends JpaRepository<ConfirmationToken, Long> {


    Optional<ConfirmationToken> findByToken(String token);


    @Modifying
    @Query("UPDATE ConfirmationToken SET confirmedAt=CURRENT_TIMESTAMP WHERE token=:token")
    void confirmToken(String token);


}
