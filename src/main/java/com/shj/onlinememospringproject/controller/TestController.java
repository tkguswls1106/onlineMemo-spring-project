package com.shj.onlinememospringproject.controller;

import com.shj.onlinememospringproject.response.ResponseCode;
import com.shj.onlinememospringproject.response.ResponseData;
import com.shj.onlinememospringproject.response.responseitem.MessageItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = {"https://www.onlinememo.kr", "http://localhost:3000"}, allowedHeaders = "*")
@CrossOrigin(origins = "https://www.onlinememo.kr", allowedHeaders = "*")
@Api(tags = {"AWS test"})
@RestController
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
public class TestController {

    @ApiOperation(value = "AWS health check", notes = "AWS에서의 health check를 위한 api입니다. (jwt X)")
    @ApiResponses({@ApiResponse(code = 200, message = MessageItem.HEALTHY_SUCCESS + " / 반환 데이터는 없습니다. 밑의 Example Value는 무시하십시오.")})
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
