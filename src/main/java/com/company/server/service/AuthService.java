package com.company.server.service;

import com.company.server.database.Database;
import com.company.server.dto.Result;
import com.company.server.dto.UserDTO;
import com.company.server.entity.User;
import com.company.server.exceptions.ValidationException;
import com.company.server.files.WorkWithFiles;

import java.util.Objects;

public class AuthService {
    public static Result register(UserDTO userDTO) {
        try {
            checkParams(userDTO);

            User user = UserService.getUserByPhoneNumber(userDTO.getPhoneNumber());

            if(user != null){
                return new Result("This phone number already taken", false);
            }

            user = new User(Database.USERS.size()+1, userDTO.getFullName(),
                    userDTO.getPhoneNumber(), userDTO.getPassword());

            Database.USERS.add(user);
            WorkWithFiles.writeToJsonFile(Database.USERS, Database.USERS_FILE);

        } catch (ValidationException e) {
            return new Result(e.getMessage(), false);
        }
        return new Result("Successfully registered", true);
    }

    private static void checkParams(UserDTO userDTO) throws ValidationException {
        if (userDTO == null)
            throw new ValidationException("Data is required");

        String fullName = userDTO.getFullName();
        if (Objects.isNull(fullName) || fullName.isBlank()) {
            throw new ValidationException("Full name is required");
        }

        String phoneNumber = userDTO.getPhoneNumber();
        if (Objects.isNull(phoneNumber) || phoneNumber.isBlank()) {
            throw new ValidationException("Phone number is required");
        }

        if (!phoneNumber.matches("\\+998\\d{9}")) {
            throw new ValidationException("Invalid phone number");
        }

        String password = userDTO.getPassword();
        if (Objects.isNull(password) || password.isBlank()) {
            throw new ValidationException("Password is required");
        }

        if (!Objects.equals(password, userDTO.getConfirmPassword())) {
            throw new ValidationException("Passwords don't match.");
        }
    }

    public static Result login(UserDTO userDTO) {
        try {
            // phone number, password
            if(userDTO != null){
                userDTO.setFullName("full name");
                userDTO.setConfirmPassword(userDTO.getPassword());
            }

            checkParams(userDTO);

            User user = UserService.getUserByPhoneNumber(userDTO.getPhoneNumber());

            if(user == null || !user.getPassword().equals(userDTO.getPassword())){
                return new Result("Wrong phone number or password", false);
            }

        } catch (ValidationException e) {
            return new Result(e.getMessage(), false);
        }
        return new Result("Successfully signed", true);
    }
}
