package com.company.bookshop.client.ui;

import com.company.bookshop.client.util.ScannerUtil;
import com.company.bookshop.server.enums.Language;
import com.company.bookshop.server.enums.Role;
import com.company.bookshop.server.model.Book;
import com.company.bookshop.server.model.Sale;
import com.company.bookshop.server.model.User;
import com.company.bookshop.server.records.*;
import com.company.bookshop.server.service.AuthService;
import com.company.bookshop.server.service.BookService;
import com.company.bookshop.server.service.UserService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class AuthUI {

    public static void login() {
        System.out.println("LOGIN FORM");
        String phoneNumber = ScannerUtil.getString("Phone number(+998901234455): ");
        String password = ScannerUtil.getString("Password: ");

        UserDTO userDTO = new UserDTO("Full name", phoneNumber, password, password);

        AuthService authService = new AuthService();

        Response response = authService.login(userDTO);

        System.out.println(response);

        if (response.success()) {
            UserUI.cabinet(response.message());
        }

    }

    public static void register() {
        System.out.println("REGISTRATION FORM");
        String fullName = ScannerUtil.getString("Full name: ");
        String phoneNumber = ScannerUtil.getString("Phone number(+998901234455): ");
        String password = ScannerUtil.getString("Password: ");
        String confirmPassword = ScannerUtil.getString("Password again: ");

        UserDTO userDTO = new UserDTO(fullName, phoneNumber, password, confirmPassword);

        AuthService authService = new AuthService();

        Response response = authService.register(userDTO);

        System.out.println(response);
    }

    public static void grantAdminRoleToUser(User sessionUser) {
        UserService userService = new UserService();
        List<User> userList = userService.getUsers(List.of(Role.USER));

        if(userList == null || userList.isEmpty()){
            System.out.println("No simple users");
            return;
        }

        userList.forEach(System.out::println);
        String userId = ScannerUtil.getString("User id:");

        Response response = userService.grantNewRoleToUser(userId, Role.ADMIN);
        System.out.println(response);
    }

    public static void revokeAdminRoleFromAdmin(User sessionUser) {
        UserService userService = new UserService();
        List<Role> roles = List.of(Role.ADMIN, Role.USER);
        List<User> userList = userService.getUsers(roles);

        if(userList == null || userList.isEmpty()){
            System.out.println("No simple admins");
            return;
        }

        userList.forEach(System.out::println);
        String userId = ScannerUtil.getString("User id:");

        Response response = userService.revokeAdminRoleFromAdmin(userId, Role.ADMIN, roles);
        System.out.println(response);
    }

    public static void changePassword(User sessionUser) {
        String currentPassword = ScannerUtil.getString("Enter current password");

        if (currentPassword.equals(sessionUser.getPassword())){
            String newPassword = ScannerUtil.getString("Enter new password");
            String confirmPassword = ScannerUtil.getString("Enter password again");
            if (newPassword.equals(confirmPassword)){
                PasswordDTO passwordDTO = new PasswordDTO(sessionUser.getId(), newPassword, confirmPassword);
                AuthService authService = new AuthService();

                Response response  = authService.changePassword(passwordDTO);
                System.out.println(response);

            }else {
                System.out.println("Passwords don't match");
            }
        }else {
            System.out.println("Wrong current password");
        }

    }


    public static void addBook(User sessionUser) {
        System.out.println("------------ADD BOOK----------------");
        String title =ScannerUtil.getString("Enter title : ");
        String author =ScannerUtil.getString("Enter author : ");
        Language language = null;
        do {
            System.out.println(Arrays.toString(Language.values()));
            String  language1 = ScannerUtil.getString("Choose language: ");
            for (Language value : Language.values()) {
                if (language1.equalsIgnoreCase(value.name())){
                    language = value;
                    break;
                }
            }}
            while (language == null) ;

            double price =ScannerUtil.getDouble("Enter price : ");
        Integer year;
            while (true){
             year = ScannerUtil.getInteger("Enter created year : ");
        if (!(year < 0 || year > LocalDate.now().getYear())) {
            break;
        }}
        BookDTO bookDTO = new BookDTO(title, author, language, price, year);
        AuthService authService = new AuthService();
        Response response = authService.addBook(bookDTO);

        System.out.println(response);
    }

    public static void editBook(User sessionUser) {
        BookService bookService = new BookService();
        List<Book> bookList = bookService.getBooks();

        if(bookList == null || bookList.isEmpty()){
            System.out.println("No simple users");
            return;
        }

        bookList.forEach(System.out::println);
        String bookId = ScannerUtil.getString("Book id : ");

        Response response = bookService.editBook(bookId);
        System.out.println(response);
    }

    public static void deletBook(User sessionUser) {
        BookService bookService = new BookService();
        List<Book> bookList = bookService.getBooks();

        if(bookList == null || bookList.isEmpty()){
            System.out.println("No simple users");
            return;
        }

        bookList.forEach(System.out::println);
        String bookId = ScannerUtil.getString("Book id : ");

        Response response = bookService.deletBook(bookId);
        System.out.println(response);
    }

    public static void showAllBooks() {
        BookService bookService = new BookService();
        List<Book> bookList = bookService.getBooks();

        if (bookList==null || bookList.isEmpty()){
            System.out.println("No users");
        }else {
            bookList.forEach( System.out::println);
        }
    }



    public static void showAllSales(User sessionUser) {
        BookService bookService = new BookService();
        List<Sale> saleList = bookService.getSale();

        if (saleList==null || saleList.isEmpty()){
            System.out.println("No users");
        }else {
            saleList.forEach( System.out::println);
        }

    }

    public static void showUserAllBooks(User sessionUser) {
        BookService bookService = new BookService();
        List<Book> bookList = bookService.getBooks();

        if (bookList==null || bookList.isEmpty()){
            System.out.println("No users");
        }else {
            for (Book book : bookList) {
                if (!book.isDeleted()) System.out.println(book);
            }
        }
        String bookId = ScannerUtil.getString("Choose book id for buying : ");
        int bookCon = ScannerUtil.getInteger("Enter book count : ");

        Response response = bookService.orderBook(bookId, bookCon, sessionUser);
        System.out.println(response);

    }

    public static void searchBook(User sessionUser) {
        BookService bookService = new BookService();
        String searchBook = ScannerUtil.getString("Search : ");

        System.out.println(bookService.searchBook(searchBook));

        String bookId = ScannerUtil.getString("Choose book id for buying : ");
        int bookCon = ScannerUtil.getInteger("Enter book count : ");

        Response response = bookService.orderBook(bookId, bookCon, sessionUser);
        System.out.println(response);

    }

    public static void fillBalance(User sessionUser) {
        double fillBalance = ScannerUtil.getDouble("Enter balance : ");
        BookService bookService = new BookService();

        System.out.println(bookService.fillBalance(sessionUser, fillBalance));


    }

    public static void showBalance(User sessionUser) {
        BookService bookService = new BookService();
        System.out.println(bookService.showBalance(sessionUser));
    }

    public static void showOwnSale(User sessionUser) {
        BookService bookService = new BookService();
        bookService.showSale(sessionUser);
    }
}


