package com.shj.onlinememospringproject.service.auth;

import com.shj.onlinememospringproject.domain.user.User;
import com.shj.onlinememospringproject.domain.user.UserJpaRepository;
import com.shj.onlinememospringproject.dto.token.TokenDto;
import com.shj.onlinememospringproject.dto.user.UserSignRequestDto;
import com.shj.onlinememospringproject.dto.user.UserResponseDto;
import com.shj.onlinememospringproject.jwt.TokenProvider;
import com.shj.onlinememospringproject.response.exception.LoginIdDuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserJpaRepository userJpaRepository;
    private final AuthenticationManagerBuilder managerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;


    @Transactional
    public UserResponseDto signup(UserSignRequestDto userSignRequestDto) {  // 신규 사용자 생성하고 user 반환 기능.
        // 클라이언트가 요청한, 클라이언트와 교류한 정보니까 RequestDto 형식을 파라미터로 받음.

        String newLoginId = userSignRequestDto.getLoginId();
        userJpaRepository.findByLoginId(newLoginId)
                .ifPresent(user -> {  // 해당 로그인아이디의 사용자가 이미 존재한다면,
                    throw new LoginIdDuplicateException();  // 회원가입 로그인아이디 중복 예외처리.
                });

        User entity = userJpaRepository.save(userSignRequestDto.toEntity(passwordEncoder));
        return new UserResponseDto(entity);
    }

    @Transactional
    public TokenDto login(UserSignRequestDto userSignRequestDto) {

        UsernamePasswordAuthenticationToken authenticationToken = userSignRequestDto.toAuthentication();

        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

        return tokenProvider.generateTokenDto(authentication);
    }
}
