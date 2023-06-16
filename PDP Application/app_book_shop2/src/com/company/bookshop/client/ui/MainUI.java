package com.company.bookshop.client.ui;

import com.company.bookshop.client.util.ScannerUtil;
import com.company.bookshop.server.db.Database;

public class MainUI {
    public static void window() {
        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Exit");
            String operation = ScannerUtil.getString("?: ");

            if (operation.equals("0")) break;

            switch (operation){
                case "1"-> AuthUI.login();
                case "2"-> AuthUI.register();
            }

            System.out.println();

        }

    }
}
