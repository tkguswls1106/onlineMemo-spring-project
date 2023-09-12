package com.shj.onlinememospringproject.controller;

import com.shj.onlinememospringproject.dto.memo.*;
import com.shj.onlinememospringproject.dto.user.UserRequestDtos;
import com.shj.onlinememospringproject.dto.user.UserResponseDto;
import com.shj.onlinememospringproject.response.ResponseCode;
import com.shj.onlinememospringproject.response.ResponseData;
import com.shj.onlinememospringproject.response.responseitem.MessageItem;
import com.shj.onlinememospringproject.service.MemoService;
import com.shj.onlinememospringproject.service.UserAndMemoService;
import com.shj.onlinememospringproject.service.logic.UserServiceLogic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = {"https://www.onlinememo.kr", "http://localhost:3000"}, allowedHeaders = "*")
@CrossOrigin(origins = "https://www.onlinememo.kr", allowedHeaders = "*")
@Api(tags = {"Memo"})
@RestController
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
public class MemoController {

    private final MemoService memoService;
    private final UserAndMemoService userAndMemoService;
    private final UserServiceLogic userServiceLogic;


    @ApiOperation(value = "신규 개인메모 생성", notes = "신규 개인메모를 생성합니다. (jwt O)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "이 api의 성공적인 응답으로는 statusCode=201가 올바릅니다. 밑의 Example Value는 무시하십시오."),
            @ApiResponse(code = 201, message = MessageItem.CREATED_MEMO, response = MemoSaveResponseDto.class)
    })
    @PostMapping("/users/{userId}/memos")
    public ResponseEntity saveMemo(@PathVariable Long userId, @RequestBody MemoSaveRequestDto memoSaveRequestDto) {  // 신규 개인메모 생성 & 해당 메모를 사용하는 사용자 1명의 userId도 함께 조회
        userServiceLogic.checkTokenUser(userId);

        MemoSaveResponseDto memoSaveResponseDto = memoService.saveMemo(userId, memoSaveRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.CREATED_MEMO, memoSaveResponseDto);
    }

    @ApiOperation(value = "신규 공동메모 생성 or 메모에 친구 초대", notes = "신규 공동메모를 생성하거나, 기존 메모에 친구를 초대합니다. (jwt O)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "이 api의 성공적인 응답으로는 statusCode=201가 올바릅니다. 밑의 Example Value는 무시하십시오."),
            @ApiResponse(code = 201, message = MessageItem.CREATED_MEMO, response = MemoInviteResponseDto.class)
    })
    @PostMapping("/memos/{memoId}")
    public ResponseEntity inviteMemo(@PathVariable Long memoId, @RequestBody UserRequestDtos userRequestDtos) {  // 메모에 사용자들 초대(or 신규 공동메모 생성) & 해당 메모 사용하는 회원들 리스트와 몇명인지도 함께 조회
        userServiceLogic.checkTokenMemo(memoId);

        MemoInviteResponseDto memoInviteResponseDto = userAndMemoService.inviteUsersToMemo(userRequestDtos, memoId);
        return ResponseData.toResponseEntity(ResponseCode.CREATED_MEMO, memoInviteResponseDto);
    }

    @ApiOperation(value = "메모 목록 조회", notes = "메모 목록을 조회합니다. 반환된 데이터는 '여러 객체를 담은 리스트' 형식입니다. (jwt O)")
    @ApiResponses({@ApiResponse(code = 200, message = MessageItem.READ_MEMOLIST, response = MemoResponseDto.class, responseContainer = "List")})
    @GetMapping("/users/{userId}/memos")
    public ResponseEntity UserLoadMemos(@PathVariable Long userId,  // 사용자의 모든 메모들 리스트 조회 & 각 메모 사용하는 회원들 리스트와 몇명인지도 함께 조회 + 정렬 및 검색 가능
                                        @RequestParam(value = "order", required = false) String order,
                                        @RequestParam(value = "search", required = false) String search) {

        userServiceLogic.checkTokenUser(userId);

        List<MemoResponseDto> memoResponseDtos = userAndMemoService.findMemosByUserId(userId);

        for (int i = 0; i < memoResponseDtos.size(); i++) {
            memoResponseDtos.get(i).setUserResponseDtos(
                    userAndMemoService.findUsersByMemoId(memoResponseDtos.get(i).getId())
            );
            memoResponseDtos.get(i).setMemoHasUsersCount(
                    userAndMemoService.findUsersByMemoId(memoResponseDtos.get(i).getId())
            );
        }

        List<MemoResponseDto> resultMemoResponseDtos = memoService.sortAndsearch(memoResponseDtos, order, search);

        return ResponseData.toResponseEntity(ResponseCode.READ_MEMOLIST, resultMemoResponseDtos);
    }

    @ApiOperation(value = "메모정보 조회", notes = "메모 1개의 정보를 조회합니다. (jwt O)")
    @ApiResponses({@ApiResponse(code = 200, message = MessageItem.READ_MEMO, response = MemoResponseDto.class)})
    @GetMapping("/memos/{memoId}")
    public ResponseEntity findMemoById(@PathVariable Long memoId) {  // 메모정보 조회
        userServiceLogic.checkTokenMemo(memoId);

        MemoResponseDto memoResponseDto = memoService.findById(memoId);

        memoResponseDto.setUserResponseDtos(
                userAndMemoService.findUsersByMemoId(memoResponseDto.getId())
        );
        memoResponseDto.setMemoHasUsersCount(
                userAndMemoService.findUsersByMemoId(memoResponseDto.getId())
        );

        return ResponseData.toResponseEntity(ResponseCode.READ_MEMO, memoResponseDto);
    }

    @ApiOperation(value = "메모 제목과 내용 수정", notes = "메모 1개의 제목과 내용을 수정합니다. (jwt O)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "이 api의 성공적인 응답으로는 statusCode=204가 올바릅니다. 밑의 Example Value는 무시하십시오."),
            @ApiResponse(code = 204, message = MessageItem.UPDATE_MEMO + " / 반환 데이터는 없습니다. 밑의 Example Value는 무시하십시오.")
    })
    @PutMapping("/memos/{memoId}")
    public ResponseEntity updateMemo(@PathVariable Long memoId, @RequestBody MemoUpdateRequestDto memoUpdateRequestDto) {  // 메모 수정
        userServiceLogic.checkTokenMemo(memoId);

        memoService.updateMemo(memoId, memoUpdateRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.UPDATE_MEMO);
    }

    @ApiOperation(value = "메모 즐겨찾기 수정", notes = "메모 1개의 즐겨찾기 여부를 수정합니다. (jwt O)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "이 api의 성공적인 응답으로는 statusCode=204가 올바릅니다. 밑의 Example Value는 무시하십시오."),
            @ApiResponse(code = 204, message = MessageItem.UPDATE_MEMO + " / 반환 데이터는 없습니다. 밑의 Example Value는 무시하십시오.")
    })
    @PutMapping("/memos/{memoId}/star")
    public ResponseEntity updateStar(@PathVariable Long memoId, @RequestBody MemoUpdateStarRequestDto memoUpdateStarRequestDto) {  // 메모 즐겨찾기 여부 수정
        userServiceLogic.checkTokenMemo(memoId);

        memoService.updateIsStar(memoId, memoUpdateStarRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.UPDATE_MEMO);
    }

    @ApiOperation(value = "메모 삭제", notes = "메모 1개를 삭제합니다. (jwt O)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "이 api의 성공적인 응답으로는 statusCode=204가 올바릅니다. 밑의 Example Value는 무시하십시오."),
            @ApiResponse(code = 204, message = MessageItem.DELETE_MEMO + " / 반환 데이터는 없습니다. 밑의 Example Value는 무시하십시오.")
    })
    @DeleteMapping("/users/{userId}/memos/{memoId}")
    public ResponseEntity deleteMemo(@PathVariable Long userId, @PathVariable Long memoId) {  // 메모 삭제. 만약 개인메모가 아닐 경우에는 메모를 삭제하지 않고 메모그룹 탈퇴로 처리함.
        userServiceLogic.checkTokenUser(userId);

        memoService.deleteMemo(userId, memoId);
        return ResponseData.toResponseEntity(ResponseCode.DELETE_MEMO);
    }

}
