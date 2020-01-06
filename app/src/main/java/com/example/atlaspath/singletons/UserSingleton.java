package com.example.atlaspath.singletons;

import com.example.atlaspath.models.User;

public class UserSingleton {
    private static final User user = new User();

    public static User getInstance() {
        return user;
    }

    private UserSingleton() { }
}
