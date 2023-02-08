package com.shj.onlinememospringproject.service.logic;

import com.shj.onlinememospringproject.domain.memo.Memo;
import com.shj.onlinememospringproject.domain.memo.MemoJpaRepository;
import com.shj.onlinememospringproject.domain.user.User;
import com.shj.onlinememospringproject.domain.user.UserJpaRepository;
import com.shj.onlinememospringproject.domain.userandmemo.UserAndMemoJpaRepository;
import com.shj.onlinememospringproject.dto.memo.MemoSaveRequestDto;
import com.shj.onlinememospringproject.dto.memo.MemoResponseDto;
import com.shj.onlinememospringproject.dto.memo.MemoUpdateRequestDto;
import com.shj.onlinememospringproject.dto.memo.MemoUpdateStarRequestDto;
import com.shj.onlinememospringproject.dto.user.UserRequestDto;
import com.shj.onlinememospringproject.dto.user.UserResponseDto;
import com.shj.onlinememospringproject.dto.userandmemo.UserAndMemoRequestDto;
import com.shj.onlinememospringproject.response.exception.NoSuchMemoException;
import com.shj.onlinememospringproject.response.exception.NoSuchUserException;
import com.shj.onlinememospringproject.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
public class MemoServiceLogic implements MemoService {

    private final UserJpaRepository userJpaRepository;
    private final MemoJpaRepository memoJpaRepository;
    private final UserAndMemoJpaRepository userAndMemoJpaRepository;
    private final UserAndMemoServiceLogic userAndMemoServiceLogic;


    @Transactional
    @Override
    public Long saveMemo(MemoSaveRequestDto memoSaveRequestDto) {  // 신규 메모 생성하고 memoId 반환 기능.
        // 사용자 없이 메모 단독으로는 생성이 불가능하므로 나중에 컨트롤러에서 Requestbody로 memoSaveRequestDto 안에 userId도 함께 받아줌.
        // 클라이언트가 요청한, 클라이언트와 교류한 정보니까 RequestDto 형식을 파라미터로 받음.

        Long userId = memoSaveRequestDto.getUserId();
        User userEntity = userJpaRepository.findById(userId).orElseThrow(
                ()->new NoSuchUserException());  // userId에 해당되는 User 객체 찾아오기
        UserRequestDto userRequestDto = new UserRequestDto(userEntity.getId(), userEntity.getLoginId(), userEntity.getUsername());  //  userAndMemoJpaRepository에 save하기전에 먼저, 보안되어야할 컬럼을 솎아내서 한정적으로 가져오기위헤 dto를 한번 거침.
        User userSecondEntity = userRequestDto.toEntity();  // 보안되어야할 컬럼을 솎아낸 dto를 다시 entity 형식으로 변환.

        Memo memoEntity = memoJpaRepository.save(memoSaveRequestDto.toEntity());  // 메모 저장하고

        UserAndMemoRequestDto userAndMemoRequestDto = new UserAndMemoRequestDto(userSecondEntity, memoEntity);
        userAndMemoJpaRepository.save(userAndMemoRequestDto.toEntity());  // UserAndMemo 테이블에도 저장.

        return memoEntity.getId();
    }

    @Transactional(readOnly = true)
    @Override
    public MemoResponseDto findById(Long memoId) {  // memoId로 검색한 메모 1개 반환 기능.
        // 클라이언트에게 전달해야하므로, 이미 DB 레이어를 지나쳤기에 다시 entity 형식을 ResponseDto 형식으로 변환하여 빈환해야함.

        Memo entity = memoJpaRepository.findById(memoId).orElseThrow(
                ()->new NoSuchMemoException());

        return new MemoResponseDto(entity);
    }

    @Transactional
    @Override
    public void updateMemo(Long memoId, MemoUpdateRequestDto memoUpdateRequestDto) {  // 해당 memoId의 메모 수정 기능.

        Memo entity = memoJpaRepository.findById(memoId).orElseThrow(
                ()->new NoSuchMemoException());

        entity.updateMemo(memoUpdateRequestDto.getTitle(), memoUpdateRequestDto.getContent());
    }

    @Transactional
    @Override
    public void updateIsStar(Long memoId, MemoUpdateStarRequestDto memoUpdateStarRequestDto) {  // 해당 memoId의 즐겨찾기 여부 수정 기능.

        memoJpaRepository.findById(memoId).orElseThrow(
                ()->new NoSuchMemoException());

        memoJpaRepository.updateStar(memoId, memoUpdateStarRequestDto.getIsStar());
    }

    @Transactional
    @Override
    public void deleteMemo(Long userId, Long memoId) {  // 해당 memoId의 메모 삭제 기능. 만약 개인메모가 아닐 경우에는 메모를 삭제하지 않고 메모그룹 탈퇴로 처리함.

        User userEntity = userJpaRepository.findById(userId).orElseThrow(
                ()->new NoSuchUserException());
        Memo memoEntity = memoJpaRepository.findById(memoId).orElseThrow(
                ()->new NoSuchMemoException());

        List<UserResponseDto> userResponseDtos = userAndMemoServiceLogic.findUsersByMemoId(memoEntity.getId());  // 해당 memoId의 메모를 가지고있는 모든 사용자들 리스트 가져오기.
        int memoHasUserCount = userResponseDtos.size();  // 해당 메모를 가지고 있는 사용자의 수를 카운트.

        if (memoHasUserCount > 1) {  // 해당 메모가 개인메모가 아니라면 (공동 메모라면)
            userAndMemoJpaRepository.deleteByUserAndMemo(userEntity, memoEntity);  // 메모 삭제없이 사용자와메모 관계만 삭제.
            // 즉, 메모 삭제 없이, 공동메모 그룹 탈퇴 처리만 하였음.
        }
        else {  // 해당 메모가 개인메모라면
            userAndMemoJpaRepository.deleteByUserAndMemo(userEntity, memoEntity);  // 부모 테이블인 Memo보다 먼저, 자식 테이블인 UserAndMemo에서 사용자와 메모와의 관계부터 삭제함.
            memoJpaRepository.deleteById(memoId);  // 그 이후에 부모 테이블인 Memo에서 해당 메모를 삭제함.
        }
    }

}
