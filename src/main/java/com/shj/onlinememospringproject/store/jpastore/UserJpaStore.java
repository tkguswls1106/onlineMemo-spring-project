package com.shj.onlinememospringproject.store.jpastore;

import com.shj.onlinememospringproject.domain.jpo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaStore extends JpaRepository<User, Long> {
}
