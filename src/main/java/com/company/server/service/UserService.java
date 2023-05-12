package com.company.server.service;

import com.company.server.database.Database;
import com.company.server.entity.User;

public class UserService {
    public static User getUserByPhoneNumber(String phoneNumber) {
        return Database.USERS.stream()
                .filter(user -> user.getPhoneNumber().equals(phoneNumber))
                .findFirst()
                .orElse(null);
    }
}
