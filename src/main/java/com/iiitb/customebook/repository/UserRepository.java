package com.iiitb.customebook.repository;

import com.iiitb.customebook.bean.Book;
import com.iiitb.customebook.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    @Query(value = "SELECT * FROM user u WHERE u.email =:email", nativeQuery = true)
    public User findByEmail(@Param("email") String email);
}