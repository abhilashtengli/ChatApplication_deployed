package com.example.chatapplication.controller;

import java.sql.Timestamp;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatapplication.dao.StatusRepository;
import com.example.chatapplication.dao.UserRepository;
import com.example.chatapplication.model.ChatHistory;
import com.example.chatapplication.model.Status;
import com.example.chatapplication.model.Users;
import com.example.chatapplication.service.ChatHistoryService;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatHistoryController {

    @Autowired
    ChatHistoryService chatHistoryService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StatusRepository statusRepository;

    @PostMapping("/send-message")
    public ResponseEntity<String> saveMessage(@RequestBody String requestData) {
        JSONObject requestObj = new JSONObject(requestData);
        JSONObject errorList = validateRequest(requestObj);
        if (errorList.isEmpty()) {

            ChatHistory chat = setChatHistory(requestObj);
            if (chat.getStatusId() == null) {
                return new ResponseEntity<>("The user is Inactive", HttpStatus.BAD_REQUEST);
            }
            int chatId = chatHistoryService.saveMessage(chat);
            return new ResponseEntity<>("message sent " + chatId, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(errorList.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-chat")
    public ResponseEntity<String> getChatByUserId(@RequestParam Integer userId) {
        JSONObject chat = chatHistoryService.getChatByUserId(userId);
        return new ResponseEntity<>(chat.toString(), HttpStatus.OK);

    }

    @GetMapping("/get-conversation")
    public ResponseEntity<String> getConversation(@RequestParam Integer user1, @RequestParam Integer user2) {
        JSONObject chats = chatHistoryService.getConversation(user1, user2);
        return new ResponseEntity<>(chats.toString(), HttpStatus.OK);
    }


    private ChatHistory setChatHistory(JSONObject requestObj) {
        ChatHistory chat = new ChatHistory();

        String message = requestObj.getString("message");
        int senderId = requestObj.getInt("sender");
        int receiverId = requestObj.getInt("receiver");
        Users senderUserObj = userRepository.findById(senderId).get();
        Status statusId1 = senderUserObj.getStatusId();
        System.out.println(statusId1.getStatusId());
        if (statusId1.getStatusId() == 2) {
            return chat;
        }
        Users receiverUserObj = userRepository.findById(receiverId).get();
        Status statusId2 = receiverUserObj.getStatusId();
        if (statusId2.getStatusId() == 2) {
            return chat;
        }

        chat.setReceiver(receiverUserObj);
        chat.setSender(senderUserObj);
        chat.setMessage(message);
        Status statusId = statusRepository.findById(1).get();
        chat.setStatusId(statusId);

        Timestamp createdTime = new Timestamp(System.currentTimeMillis());
        chat.setCreatedDate(createdTime);

        return chat;
    }

    private JSONObject validateRequest(JSONObject requestObj) {
        JSONObject errorObj = new JSONObject();
        if (!requestObj.has("sender")) {
            errorObj.put("sender", "Missing parameter");
        }
        if (!requestObj.has("receiver")) {
            errorObj.put("receiver", "Missing parameter");
        }
        if (requestObj.has("message")) {
            String message = requestObj.getString("message");
            if (message.isBlank() || message.isEmpty()) {
                errorObj.put("message", "message body can't be Empty");
            }
        } else {
            errorObj.put("message", "Missing parameter");
        }
        return errorObj;
    }
}
