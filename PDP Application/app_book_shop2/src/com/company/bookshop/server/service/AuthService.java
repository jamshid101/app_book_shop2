package com.company.bookshop.server.service;

import com.company.bookshop.server.db.Database;
import com.company.bookshop.server.model.Book;
import com.company.bookshop.server.model.User;
import com.company.bookshop.server.records.BookDTO;
import com.company.bookshop.server.records.PasswordDTO;
import com.company.bookshop.server.records.Response;
import com.company.bookshop.server.records.UserDTO;

import java.time.LocalDate;

public class AuthService {

    public Response register(UserDTO userDTO) {

        Response response = checkParameters(userDTO);
        if (!response.success()) return response;

        UserService userService = new UserService();
        User user = userService.getUserByPhoneNumber(userDTO.phoneNumber());
        if (user != null) {
            return new Response("User exists with this number", false);
        }

        User newUser = new User(userDTO.fullName(), userDTO.phoneNumber(), userDTO.password());
        Database.USERS.add(newUser);

        return new Response(newUser.getFullName() + " successfully registered", true);
    }

    private Response checkParameters(UserDTO userDTO) {
        if (userDTO == null) {
            return new Response("Data not found", false);
        }

        if (userDTO.fullName() == null || userDTO.fullName().isBlank()) {
            return new Response("Full name is required", false);
        }

        if (userDTO.phoneNumber() == null || userDTO.phoneNumber().isBlank()) {
            return new Response("Phone number is required", false);
        }
        if (!userDTO.phoneNumber().matches("\\+998[0-9]{9}")) {
            return new Response("Invalid phone number", false);
        }
        if (userDTO.password() == null) {
            return new Response("Password is required", false);
        }

        if (!userDTO.password().equals(userDTO.confirmPassword())) {
            return new Response("Passwords don't match", false);
        }
        return new Response("", true);
    }


    public Response login(UserDTO userDTO) {
        Response response = checkParameters(userDTO);
        if (!response.success()) return response;

        UserService userService = new UserService();
        User user = userService.getUserByPhoneNumber(userDTO.phoneNumber());

        if (user == null || !user.getPassword().equals(userDTO.password()) || user.isDeleted()) {
            return new Response("Phone number or password incorrect", false);
        }


        return new Response(user.getId(), true);

    }

    public Response changePassword(PasswordDTO passwordDTO) {
        Response response = checkParameters(passwordDTO);
        if (!response.success()) return response;

        UserService userService = new UserService();
        User user = userService.getUserById(passwordDTO.userId());

        if (user == null) {
            return new Response("User not found", false);
        }

        user.setPassword(passwordDTO.newPassword());

        return new Response("Password successfully changed", true);
    }

    private Response checkParameters(PasswordDTO passwordDTO) {
        if (passwordDTO == null) {
            return new Response("Data not found", false);
        }

        if (passwordDTO.userId() == null || passwordDTO.userId().isBlank()) {
            return new Response("Wrong user id ", false);
        }

        String newPassword = passwordDTO.newPassword();

        if (newPassword == null || newPassword.isBlank()) {
            return new Response("Password is required", false);
        }

        String confirmPassword = passwordDTO.confirmPassword();
        if (!newPassword.equals(confirmPassword)) {
            return new Response("Passwords don't match", false);
        }

        return new Response("", true);
    }

    public Response addBook(BookDTO bookDTO) {
        Response response = checkBookParameters(bookDTO);
        if (!response.success()) return response;

        BookService bookService = new BookService();
        Book book = bookService.checkingHasParametr(bookDTO , "");
        if (book != null) {
            return new Response("Book exists ", false);
        }
        Book newBook = new Book(bookDTO.title(), bookDTO.author(), bookDTO.language(), bookDTO.price(), bookDTO.year());
        Database.BOOKS.add(newBook);


        return new Response(newBook.getTitle() + " successfully added", true);
    }

    private Response checkBookParameters(BookDTO bookDTO) {
        if (bookDTO == null) {
            return new Response("Data not found ", false);
        }
        if (bookDTO.title() == null || bookDTO.title().isBlank()) {
            return new Response("Wrong title ", false);
        }
        if (bookDTO.author() == null || bookDTO.author().isBlank()) {
            return new Response("Wrong author ", false);
        }
        if (bookDTO.year() < 0 || bookDTO.year() > LocalDate.now().getYear()) {
            return new Response("Wrong created year ", false);
        }
        return new Response("", true);
    }

    public Response editBook(BookDTO bookDTO, Book book, String bookId) {
        Response response = checkBookParameters(bookDTO);
        if (!response.success()) return response;

        BookService bookService = new BookService();
        Book book1 = bookService.checkingHasParametr(bookDTO, bookId );
        if (book1 != null) {
            return new Response("Book exists ", false);
        }
        Book newBook = new Book(bookDTO.title(), bookDTO.author(), bookDTO.language(), bookDTO.price(), bookDTO.year());
        int con = 0;
        for (Book book2 : Database.BOOKS) {
            if (book.equals(book2)){
                book2.setLanguage(bookDTO.language());
                book2.setTitle(bookDTO.title());
                book2.setAuthor(bookDTO.author());
                book2.setPrice(bookDTO.price());
                book2.setYear(bookDTO.year());
            }
            con++;
        }


        return new Response(newBook.getTitle() + " successfully edit", true);
    }
}
