package com.company.server.entity;

import com.company.server.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private Integer id;
    private String fullName;
    private String phoneNumber;
    private String password;
    private Role role;

    public User(Integer id, String fullName,
                String phoneNumber, String password) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = Role.USER;
    }


}
