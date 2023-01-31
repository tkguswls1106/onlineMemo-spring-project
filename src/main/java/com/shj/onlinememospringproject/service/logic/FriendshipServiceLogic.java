package com.shj.onlinememospringproject.service.logic;

import com.shj.onlinememospringproject.domain.friendship.FriendshipJpaRepository;
import com.shj.onlinememospringproject.service.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
public class FriendshipServiceLogic implements FriendshipService {

    private final FriendshipJpaRepository friendshipJpaRepository;

}
