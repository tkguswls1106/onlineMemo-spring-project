package com.shj.onlinememospringproject.store.repository;

import com.shj.onlinememospringproject.store.jpastore.UserJpaStore;
import com.shj.onlinememospringproject.store.normalstore.UserStore;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements UserStore {

    private UserJpaStore userJpaStore;
    public UserRepository(UserJpaStore userJpaStore) {
        this.userJpaStore = userJpaStore;
    }
}
