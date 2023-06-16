package com.company.bookshop.server.records;

public record PasswordDTO(String userId, String newPassword, String confirmPassword) {
}
