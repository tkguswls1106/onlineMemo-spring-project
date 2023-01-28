package com.shj.onlinememospringproject.service.logic;

import com.shj.onlinememospringproject.service.UserService;
import com.shj.onlinememospringproject.store.normalstore.UserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceLogic implements UserService {

    private UserStore userStore;
    @Autowired
    public UserServiceLogic(UserStore userStore) {
        this.userStore = userStore;
    }
}
