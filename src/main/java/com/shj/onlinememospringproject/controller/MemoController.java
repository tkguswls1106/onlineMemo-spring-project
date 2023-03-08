package com.shj.onlinememospringproject.controller;

import com.shj.onlinememospringproject.dto.memo.*;
import com.shj.onlinememospringproject.dto.user.UserRequestDto;
import com.shj.onlinememospringproject.response.ResponseCode;
import com.shj.onlinememospringproject.response.ResponseData;
import com.shj.onlinememospringproject.service.MemoService;
import com.shj.onlinememospringproject.service.UserAndMemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
//@RequestMapping("")
public class MemoController {

    private final MemoService memoService;
    private final UserAndMemoService userAndMemoService;


    @PostMapping("/users/{userId}/memos")
    public ResponseEntity saveMemo(@PathVariable Long userId, @RequestBody MemoSaveRequestDto memoSaveRequestDto) {  // 신규 개인메모 생성 & 해당 메모를 사용하는 사용자 1명의 userId도 함께 조회
        MemoSaveResponseDto memoSaveResponseDto = memoService.saveMemo(userId, memoSaveRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.CREATED_MEMO, memoSaveResponseDto);
    }

    @PostMapping("/memos/{memoId}")
    public ResponseEntity inviteMemo(@PathVariable Long memoId, @RequestBody List<UserRequestDto> userRequestDtos) {  // 메모에 사용자들 초대(or 신규 공동메모 생성) & 해당 메모 사용하는 회원들 리스트와 몇명인지도 함께 조회
        MemoInviteResponseDto memoInviteResponseDto = userAndMemoService.inviteUsersToMemo(userRequestDtos, memoId);
        return ResponseData.toResponseEntity(ResponseCode.CREATED_MEMO, memoInviteResponseDto);
    }

    @GetMapping("/users/{userId}/memos")
    public ResponseEntity UserLoadMemos(@PathVariable Long userId,  // 사용자의 모든 메모들 리스트 조회 & 각 메모 사용하는 회원들 리스트와 몇명인지도 함께 조회 + 정렬 및 검색 가능
                                        @RequestParam(value = "order", required = false) String order,
                                        @RequestParam(value = "search", required = false) String search) {

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

    @GetMapping("/memos/{memoId}")
    public ResponseEntity findMemoById(@PathVariable Long memoId) {  // 메모정보 조회
        MemoResponseDto memoResponseDto = memoService.findById(memoId);

        memoResponseDto.setUserResponseDtos(
                userAndMemoService.findUsersByMemoId(memoResponseDto.getId())
        );
        memoResponseDto.setMemoHasUsersCount(
                userAndMemoService.findUsersByMemoId(memoResponseDto.getId())
        );

        return ResponseData.toResponseEntity(ResponseCode.READ_MEMO, memoResponseDto);
    }

    @PutMapping("/memos/{memoId}")
    public ResponseEntity updateMemo(@PathVariable Long memoId, @RequestBody MemoUpdateRequestDto memoUpdateRequestDto) {  // 메모 수정
        memoService.updateMemo(memoId, memoUpdateRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.UPDATE_MEMO);
    }

    @PutMapping("/memos/{memoId}/star")
    public ResponseEntity updateStar(@PathVariable Long memoId, @RequestBody MemoUpdateStarRequestDto memoUpdateStarRequestDto) {  // 메모 즐겨찾기 여부 수정
        memoService.updateIsStar(memoId, memoUpdateStarRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.UPDATE_MEMO);
    }

    @DeleteMapping("/users/{userId}/memos/{memoId}")
    public ResponseEntity deleteMemo(@PathVariable Long userId, @PathVariable Long memoId) {  // 메모 삭제. 만약 개인메모가 아닐 경우에는 메모를 삭제하지 않고 메모그룹 탈퇴로 처리함.
        memoService.deleteMemo(userId, memoId);
        return ResponseData.toResponseEntity(ResponseCode.DELETE_MEMO);
        // !!! 나중에 이거 메모 삭제 성공시, 사용자의 전체 메모리스트가 보이는 메인화면으로 리다이렉트 시키도록 변경시킬것. !!!
    }

}
