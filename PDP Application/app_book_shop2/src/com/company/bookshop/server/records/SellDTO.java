package com.company.bookshop.server.records;

import com.company.bookshop.server.model.Book;
import com.company.bookshop.server.model.User;

import java.time.LocalDateTime;

public record SellDTO( User user, Book book, Integer quantity) {
}
