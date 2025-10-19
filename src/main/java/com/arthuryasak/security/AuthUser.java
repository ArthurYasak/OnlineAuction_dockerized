package com.arthuryasak.security;

import com.arthuryasak.models.User;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
public class AuthUser extends org.springframework.security.core.userdetails.User {

    @NonNull
    private final User user;

    public AuthUser(User user) {
        super(user.getAuthorizationData().getLogin(),
                user.getAuthorizationData().getPassword(),
                user.getUserRoles());
        this.user = user;
    }

    public int id() {
        return this.user.getUserId();
    }

    public User getUser() {
        return this.user;
    }
}
