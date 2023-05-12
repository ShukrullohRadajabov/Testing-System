package com.company;

import com.company.client.ui.MainUI;
import com.company.server.database.Database;
import com.company.server.entity.User;

public class TestingSystem {

    public static void main(String[] args) {
        try {
            Database.loadData();

            MainUI.run();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
