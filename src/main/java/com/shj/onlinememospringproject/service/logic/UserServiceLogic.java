package com.shj.onlinememospringproject.service.logic;

import com.shj.onlinememospringproject.domain.memo.Memo;
import com.shj.onlinememospringproject.domain.memo.MemoJpaRepository;
import com.shj.onlinememospringproject.domain.user.User;
import com.shj.onlinememospringproject.domain.user.UserJpaRepository;
import com.shj.onlinememospringproject.domain.userandmemo.UserAndMemoJpaRepository;
import com.shj.onlinememospringproject.dto.memo.MemoRequestDto;
import com.shj.onlinememospringproject.dto.memo.MemoResponseDto;
import com.shj.onlinememospringproject.dto.user.UserJoinRequestDto;
import com.shj.onlinememospringproject.dto.user.UserRequestDto;
import com.shj.onlinememospringproject.dto.user.UserResponseDto;
import com.shj.onlinememospringproject.dto.user.UserUpdateNameRequestDto;
import com.shj.onlinememospringproject.dto.userandmemo.UserAndMemoRequestDto;
import com.shj.onlinememospringproject.response.exception.LoginIdDuplicateException;
import com.shj.onlinememospringproject.response.exception.NoSuchUserException;
import com.shj.onlinememospringproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
public class UserServiceLogic implements UserService {

    private final UserJpaRepository userJpaRepository;
    private final MemoJpaRepository memoJpaRepository;
    private final UserAndMemoJpaRepository userAndMemoJpaRepository;
    private final UserAndMemoServiceLogic userAndMemoServiceLogic;


    @Transactional
    @Override
    public Long save(UserJoinRequestDto userJoinRequestDto) {  // 신규 사용자 생성하고 userId 반환 기능.
        // 클라이언트가 요청한, 클라이언트와 교류한 정보니까 RequestDto 형식을 파라미터로 받음.

        String newLoginId = userJoinRequestDto.getLoginId();
        userJpaRepository.findByLoginId(newLoginId)
                .ifPresent(user -> {  // 해당 로그인아이디의 사용자가 이미 존재한다면,
                    throw new LoginIdDuplicateException();  // 회원가입 로그인아이디 중복 예외처리.
                });

        User entity = userJpaRepository.save(userJoinRequestDto.toEntity());
        return entity.getId();
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponseDto findById(Long userId) {  // userId로 검색한 사용자 1명 반환 기능.
        // 클라이언트에게 전달해야하므로, 이미 DB 레이어를 지나쳤기에 다시 entity 형식을 ResponseDto 형식으로 변환하여 빈환해야함.

        User entity = userJpaRepository.findById(userId).orElseThrow(
                ()->new NoSuchUserException());
        return new UserResponseDto(entity);
    }

    @Transactional
    @Override
    public void updateName(Long userId, UserUpdateNameRequestDto userUpdateNameRequestDto) {  // 해당 userId 사용자의 이름 수정 기능.

        User entity = userJpaRepository.findById(userId).orElseThrow(
                ()->new NoSuchUserException());

        entity.updateUsername(userUpdateNameRequestDto.getUsername());
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {  // 해당 userId의 사용자 삭제 기능. 동시에 해당 사용자의 단독 메모도 함께 삭제함.

        User userEntity = userJpaRepository.findById(userId).orElseThrow(
                ()->new NoSuchUserException());

        List<MemoResponseDto> memoResponseDtos = userAndMemoServiceLogic.findMemosByUserId(userEntity.getId());  // 해당 userId의 사용자가 가지고있는 모든 메모들 리스트 가져오기.
        List<Long> memoIds = memoResponseDtos.stream().map(MemoResponseDto::getId)
                .collect(Collectors.toList());  // 사용자와 메모와의 관계를 삭제하기 이전에, 먼저 해당 사용자가 보유한 메모의 memoId 부터 미리 리스트에 담아둠.

        userAndMemoJpaRepository.deleteAllByUser(userEntity);  // 부모 테이블인 User보다 먼저, 자식 테이블인 UserAndMemo에서 사용자와 메모와의 관계부터 삭제함.
        userJpaRepository.delete(userEntity);  // 그 이후에 부모 테이블인 User에서 해당 사용자를 삭제함.

        for(int i=0; i < memoIds.size(); i++) {
            if (userAndMemoServiceLogic.findUsersByMemoId(memoIds.get(i)).size() == 0) {  // 사용자와 메모와의 관계 삭제이후, 사용자 삭제이후, 담아두었던 memoId의 메모를 가진 사용자가 총 0명이라면,
                memoJpaRepository.deleteById(memoIds.get(i));  // 그렇다면 해당 메모도 함께 삭제 조치시킴.
            }
        }
    }

}
