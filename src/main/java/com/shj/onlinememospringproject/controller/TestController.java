package com.shj.onlinememospringproject.controller;

import com.shj.onlinememospringproject.dto.userandmemo.UserAndMemoResponseDto;
import com.shj.onlinememospringproject.response.ResponseCode;
import com.shj.onlinememospringproject.response.ResponseData;
import com.shj.onlinememospringproject.service.UserAndMemoService;
import com.shj.onlinememospringproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
@RequestMapping("/test")
public class TestController {

    private final UserAndMemoService userAndMemoService;


    // 이건 테스트용
    @GetMapping("/{userId}/{memoId}")
    public ResponseEntity findAllUserAndMemo(@PathVariable Long userId, @PathVariable Long memoId) {
        List<UserAndMemoResponseDto> userAndMemoResponseDtos = userAndMemoService.findAllUserAndMemo(userId, memoId);
        return ResponseData.toResponseEntity(ResponseCode.READ_USER, userAndMemoResponseDtos);
    }
}
