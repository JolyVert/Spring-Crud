package com.variable.responses;

import com.variable.entities.Role;
import com.variable.entities.enums.RoleEnum;

public class UserInfoResponse {
    private long id;

    private String username;

    private String email;

    private RoleEnum role;

    private Object friends;

    private Object blocklist;

    private Object favourites;


    public UserInfoResponse(long id, String username, String email, Role role, Object friends, Object blocklist, Object favourites) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role.getName();
        this.friends = friends;
        this.blocklist = blocklist;
        this.favourites = favourites;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role.getName();
    }

    public Object getBlocklist() {
        return null;
    }

    public void setBlocklist(Object blocklist) {
        this.blocklist = blocklist;
    }

    public Object getFriends() {
        return null;
    }

    public void setFriends(Object friends) {
        this.friends = friends;
    }

    public Object getFavourites() {
        return null;
    }

    public void setFavourites(Object favourites) {
        this.favourites = favourites;
    }
}
