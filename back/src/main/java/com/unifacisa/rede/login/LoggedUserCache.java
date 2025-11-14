package com.unifacisa.rede.login;

import com.unifacisa.rede.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class LoggedUserCache {

    private final AtomicReference<UserEntity> currentUser = new AtomicReference<>();

    public void setLoggedUser(UserEntity user) {
        currentUser.set(user);
    }

    public UserEntity getLoggedUser() {
        return currentUser.get();
    }

    public void clear() {
        currentUser.set(null);
    }
}
