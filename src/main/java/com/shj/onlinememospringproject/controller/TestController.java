package com.shj.onlinememospringproject.controller;

import com.shj.onlinememospringproject.dto.user.UserResponseDto;
import com.shj.onlinememospringproject.response.ResponseCode;
import com.shj.onlinememospringproject.response.ResponseData;
import com.shj.onlinememospringproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = {"https://www.onlinememo.kr", "http://localhost:3000"}, allowedHeaders = "*")
@CrossOrigin(origins = "https://www.onlinememo.kr", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
public class TestController {

    @GetMapping("/health")
    public ResponseEntity health() {  // aws health check용 api
        return ResponseData.toResponseEntity(ResponseCode.HEALTHY_SUCCESS);
    }

//    @GetMapping("/testapi")
//    public ResponseEntity testapi() {  // 테스트용 api
//        // 단, 이는 api테스트를 위한 userId가 1인 테스트용 계정을 생성해두고 탈퇴하지않아야 유의미하게 사용가능하다.
//
//        Long userId = Long.valueOf(1);
//        UserResponseDto userResponseDto = userService.findById(userId);
//
//        return ResponseData.toResponseEntity(ResponseCode.READ_USER, userResponseDto);
//    }

}
