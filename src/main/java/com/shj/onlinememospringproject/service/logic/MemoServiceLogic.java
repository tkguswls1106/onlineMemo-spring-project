package com.shj.onlinememospringproject.service.logic;

import com.shj.onlinememospringproject.domain.memo.Memo;
import com.shj.onlinememospringproject.domain.memo.MemoJpaRepository;
import com.shj.onlinememospringproject.domain.user.User;
import com.shj.onlinememospringproject.domain.user.UserJpaRepository;
import com.shj.onlinememospringproject.domain.userandmemo.UserAndMemo;
import com.shj.onlinememospringproject.domain.userandmemo.UserAndMemoJpaRepository;
import com.shj.onlinememospringproject.dto.memo.MemoRequestDto;
import com.shj.onlinememospringproject.dto.memo.MemoResponseDto;
import com.shj.onlinememospringproject.dto.user.UserRequestDto;
import com.shj.onlinememospringproject.dto.user.UserResponseDto;
import com.shj.onlinememospringproject.dto.userandmemo.UserAndMemoRequestDto;
import com.shj.onlinememospringproject.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
public class MemoServiceLogic implements MemoService {

    private final MemoJpaRepository memoJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final UserAndMemoJpaRepository userAndMemoJpaRepository;


    @Transactional
    @Override
    public Long saveMemo(Long userId, MemoRequestDto memoRequestDto) { // 신규 메모 생성하고 memoId 반환 기능.
        // 사용자 없이 메모 단독으로는 생성이 불가능하므로 파라미터 2개 필요.
        // 클라이언트가 요청한, 클라이언트와 교류한 정보니까 RequestDto 형식을 파라미터로 받음.

        User userEntity = userJpaRepository.findById(userId).orElseThrow(
                ()->new IllegalArgumentException("해당 userId의 사용자는 존재하지 않습니다!! => userId: " + userId));  // userId에 해당되는 User 객체 찾아오기
        UserRequestDto userRequestDto = new UserRequestDto(userEntity.getId(), userEntity.getLoginId(), userEntity.getUsername());  //  userAndMemoJpaRepository에 save하기전에 먼저, 보안되어야할 컬럼을 솎아내서 한정적으로 가져오기위헤 dto를 한번 거침.
        User userSecondEntity = userRequestDto.toEntity();  // 보안되어야 먽 컬럼을 솎아낸 dto를 다시 entity 형식으로 변환.

        Memo memoEntity = memoJpaRepository.save(memoRequestDto.toEntity());  // 메모 저장하고

        UserAndMemoRequestDto userAndMemoRequestDto = new UserAndMemoRequestDto(userSecondEntity, memoEntity);
        userAndMemoJpaRepository.save(userAndMemoRequestDto.toEntity());  // UserAndMemo 테이블에도 저장.

        return memoEntity.getId();
    }

    @Transactional(readOnly = true)
    @Override
    public MemoResponseDto findById(Long memoId) {  // memoId로 검색한 메모 1개 반환 기능.
        // 클라이언트에게 전달해야하므로, 이미 DB 레이어를 지나쳤기에 다시 entity 형식을 ResponseDto 형식으로 변환하여 빈환해야함.

        Memo entity = memoJpaRepository.findById(memoId).orElseThrow(
                ()->new IllegalArgumentException("해당 memoId의 메모는 존재하지 않습니다!! => memoId: " + memoId));

        return new MemoResponseDto(entity);
    }

}
