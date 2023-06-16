package com.company.bookshop.client.ui;

import com.company.bookshop.client.util.ScannerUtil;
import com.company.bookshop.server.enums.Role;
import com.company.bookshop.server.model.User;
import com.company.bookshop.server.service.UserService;

import java.util.List;

public class UserUI {
    public static void cabinet(String userId) {
        System.out.println();
        UserService userService = new UserService();
        User sessionUser = userService.getUserById(userId);

        if (sessionUser == null) return;

        System.out.println(sessionUser.getFullName() + ", welcome to your cabinet!");

        // Role role = Role.valueOf(roleAsString);

        if (sessionUser.getRoles().size() == 1) {
            userPage(sessionUser);
            return;
        }

        Role role = null;

        do {
            System.out.println(sessionUser.getRoles());
            String roleAsString = ScannerUtil.getString("Choose role: ");
            for (Role value : sessionUser.getRoles()) {
                if (value.name().equalsIgnoreCase(roleAsString)) {
                    role = value;
                    break;
                }
            }
        } while (role == null);


        switch (role) {
            case USER -> userPage(sessionUser);
            case ADMIN -> adminPage(sessionUser);
            case SUPER_ADMIN -> superAdminPage(sessionUser);
        }

    }

    private static void superAdminPage(User sessionUser) {
        System.out.println("---------------Super Admin page--------------");
        while (true) {
            System.out.println();
            System.out.println("1. Grant ADMIN ROLE to USER");
            System.out.println("2. Revoke ADMIN ROLE from ADMIN");
            System.out.println("3. Show all users");
            System.out.println("4. Change password");
            System.out.println("5. Logout");
            String operation = ScannerUtil.getString("?: ");
            switch (operation) {
                case "1" -> AuthUI.grantAdminRoleToUser(sessionUser);
                case "2" -> AuthUI.revokeAdminRoleFromAdmin(sessionUser);
                case "3" -> UserUI.showAllUsers(sessionUser);
                case "4" -> AuthUI.changePassword(sessionUser);
                case "5" -> {return;}

            }
        }
    }

    private static void showAllUsers(User sessionUser) {
        UserService userService = new UserService();
        List<User> userList = userService.getUsers();

        if (userList==null || userList.isEmpty()){
            System.out.println("No users");
        }else {
            userList.forEach(System.out::println);
        }
    }

    private static void adminPage(User sessionUser) {
        System.out.println("------------------Admin page-----------------");
        System.out.println();
while (true){
        System.out.println("""
               1. Add book
               2. Edit book
               3. Delete book
               4. Show all books
               5. Show all users
               6. Show all sales
               7. Change password
               8. Logout
                """);
        String operation = ScannerUtil.getString("?: ");
        switch (operation) {
            case "1" -> AuthUI.addBook(sessionUser);
            case "2" -> AuthUI.editBook(sessionUser);
            case "3" -> AuthUI.deletBook(sessionUser);
            case "4" -> AuthUI.showAllBooks();
            case "5" -> UserUI.showAllUsers(sessionUser);
            case "6" -> AuthUI.showAllSales(sessionUser);
            case "7" -> AuthUI.changePassword(sessionUser);
            case "8" -> {return;}

        }

    }
    }



    private static void userPage(User sessionUser) {
        System.out.println("======================USER PAGE=================");
        while (true){
        System.out.println("""
                1. Show active books
                2. Search books
                3. Fill balance
                4. Show balance
                5. Show own sales
                6. Change password
                7. Logout
                """);
        String operation = ScannerUtil.getString("?: ");
        switch (operation) {
            case "1" -> AuthUI.showUserAllBooks(sessionUser);
            case "2" -> AuthUI.searchBook(sessionUser);
            case "3" -> AuthUI.fillBalance(sessionUser);
            case "4" -> AuthUI.showBalance(sessionUser);
            case "5" -> AuthUI.showOwnSale(sessionUser);
            case "6" -> AuthUI.showAllSales(sessionUser);
            case "7" -> {return;}


        }
    }
}


}
