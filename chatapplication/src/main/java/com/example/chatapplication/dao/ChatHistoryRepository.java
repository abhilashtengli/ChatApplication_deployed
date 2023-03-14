package com.example.chatapplication.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.chatapplication.model.ChatHistory;

public interface ChatHistoryRepository extends JpaRepository<ChatHistory,Integer> {


    @Query(value = "Select * from tbl_chat_history where sender_id = :senderId and status_id = 1", nativeQuery = true)
    List<ChatHistory> getChatByUserId(Integer senderId);

    @Query(value = "Select * from tbl_chat_history where (sender_id = :user1 and receiver_id = :user2)" + 
    "or (sender_id = :user2 and receiver_id = :user1) and status_id = 1 order by created_time", nativeQuery = true)
    List<ChatHistory> getConversation(Integer user1, Integer user2);
    
}
