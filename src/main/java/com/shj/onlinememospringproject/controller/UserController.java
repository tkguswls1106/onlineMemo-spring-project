package com.shj.onlinememospringproject.controller;

import com.shj.onlinememospringproject.dto.memo.MemoResponseDto;
import com.shj.onlinememospringproject.dto.user.UserJoinRequestDto;
import com.shj.onlinememospringproject.dto.user.UserResponseDto;
import com.shj.onlinememospringproject.dto.user.UserUpdateNameRequestDto;
import com.shj.onlinememospringproject.response.ResponseCode;
import com.shj.onlinememospringproject.response.ResponseData;
import com.shj.onlinememospringproject.service.UserAndMemoService;
import com.shj.onlinememospringproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserAndMemoService userAndMemoService;

    @PostMapping
    public ResponseEntity joinUser(@RequestBody UserJoinRequestDto userJoinRequestDto) {  // 회원가입
        userService.save(userJoinRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.CREATED_USER);
    }

    @GetMapping("/{userId}")
    public ResponseEntity findUserById(@PathVariable Long userId) {  // 회원정보 조회
        UserResponseDto userResponseDto = userService.findById(userId);
        return ResponseData.toResponseEntity(ResponseCode.READ_USER, userResponseDto);
    }

    @PutMapping("/{userId}")
    public ResponseEntity updateName(@PathVariable Long userId, @RequestBody UserUpdateNameRequestDto userUpdateNameRequestDto) {  // 회원이름 수정
        userService.updateName(userId, userUpdateNameRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.UPDATE_USER);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable Long userId) {  // 회원 탈퇴
        userService.deleteUser(userId);
        return ResponseData.toResponseEntity(ResponseCode.DELETE_USER);
        // !!! 나중에 이거 회원 탈퇴 성공시, 로그인 화면으로 리다이렉트 시키도록 변경시킬것. !!!
    }

    @GetMapping("/{userId}/memos")
    public ResponseEntity UserLoadMemos(@PathVariable Long userId) {  // 사용자의 모든 메모들 리스트 조회
        List<MemoResponseDto> memoResponseDtos = userAndMemoService.findMemosByUserId(userId);
        return ResponseData.toResponseEntity(ResponseCode.READ_MEMOLIST, memoResponseDtos);
    }

}
