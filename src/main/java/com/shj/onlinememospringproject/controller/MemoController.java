package com.shj.onlinememospringproject.controller;

import com.shj.onlinememospringproject.dto.memo.MemoSaveRequestDto;
import com.shj.onlinememospringproject.dto.memo.MemoResponseDto;
import com.shj.onlinememospringproject.dto.memo.MemoUpdateRequestDto;
import com.shj.onlinememospringproject.dto.memo.MemoUpdateStarRequestDto;
import com.shj.onlinememospringproject.response.ResponseCode;
import com.shj.onlinememospringproject.response.ResponseData;
import com.shj.onlinememospringproject.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
@RequestMapping("/memos")
public class MemoController {

    private final MemoService memoService;


    @PostMapping
    public ResponseEntity saveMemo(@RequestBody MemoSaveRequestDto memoSaveRequestDto) {  // 신규 개인메모 생성 (참고로 여기 memoSaveRequestDto 안에 userId도 들어있음.)
        memoService.saveMemo(memoSaveRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.CREATED_MEMO);
    }

    @GetMapping("/{memoId}")
    public ResponseEntity findMemoById(@PathVariable Long memoId) {  // 메모정보 조회
        MemoResponseDto memoResponseDto = memoService.findById(memoId);
        return ResponseData.toResponseEntity(ResponseCode.READ_MEMO, memoResponseDto);
    }

    @PutMapping("/{memoId}")
    public ResponseEntity updateMemo(@PathVariable Long memoId, @RequestBody MemoUpdateRequestDto memoUpdateRequestDto) {  // 메모 수정
        memoService.updateMemo(memoId, memoUpdateRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.UPDATE_MEMO);
    }

    @PutMapping("/{memoId}/star")
    public ResponseEntity updateStar(@PathVariable Long memoId, @RequestBody MemoUpdateStarRequestDto memoUpdateStarRequestDto) {  // 메모 즐겨찾기 여부 수정
        memoService.updateIsStar(memoId, memoUpdateStarRequestDto);
        return ResponseData.toResponseEntity(ResponseCode.UPDATE_MEMO);
    }

    @DeleteMapping("/{memoId}")
    public ResponseEntity deleteMemo(@RequestBody Integer userId, @PathVariable Long memoId) {  // 메모 삭제. 만약 개인메모가 아닐 경우에는 메모를 삭제하지 않고 메모그룹 탈퇴로 처리함.
        memoService.deleteMemo(Long.valueOf(userId), memoId);
        return ResponseData.toResponseEntity(ResponseCode.DELETE_MEMO);
        // !!! 나중에 이거 메모 삭제 성공시, 사용자의 전체 메모리스트가 보이는 메인화면으로 리다이렉트 시키도록 변경시킬것. !!!
    }

}
