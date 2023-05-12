package com.company.client.ui;

import com.company.client.util.ScannerUtil;
import com.company.server.entity.User;
import com.company.server.enums.Role;

public class UserUI {
    public static void cabinet(User user) {
        System.out.printf("\nWelcome, %s!%n", user.getFullName());

        if(user.getRole().equals(Role.ADMIN)){
            adminPage(user);
        }else{
            userPage(user);
        }
    }

    private static void userPage(User user) {
        while (true){
            System.out.println();
            String operation = getUserMenu();
            System.out.println();
            if(operation.equals("0")) break;

            switch (operation){
                case "1" : TestUI.solveTest(user); break; // done
                case "2" : TestUI.showTestHistory(user); break;
                case "3" : AuthUI.changePassword(user); break;
            }
        }
    }

    private static void adminPage(User admin) {
        while (true){
            System.out.println();
            String operation = getAdminMenu();
            System.out.println();
            if(operation.equals("0")) break;

            switch (operation){
                case "1" : SubjectUI.addSubject(); break; // done
                case "2" : SubjectUI.showSubjects(); break; // done
                case "3" : QuestionUI.addQuestion(); break; // done
                case "4" : QuestionUI.showQuestions(); break; // done
                case "5" : UserUI.showUsers(); break;
                case "6" : AuthUI.changePassword(admin); break;
            }
        }
    }

    private static void showUsers() {

    }

    private static String getAdminMenu() {
        System.out.println("1. Add subject");
        System.out.println("2. Export subject list to Word");
        System.out.println("3. Add question");
        System.out.println("4. Export question list to Excel");
        System.out.println("5. Export user list to PDF");
        System.out.println("6. Change password");
        System.out.println("0. Logout");
        System.out.println("Enter operation number");
        return ScannerUtil.SCANNER_STR.nextLine();
    }

    private static String getUserMenu() {
        System.out.println("1. Solve test");
        System.out.println("2. Export solve test history to Excel");
        System.out.println("3. Change password");
        System.out.println("0. Logout");
        System.out.println("Enter operation number");
        return ScannerUtil.SCANNER_STR.nextLine();
    }
}
