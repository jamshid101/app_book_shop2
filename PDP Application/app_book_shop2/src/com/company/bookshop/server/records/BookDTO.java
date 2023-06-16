package com.company.bookshop.server.records;

import com.company.bookshop.server.enums.Language;

public record BookDTO( String title, String author, Language language, Double price, Integer year) {}

