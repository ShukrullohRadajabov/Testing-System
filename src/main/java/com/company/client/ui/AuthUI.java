package com.company.client.ui;

import com.company.client.util.ScannerUtil;
import com.company.server.dto.Result;
import com.company.server.dto.UserDTO;
import com.company.server.entity.User;
import com.company.server.service.AuthService;
import com.company.server.service.UserService;

public class AuthUI {
    public static void login() {
        System.out.println("Enter phone number (+998XXYYYYYYY)");
        String phoneNumber = ScannerUtil.SCANNER_STR.nextLine();

        System.out.println("Enter password");
        String password = ScannerUtil.SCANNER_STR.nextLine();

        UserDTO userDTO = new UserDTO(phoneNumber, password);

        Result result = AuthService.login(userDTO);
        if(result.isSuccess()){
            System.out.println(ScannerUtil.ANSI_GREEN+result.getMessage()+ScannerUtil.ANSI_RESET);

            User user = UserService.getUserByPhoneNumber(phoneNumber);
            UserUI.cabinet(user);

        }else{
            System.out.println(ScannerUtil.ANSI_RED+result.getMessage()+ScannerUtil.ANSI_RESET);
        }
    }

    public static void register() {
        System.out.println("Enter full name");
        String fullName = ScannerUtil.SCANNER_STR.nextLine();

        System.out.println("Enter phone number (+998XXYYYYYYY)");
        String phoneNumber = ScannerUtil.SCANNER_STR.nextLine();

        System.out.println("Enter password");
        String password = ScannerUtil.SCANNER_STR.nextLine();

        System.out.println("Enter password again");
        String confirmPassword = ScannerUtil.SCANNER_STR.nextLine();

        UserDTO userDTO = new UserDTO(fullName, phoneNumber, password, confirmPassword);

        Result result = AuthService.register(userDTO);
        if(result.isSuccess()){
            System.out.println(ScannerUtil.ANSI_GREEN+result.getMessage()+ScannerUtil.ANSI_RESET);
        }else{
            System.out.println(ScannerUtil.ANSI_RED+result.getMessage()+ScannerUtil.ANSI_RESET);
        }
    }

    public static void changePassword(User user) {
        // todo
    }
}
