package com.shj.onlinememospringproject.controller;

import com.shj.onlinememospringproject.dto.token.TokenDto;
import com.shj.onlinememospringproject.dto.user.*;
import com.shj.onlinememospringproject.response.responseitem.MessageItem;
import com.shj.onlinememospringproject.util.SecurityUtil;
import com.shj.onlinememospringproject.response.ResponseCode;
import com.shj.onlinememospringproject.response.ResponseData;
import com.shj.onlinememospringproject.service.auth.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = {"https://www.onlinememo.kr", "http://localhost:3000"}, allowedHeaders = "*")
@CrossOrigin(origins = "https://www.onlinememo.kr", allowedHeaders = "*")
@Api(tags = {"Auth"})
@RestController
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
public class AuthController {

    private final AuthService authService;


    @ApiOperation(value = "로그인 상태 여부 확인", notes = "해당 페이지의 로그인 상태 여부를 확인합니다. (jwt O&X)")
    @ApiResponses({@ApiResponse(code = 200, message = MessageItem.READ_IS_LOGIN, response = UserIdResponseDto.class)})
    @GetMapping("/auth")
    public ResponseEntity isLogin() {  // 로그인 상태 여부 확인
        Long userId = SecurityUtil.getCurrentMemberId();
        UserIdResponseDto userIdResponseDto = new UserIdResponseDto(userId);

        return ResponseData.toResponseEntity(ResponseCode.READ_IS_LOGIN, userIdResponseDto);
    }

    @ApiOperation(value = "회원가입", notes = "회원가입 합니다. (jwt X)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "이 api의 성공적인 응답으로는 statusCode=201가 올바릅니다. 밑의 Example Value는 무시하십시오."),
            @ApiResponse(code = 201, message = MessageItem.CREATED_USER, response = UserResponseDto.class)
    })
    @PostMapping("/signup")
    public ResponseEntity joinUser(@RequestBody UserSignupRequestDto userSignupRequestDto) {  // 회원가입
        UserResponseDto userResponseDto = authService.signup(userSignupRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.CREATED_USER, userResponseDto);
    }

    @ApiOperation(value = "로그인", notes = "로그인 합니다. (jwt X)")
    @ApiResponses({@ApiResponse(code = 200, message = MessageItem.LOGIN_SUCCESS, response = TokenDto.class)})
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginRequestDto userLoginRequestDto) {  // 로그인
        TokenDto tokenDto = authService.login(userLoginRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.LOGIN_SUCCESS, tokenDto);
    }

    @ApiOperation(value = "GET_login AWS에러 제거", notes = "AWS에서 GET method로 login api가 실행될시에 발생하는 에러를 제거합니다. (jwt X)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "이 api의 성공적인 응답으로는 statusCode=204가 올바릅니다. 밑의 Example Value는 무시하십시오."),
            @ApiResponse(code = 204, message = MessageItem.GET_LOGIN + " / 반환 데이터는 없습니다. 밑의 Example Value는 무시하십시오.")
    })
    @GetMapping("/login")
    public ResponseEntity getLogin() {  // 로그인 api가 get방식으로 요청올시에
        return ResponseData.toResponseEntity(ResponseCode.GET_LOGIN);
    }

    @ApiOperation(value = "계정 비밀번호 변경", notes = "회원의 계정 비밀번호를 변경합니다. (jwt X)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "이 api의 성공적인 응답으로는 statusCode=204가 올바릅니다. 밑의 Example Value는 무시하십시오."),
            @ApiResponse(code = 204, message = MessageItem.UPDATE_PASSWORD + " / 반환 데이터는 없습니다. 밑의 Example Value는 무시하십시오.")
    })
    @PutMapping("/password")
    public ResponseEntity updatePassword(@RequestBody UserUpdatePwRequestDto userUpdatePwRequestDto) {  // 비밀번호 수정
        authService.updatePw(userUpdatePwRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.UPDATE_PASSWORD);
    }
}
