package com.company.bookshop.server.db;

import com.company.bookshop.server.enums.Language;
import com.company.bookshop.server.enums.Role;
import com.company.bookshop.server.model.Book;
import com.company.bookshop.server.model.Sale;
import com.company.bookshop.server.model.User;

import java.util.ArrayList;
import java.util.List;

public interface Database {
    List<User> USERS = new ArrayList<>();
    List<Book> BOOKS = new ArrayList<>();

    List<Sale> SALES = new ArrayList<>();


    static void loadData(){
        User user = new User("Ibrohim Xamidov",
                "+998901234567", "4567");
        user.getRoles().add(Role.ADMIN);
        user.getRoles().add(Role.SUPER_ADMIN);

        USERS.add(user);

        User user1 = new User("Bilol Aliqo'ziyev",
                "+998901112233", "2233");
        user1.getRoles().add(Role.ADMIN);
        USERS.add(user1);

        User user2 = new User("Nodir Norov", "+998904445566", "5566");
        USERS.add(user2);

        User user3 = new User("Nodir Fayzullayev", "+998907778899", "8899");
        USERS.add(user3);



    }

}
