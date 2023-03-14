package com.example.chatapplication.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chatapplication.dao.ChatHistoryRepository;
import com.example.chatapplication.model.ChatHistory;

@Service
public class ChatHistoryService {

    @Autowired
    ChatHistoryRepository chatHistoryRepository;

    public int saveMessage(ChatHistory chat) {
        ChatHistory chatHistory = chatHistoryRepository.save(chat);
        return chatHistory.getChatid();
    }

    public JSONObject getChatByUserId(Integer senderId) {
        List<ChatHistory> chatList = chatHistoryRepository.getChatByUserId(senderId);
        JSONObject response = new JSONObject();

        if (!chatList.isEmpty()) {
            response.put("senderId", chatList.get(0).getSender().getUserId());
            response.put("senderName", chatList.get(0).getSender().getUsername());
        }
        JSONArray receivers = new JSONArray();
        for (ChatHistory chats : chatList) {
            JSONObject receiverObj = new JSONObject();
            receiverObj.put("receiverId", chats.getReceiver().getUserId());
            receiverObj.put("reciverName", chats.getReceiver().getUsername());
            receiverObj.put("message", chats.getMessage());
            receivers.put(receiverObj);
        }

        response.put("receivers", receivers);
        return response;
    }

    public JSONObject getConversation(Integer user1, Integer user2) {
        List<ChatHistory> chatList = chatHistoryRepository.getConversation(user1, user2);
        JSONObject response = new JSONObject();
        JSONArray conversation = new JSONArray();

        for (ChatHistory chat : chatList) {
            JSONObject messageObj = new JSONObject();
            messageObj.put("chatId", chat.getChatid());
            messageObj.put("TimeStamp", chat.getCreatedDate());
            messageObj.put("senderName", chat.getSender().getFirstname());
            messageObj.put("message", chat.getMessage());
            conversation.put(messageObj);
        }
        response.put("conversation", conversation);
        return response;
    }

}
