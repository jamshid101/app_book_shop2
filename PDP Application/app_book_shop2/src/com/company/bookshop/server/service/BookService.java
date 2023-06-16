package com.company.bookshop.server.service;

import com.company.bookshop.client.util.ScannerUtil;
import com.company.bookshop.server.db.Database;
import com.company.bookshop.server.enums.Language;
import com.company.bookshop.server.model.Book;
import com.company.bookshop.server.model.Sale;
import com.company.bookshop.server.model.User;
import com.company.bookshop.server.records.BookDTO;
import com.company.bookshop.server.records.Response;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class BookService {

    public Book checkingHasParametr(BookDTO bookDTO, String bookId) {
        for (Book book : Database.BOOKS) {
            if (!bookId.equals(book.getId()) && bookDTO.title().toLowerCase().equals(book.getTitle().toLowerCase()) && bookDTO.author().toLowerCase()
                    .equals(book.getAuthor().toLowerCase()) && bookDTO.year().equals(book.getYear()) && bookDTO.language().equals(book.getLanguage())) {
                return book;
            }
        }
        return null;
    }

    public List<Book> getBooks() {
        return Database.BOOKS;
    }

    public Response editBook(String bookId) {
        Book book = getBookById(bookId);
        if (book == null) {
            return new Response("Book not found", false);
        }
        System.out.println(book);

        String title = ScannerUtil.getString("Enter title : ");
        String author = ScannerUtil.getString("Enter author : ");
        Language language = null;
        do {
            System.out.println(Arrays.toString(Language.values()));
            String language1 = ScannerUtil.getString("Choose language: ");
            for (Language value : Language.values()) {
                if (language1.equalsIgnoreCase(value.name())) {
                    language = value;
                    break;
                }
            }
        }
        while (language == null);

        double price = ScannerUtil.getDouble("Enter price : ");
        Integer year;
        while (true) {
            year = ScannerUtil.getInteger("Enter created year : ");
            if (!(year < 0 || year > LocalDate.now().getYear())) {
                break;
            }
        }
        BookDTO bookDTO = new BookDTO(title, author, language, price, year);
        AuthService authService = new AuthService();
        Response response = authService.editBook(bookDTO, book, bookId);

        return (response);
    }

    private Book getBookById(String bookId) {
        if (bookId == null || bookId.isBlank()) {
            return null;
        }
        for (Book book : Database.BOOKS) {
            if (book.getId().equals(bookId)) {
                return book;
            }
        }
        return null;
    }

    public Response deletBook(String bookId) {
        Book book = getBookById(bookId);
        if (book == null) {
            return new Response("Book not found", false);
        }
        for (Book book1 : Database.BOOKS) {
            if (book1.getId().equals(bookId)) {
                book1.setDeleted(true);
            }
        }
        return new Response("Deleted ", true);
    }

    public Response orderBook(String bookId, int bookCon, User sessionUser) {
        Book book = getBookById(bookId);
        if (book == null) {
            return new Response("Book not found", false);
        }

        Sale sale = new Sale(sessionUser, book, bookCon);
        Database.SALES.add(sale);
        return new Response("Successfuly", true);

    }

    public List<Sale> getSale() {
        return Database.SALES;
    }

    public Book searchBook(String searchBook) {
        for (Book book1 : Database.BOOKS) {
            //todo
        }
        return null;
    }

    public String fillBalance(User sessionUser, double fillBalance) {
         sessionUser.setBalance(fillBalance);
         return "Balance" + sessionUser.getBalance();
    }

    public String showBalance(User sessionUser) {
        return "Balance = " + sessionUser.getBalance();
    }

    public StringBuilder showSale(User sessionUser) {
        for (Sale sale : Database.SALES) {

        }
        return 0;
    }
}
