package com.shj.onlinememospringproject.service.logic;

import com.shj.onlinememospringproject.domain.userandmemo.UserAndMemoJpaRepository;
import com.shj.onlinememospringproject.service.UserAndMemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
public class UserAndMemoServiceLogic implements UserAndMemoService {

    private final UserAndMemoJpaRepository userAndMemoJpaRepository;

}
