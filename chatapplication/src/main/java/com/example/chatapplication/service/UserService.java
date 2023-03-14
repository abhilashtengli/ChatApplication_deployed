package com.example.chatapplication.service;

import java.util.List;
// import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.chatapplication.dao.UserRepository;
import com.example.chatapplication.model.Users;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    public int saveUser(Users user) {
        Users userObj = userRepo.save(user);
        return userObj.getUserId();
    }

    public JSONArray getUsers(String userId) {
        JSONArray response = new JSONArray();
        if (null != userId) {
            List<Users> userList = userRepo.getUserByUserId(Integer.valueOf(userId));
            for (Users user : userList) {
                JSONObject userObj = createResponse(user);
                response.put(userObj);
            }
        } else {
            List<Users> userList = userRepo.getAllUsers();
            for (Users user : userList) {
                JSONObject userObj = createResponse(user);
                response.put(userObj);
            }
        }

        return response;
    }

    public JSONObject createResponse(Users user) {
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("UserId", user.getUserId());
        jsonObj.put("username", user.getUsername());
        jsonObj.put("firstName", user.getFirstname());
        jsonObj.put("lastName", user.getLastname());
        jsonObj.put("age", user.getAge());
        jsonObj.put("email", user.getEmail());
        jsonObj.put("phoneNumber", user.getPhoneNumber());
        jsonObj.put("gender", user.getGender());
        jsonObj.put("createdDate", user.getCreatedDate());

        return jsonObj;
    }

    public JSONObject login(String username, String password) {
        JSONObject response = new JSONObject();
        List<Users> user = userRepo.findByUserusername(username);
        if (user.isEmpty()) {
            response.put("errorMessage", "username doesn't exists");
        } else {
            Users userObj = user.get(0);
            if (password.equals(userObj.getPassword())) {
                response = createResponse(userObj);
            } else {
                response.put("errorMessage", "Invalid password");
            }
        }
        return response;
    }

    public void deleteUserByUserId(Integer userId) {
        userRepo.deleteByUserId(userId);
    }

}
