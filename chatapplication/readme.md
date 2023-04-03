# Online Chat Tool

## Project overview
- Developed a Java-Spring Boot project using Maven, creating REST APIs, and testing them with POSTMAN. Utilising REST APIs, it was possible to enable conversation between two users and connected to MySQL Database to save the chats

## Tech Stack
- Java
- Spring Boot
- MySQL

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
  
  ## Contributing
- If you would like to contribute to this project, please open a pull request.
