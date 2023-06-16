package com.company.bookshop.server.records;

public record UserDTO(String fullName, String phoneNumber,
                      String password, String confirmPassword) {

}
