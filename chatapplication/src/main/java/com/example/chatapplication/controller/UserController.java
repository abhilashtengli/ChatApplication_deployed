package com.example.chatapplication.controller;

import java.sql.Timestamp;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.chatapplication.dao.StatusRepository;
import com.example.chatapplication.dao.UserRepository;
import com.example.chatapplication.model.Status;
import com.example.chatapplication.model.Users;
import com.example.chatapplication.service.UserService;
import com.example.chatapplication.util.CommonUtils;

import io.micrometer.common.lang.Nullable;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserService service;

    @Autowired
    UserRepository repo;

    @Autowired
    StatusRepository statusRepo;

    // -------------------------------------------------------------------------------------
    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@RequestBody String userData) {
        JSONObject isValid = validationUserRequest(userData);

        Users user = null;

        if (isValid.isEmpty()) {
            user = setUser(userData);
            service.saveUser(user);
        } else {
            return new ResponseEntity<String>(isValid.toString(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("saved", HttpStatus.CREATED);
    }

    // --------------------------------------------------------------------------------------
    @GetMapping(value = "/get-users")
    public ResponseEntity<String> getUsers(@Nullable @RequestParam String userId) {
        JSONArray userArr = service.getUsers(userId);

        return new ResponseEntity<String>(userArr.toString(), HttpStatus.OK);

    }

    // -------------------------------------------------------------------------------------
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String requestData) {
        JSONObject requestJson = new JSONObject(requestData);

        JSONObject isValidLogin = validateLogin(requestJson);

        if (isValidLogin.isEmpty()) {
            String username = requestJson.getString("username");
            String password = requestJson.getString("password");

            JSONObject responseObj = service.login(username, password);
            if (responseObj.has("errorMessage")) {
                return new ResponseEntity<String>(responseObj.toString(), HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(responseObj.toString(), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(isValidLogin.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    // -------------------------------------------------------------------------------------
    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<String> deleteByUserId(@PathVariable Integer userId){
        service.deleteUserByUserId(userId);
        return new ResponseEntity<>("Deleted",HttpStatus.NO_CONTENT);
    }
    // -------------------------------------------------------------------------------------

    private JSONObject validateLogin(JSONObject requestJson) {
        JSONObject errorList = new JSONObject();

        if (!requestJson.has("username")) {
            errorList.put("username", "Missing parameter");
        }
        if (!requestJson.has("password")) {
            errorList.put("password", "Missing parameter");
        }

        return errorList;
    }

    private Users setUser(String userData) {
        Users user = new Users();
        JSONObject json = new JSONObject(userData);

        user.setEmail(json.getString("email"));
        user.setPassword(json.getString("password"));
        user.setFirstname(json.getString("firstname"));
        user.setUsername(json.getString("username"));
        user.setPhoneNumber(json.getString("phoneNumber"));

        if (json.has("age")) {
            user.setAge(json.getInt("age"));
        }

        if (json.has("lastname")) {
            user.setLastname(json.getString("lastname"));
        }

        if (json.has("gender")) {
            user.setGender(json.getString("gender"));
        }
        Timestamp createdtime = new Timestamp(System.currentTimeMillis());
        user.setCreatedDate(createdtime);

        Status status = statusRepo.findById(1).get();
        user.setStatusId(status);

        return user;
    }

    // -------------------------------------------------------------------------------------
    private JSONObject validationUserRequest(String userData) {
        JSONObject userJson = new JSONObject(userData);
        JSONObject errorList = new JSONObject();

        if (userJson.has("username")) {
            String username = userJson.getString("username");
            List<Users> userList = repo.findByUserusername(username);
            if (userList.size() > 0) {
                errorList.put("username", "This username already exist");
                return errorList;
            }

        } else {
            errorList.put("username", "Missing parameter");
        }
        if (userJson.has("password")) {
            String password = userJson.getString("password");
            if (!CommonUtils.isValidPassword(password)) {
                errorList.put("password", "please enter valid password");
            }

        } else {
            errorList.put("password", "Missing parameter");
        }
        if (userJson.has("firstname")) {
            String firstname = userJson.getString("firstname");

        } else {
            errorList.put("firstname", "Missing parameter");
        }
        if (userJson.has("email")) {
            String email = userJson.getString("email");
            if (!CommonUtils.isValidEmail(email)) {
                errorList.put("email", "please enter a valid email");
            }

        } else {
            errorList.put("email", "Missing parameter");
        }
        if (userJson.has("phoneNumber")) {
            String phoneNumber = userJson.getString("phoneNumber");
            if (!CommonUtils.isValidPhoneNumber(phoneNumber)) {
                errorList.put("phoneNumber", "please enter a valid phoneNumber");
            }

        } else {
            errorList.put("phoneNumber", "Missing parameter");
        }

        return errorList;

    }
    // -------------------------------------------------------------------------------------
}
