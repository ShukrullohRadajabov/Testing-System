package com.company.client.ui;

import com.company.client.util.ScannerUtil;

public class MainUI {

    public static void run() {

        while (true){
            try {
                String operation = getBaseMenu();

                if (operation.equals("0")) return;

                // identification, authentication, authorization

                switch (operation) {
                    case "1":
                        AuthUI.login();
                        break;
                    case "2":
                        AuthUI.register();
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    private static String getBaseMenu() {
        System.out.println();
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("0. Exit");
        System.out.println("Enter operation number");
        return ScannerUtil.SCANNER_STR.nextLine();
    }
}
