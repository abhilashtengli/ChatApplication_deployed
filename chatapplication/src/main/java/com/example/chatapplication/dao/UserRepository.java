package com.example.chatapplication.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.chatapplication.model.Users;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<Users, Integer> {

    @Query(value = "Select * from tbl_user where username = :username and status_id = 1", nativeQuery = true)
    public List<Users> findByUserusername(String username);

    @Query(value = "select * from tbl_user where user_id = :userId and status_id = 1", nativeQuery = true)
    public List<Users> getUserByUserId(Integer userId);

    @Query(value = "select * from tbl_user where status_id = 1", nativeQuery = true)
    public List<Users> getAllUsers();

    @Modifying
    @Transactional
    // @Query(value = "update tb_user set status_id = 2 where userId = :userId",
    //         countQuery = "SELECT count(*) from tbl_user", nativeQuery = true)
      @Query(value = "update tbl_user set status_id = 2 where user_id = :userId",
            countQuery = "SELECT count(*) FROM tbl_user", nativeQuery = true)
    public void deleteByUserId(int userId);
}

