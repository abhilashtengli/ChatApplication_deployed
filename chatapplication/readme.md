# Chat Application

## FrameWork and Language Used
- SpringBoot
- Java

## DataBase Used
- MySql

## Controller
- UserController
- StatusController
- ChatHistoryController

## Service
- UserService
- StatusService
- ChatHistoryService

## Model
- User
- Status
- ChatHistory

## Repository
- UserRepository
- StatusRepository
- ChatRepository

## Project Summary
- Here in this project we are performing CRUD operations and making possible to have conversation between two users 
  personally and saving the Data in the DataBase and as follows :

  ### User
  - PostMapping   -> http://localhost:8080/api/v1/user/create-user
  - PostMapping   -> http://localhost:8080/api/v1/user/login (To Login)
  - GetMapping    -> http://localhost:8080/api/v1/user/get-users
  - DeleteMapping -> http://localhost:8080/api/v1/user/delete-user/6 

  ### Status
  - PostMapping   -> http://localhost:8080/api/v1/status/create-status

  ### ChatHistory
  - PostMapping   -> http://localhost:8080/api/v1/chat/send-message
  - GetMapping    -> http://localhost:8080/api/v1/chat/get-chat?userId=1 (Get Chats By UserId)
  - GetMapping    -> http://localhost:8080/api/v1/chat/get-conversation?user1=1&user2=3 (Get Conversation by Users UserId)
