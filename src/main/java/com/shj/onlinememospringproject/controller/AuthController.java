package com.shj.onlinememospringproject.controller;

import com.shj.onlinememospringproject.domain.user.UserJpaRepository;
import com.shj.onlinememospringproject.dto.token.TokenDto;
import com.shj.onlinememospringproject.util.SecurityUtil;
import com.shj.onlinememospringproject.dto.user.UserResponseDto;
import com.shj.onlinememospringproject.dto.user.UserSignRequestDto;
import com.shj.onlinememospringproject.response.ResponseCode;
import com.shj.onlinememospringproject.response.ResponseData;
import com.shj.onlinememospringproject.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
public class AuthController {

    private final AuthService authService;
    private final UserJpaRepository userJpaRepository;


    @GetMapping("/auth")
    public ResponseEntity isLogin() {
        UserResponseDto userResponseDto = userJpaRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(UserResponseDto::new)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));

        return ResponseData.toResponseEntity(ResponseCode.READ_IS_LOGIN, userResponseDto);
    }

    @PostMapping("/signup")
    public ResponseEntity joinUser(@RequestBody UserSignRequestDto userSignRequestDto) {  // 회원가입
        UserResponseDto userResponseDto = authService.signup(userSignRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.CREATED_USER, userResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserSignRequestDto userSignRequestDto) {  // 로그인
        TokenDto tokenDto = authService.login(userSignRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.LOGIN_SUCCESS, tokenDto);
    }
}
