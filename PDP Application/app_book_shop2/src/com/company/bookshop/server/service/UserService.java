package com.company.bookshop.server.service;

import com.company.bookshop.server.db.Database;
import com.company.bookshop.server.enums.Role;
import com.company.bookshop.server.model.User;
import com.company.bookshop.server.records.Response;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    public User getUserByPhoneNumber(String phoneNumber) {
        if (phoneNumber==null || !phoneNumber.matches("\\+998\\d{9}")){
            return null;
        }
        for (User user : Database.USERS) {
            if (user.getPhoneNumber().equals(phoneNumber)){
                return user;
            }
        }
        return null;
    }

    public User getUserById(String userId){
        if (userId==null || userId.isBlank()) {
            return null;
        }
        for (User user : Database.USERS) {
            if (user.getId().equals(userId)){
                return user;
            }
        }
        return null;
    }


    public List<User> getUsers() {
        return Database.USERS;
    }

    public List<User> getUsers(List<Role> roles) {
        List<User> users = new ArrayList<>();
        for (User user : Database.USERS) {
            if(roles.containsAll(user.getRoles()) &&
                    user.getRoles().containsAll(roles)){
                users.add(user);
            }
        }
        return users;
    }

    public Response grantNewRoleToUser(String userId, Role newRole) {
        User user = getUserById(userId);
        if(user == null){
            return new Response("User not found", false);
        }

        if(user.getRoles().contains(newRole)){
            return new Response("This user has that role", false);
        }

        user.getRoles().add(newRole);

        return new Response(
                "%s role successfully given to %s".formatted(newRole, user.getFullName()),
                true);
    }

    public Response revokeAdminRoleFromAdmin(String userId, Role deletedRole, List<Role> roles) {
        User user = getUserById(userId);
        if(user == null){
            return new Response("User not found", false);
        }
        if(!(roles.containsAll(user.getRoles()) &&
                user.getRoles().containsAll(roles))){
            return new Response("Wrong user id", false);
        }

        if(!user.getRoles().contains(deletedRole)){
            return new Response("This user hasn't "+deletedRole+" role", false);
        }

        user.getRoles().remove(deletedRole);

        return new Response(
                "%s role successfully revoked from %s".formatted(deletedRole, user.getFullName()),
                true);
    }
}
