package com.company.bookshop;

import com.company.bookshop.client.ui.MainUI;
import com.company.bookshop.server.db.Database;

public class BookShopApplication {
    public static void main(String[] args) {
        Database.loadData();
        MainUI.window();
        System.out.println(Database.BOOKS);
    }
}

