package com.example.chatapplication.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.chatapplication.model.Status;

public interface StatusRepository extends JpaRepository<Status, Integer> {

}
