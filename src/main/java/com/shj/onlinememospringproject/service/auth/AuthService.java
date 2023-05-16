package com.shj.onlinememospringproject.service.auth;

import com.shj.onlinememospringproject.domain.user.User;
import com.shj.onlinememospringproject.domain.user.UserJpaRepository;
import com.shj.onlinememospringproject.dto.token.TokenDto;
import com.shj.onlinememospringproject.dto.user.UserLoginRequestDto;
import com.shj.onlinememospringproject.dto.user.UserSignupRequestDto;
import com.shj.onlinememospringproject.dto.user.UserResponseDto;
import com.shj.onlinememospringproject.dto.user.UserUpdatePwRequestDto;
import com.shj.onlinememospringproject.jwt.TokenProvider;
import com.shj.onlinememospringproject.response.exception.LoginIdDuplicateException;
import com.shj.onlinememospringproject.response.exception.NoSuchUserException;
import com.shj.onlinememospringproject.util.SecurityUtil;
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
    public UserResponseDto signup(UserSignupRequestDto userSignupRequestDto) {  // 신규 사용자 생성하고 user 반환 기능.
        // 클라이언트가 요청한, 클라이언트와 교류한 정보니까 RequestDto 형식을 파라미터로 받음.

        String newLoginId = userSignupRequestDto.getLoginId();
        userJpaRepository.findByLoginId(newLoginId)
                .ifPresent(user -> {  // 해당 로그인아이디의 사용자가 이미 존재한다면,
                    throw new LoginIdDuplicateException();  // 회원가입 로그인아이디 중복 예외처리.
                });

        User entity = userJpaRepository.save(userSignupRequestDto.toEntity(passwordEncoder));
        return new UserResponseDto(entity);
    }

    @Transactional
    public TokenDto login(UserLoginRequestDto userLoginRequestDto) {  // 로그인 기능.

        UsernamePasswordAuthenticationToken authenticationToken = userLoginRequestDto.toAuthentication();

        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
        // 여기서 실제로 검증이 이루어진다.

        return tokenProvider.generateTokenDto(authentication);
    }

    @Transactional
    public void updatePw(UserUpdatePwRequestDto userUpdatePwRequestDto) {  // 사용자의 비밀번호 수정 기능.

        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto(userUpdatePwRequestDto.getLoginId(), userUpdatePwRequestDto.getFirstPw());
        UsernamePasswordAuthenticationToken authenticationToken = userLoginRequestDto.toAuthentication();
        managerBuilder.getObject().authenticate(authenticationToken);
        // 여기서 로그인이 가능한지 실제로 검증이 이루어진다.

        User entity = userJpaRepository.findByLoginId(userUpdatePwRequestDto.getLoginId()).orElseThrow(
                ()->new NoSuchUserException());

        entity.updateFirstPw(passwordEncoder.encode(userUpdatePwRequestDto.getNewFirstPw()));
    }
}
