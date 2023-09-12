package com.shj.onlinememospringproject.service.logic;

import com.shj.onlinememospringproject.domain.userandmemo.UserAndMemo;
import com.shj.onlinememospringproject.response.exception.NoSuchMemoException;
import com.shj.onlinememospringproject.util.SecurityUtil;
import com.shj.onlinememospringproject.domain.friendship.FriendshipJpaRepository;
import com.shj.onlinememospringproject.domain.memo.MemoJpaRepository;
import com.shj.onlinememospringproject.domain.user.User;
import com.shj.onlinememospringproject.domain.user.UserJpaRepository;
import com.shj.onlinememospringproject.domain.userandmemo.UserAndMemoJpaRepository;
import com.shj.onlinememospringproject.dto.memo.MemoResponseDto;
import com.shj.onlinememospringproject.dto.user.UserResponseDto;
import com.shj.onlinememospringproject.dto.user.UserUpdateNameRequestDto;
import com.shj.onlinememospringproject.response.exception.NoSuchUserException;
import com.shj.onlinememospringproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
public class UserServiceLogic implements UserService {

    private final UserJpaRepository userJpaRepository;
    private final MemoJpaRepository memoJpaRepository;
    private final FriendshipJpaRepository friendshipJpaRepository;
    private final UserAndMemoJpaRepository userAndMemoJpaRepository;
    private final UserAndMemoServiceLogic userAndMemoServiceLogic;
    private final PasswordEncoder passwordEncoder;


    public UserResponseDto getMyInfoBySecurity() {  // 헤더에 있는 token값을 토대로 User의 data를 건내주는 메소드이다.
        return userJpaRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(UserResponseDto::new)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }

    public void checkTokenUser(Long userId) {  // 헤더에 있는 token값을 토대로 Security Context 내의 User의 id와 path의 id가 일치하는지 확인하는 메소드이다.
        UserResponseDto securityUser = userJpaRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(UserResponseDto::new)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));

        if (securityUser.getId() != userId) {
            throw new RuntimeException("SecurityContext의 사용자id와 path의 사용자id 불일치 에러");
        }
    }

    public void checkTokenMemo(Long memoId) {  // 헤더에 있는 token값을 토대로 Security Context 내의 User의 userId와 Memo의 userId가 일치하는지 확인하는 메소드이다.
        UserResponseDto securityUser = userJpaRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(UserResponseDto::new)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));

        memoJpaRepository.findById(memoId).orElseThrow(
                ()->new NoSuchMemoException(String.format("memoId = %d", memoId)));

        List<UserAndMemo> userAndMemos = userAndMemoJpaRepository.findAll();
        List<Long> userIds = new ArrayList<>();
        for (int i = 0; i < userAndMemos.size(); i++) {
            // userAndMemos리스트에서 memoId와 일치하는 memo객체가 존재한다면, 그 리스트 인덱스의 user객체의 userId가 Security Context 내의 User의 userId와 일치하는지 확인하고, 해당 userId를 userIds리스트에 넣어줌.
            if (userAndMemos.get(i).getMemo().getId() == memoId) {
                if (userAndMemos.get(i).getUser().getId() == securityUser.getId()) {
                    userIds.add(userAndMemos.get(i).getUser().getId());
                }
            }
        }

        if (userIds.size() <= 0) {
            throw new RuntimeException("SecurityContext의 사용자id와 메모의 사용자id 불일치 에러");
        }
    }


    @Transactional(readOnly = true)
    @Override
    public UserResponseDto findById(Long userId) {  // userId로 검색한 사용자 1명 반환 기능.
        // 클라이언트에게 전달해야하므로, 이미 DB 레이어를 지나쳤기에 다시 entity 형식을 ResponseDto 형식으로 변환하여 빈환해야함.

        User entity = userJpaRepository.findById(userId).orElseThrow(
                ()->new NoSuchUserException(String.format("userId = %d", userId)));
        return new UserResponseDto(entity);
    }

    @Transactional
    @Override
    public void updateName(Long userId, UserUpdateNameRequestDto userUpdateNameRequestDto) {  // 해당 userId 사용자의 이름 수정 기능.

        User entity = userJpaRepository.findById(userId).orElseThrow(
                ()->new NoSuchUserException(String.format("userId = %d", userId)));

        entity.updateUsername(userUpdateNameRequestDto.getUsername());
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {  // 해당 userId의 사용자 삭제 기능. 동시에 해당 사용자의 단독 메모도 함께 삭제함.

        User userEntity = userJpaRepository.findById(userId).orElseThrow(
                ()->new NoSuchUserException(String.format("userId = %d", userId)));

        List<MemoResponseDto> memoResponseDtos = userAndMemoServiceLogic.findMemosByUserId(userEntity.getId());  // 해당 userId의 사용자가 가지고있는 모든 메모들 리스트 가져오기.
        List<Long> memoIds = memoResponseDtos.stream().map(MemoResponseDto::getId)
                .collect(Collectors.toList());  // 사용자와 메모와의 관계를 삭제하기 이전에, 먼저 해당 사용자가 보유한 메모의 memoId 부터 미리 리스트에 담아둠.

        userAndMemoJpaRepository.deleteAllByUser(userEntity);  // 부모 테이블인 User보다 먼저, 자식 테이블인 UserAndMemo에서 사용자와 메모와의 관계부터 삭제함.
        for(int i=0; i < memoIds.size(); i++) {
            if (userAndMemoServiceLogic.findUsersByMemoId(memoIds.get(i)).size() == 0) {  // 사용자와 메모와의 관계 삭제이후, 사용자 삭제이후, 담아두었던 memoId의 메모를 가진 사용자가 총 0명이라면,
                memoJpaRepository.deleteById(memoIds.get(i));  // 그렇다면 해당 메모도 함께 삭제 조치시킴.
            }
        }
        friendshipJpaRepository.deleteAllBySenderUserId(userEntity.getId());  // 그 이후에 부모 테이블인 User보다 먼저, 자식 테이블인 Friendship에서 요청사용자와 친구와의 관계부터 삭제함.
        friendshipJpaRepository.deleteAllByUser(userEntity);  // 그 이후에 부모 테이블인 User보다 먼저, 자식 테이블인 Friendship에서 사용자와 친구와의 관계부터 삭제함.
        userJpaRepository.delete(userEntity);  // 최종적으로 부모 테이블인 User에서 해당 사용자를 삭제함.
    }
}
