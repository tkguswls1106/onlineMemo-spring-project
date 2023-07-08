package com.shj.onlinememospringproject.service.logic;

import com.shj.onlinememospringproject.domain.memo.Memo;
import com.shj.onlinememospringproject.domain.memo.MemoJpaRepository;
import com.shj.onlinememospringproject.domain.user.User;
import com.shj.onlinememospringproject.domain.user.UserJpaRepository;
import com.shj.onlinememospringproject.domain.userandmemo.UserAndMemo;
import com.shj.onlinememospringproject.domain.userandmemo.UserAndMemoJpaRepository;
import com.shj.onlinememospringproject.dto.memo.MemoInviteResponseDto;
import com.shj.onlinememospringproject.dto.memo.MemoResponseDto;
import com.shj.onlinememospringproject.dto.user.UserRequestDto;
import com.shj.onlinememospringproject.dto.user.UserRequestDtos;
import com.shj.onlinememospringproject.dto.user.UserResponseDto;
import com.shj.onlinememospringproject.dto.userandmemo.UserAndMemoRequestDto;
import com.shj.onlinememospringproject.response.exception.NoSuchMemoException;
import com.shj.onlinememospringproject.response.exception.NoSuchUserException;
import com.shj.onlinememospringproject.response.exception.UserAndMemoDuplicateException;
import com.shj.onlinememospringproject.service.UserAndMemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
public class UserAndMemoServiceLogic implements UserAndMemoService {

    private final UserJpaRepository userJpaRepository;
    private final MemoJpaRepository memoJpaRepository;
    private final UserAndMemoJpaRepository userAndMemoJpaRepository;


    @Transactional(readOnly = true)
    @Override
    public List<MemoResponseDto> findMemosByUserId(Long userId) {  // userId와 일치하는 사용자의 메모들 리스트를 정렬후 반환 기능.
        // 클라이언트에게 전달해야하므로, 이미 DB 레이어를 지나쳤기에 다시 entity 형식을 ResponseDto 형식으로 변환하여 빈환해야함.

        userJpaRepository.findById(userId).orElseThrow(
                ()->new NoSuchUserException(String.format("userId = %d", userId)));  // 우선 userId와 일치하는 사용자가 존재하는지부터 확인.

        List<UserAndMemo> userAndMemos = userAndMemoJpaRepository.findAll();

        List<Memo> memos = new ArrayList<>();
        for (int i = 0; i < userAndMemos.size(); i++) {  // userAndMemos리스트에서 userId와 일치하는 user객체가 존재한다면 그 memo객체를 memos리스트에 넣어줌.
            if (userAndMemos.get(i).getUser().getId() == userId) {
                memos.add(userAndMemos.get(i).getMemo());
            }
        }

        return memos.stream().map(MemoResponseDto::new)
                .sorted(Comparator.comparing(MemoResponseDto::getId, Comparator.reverseOrder()))  // 메모 id 내림차순 정렬 후
                .sorted(Comparator.comparing(MemoResponseDto::getDateTimeModifiedDate, Comparator.reverseOrder()))  // 메모 수정날짜 내림차순 정렬
                .collect(Collectors.toList());  // 정렬 완료한 리스트 반환
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserResponseDto> findUsersByMemoId(Long memoId) {  // memoId와 일치하는 메모의 사용자들 리스트를 정렬후 반환 기능.
        // 클라이언트에게 전달해야하므로, 이미 DB 레이어를 지나쳤기에 다시 entity 형식을 ResponseDto 형식으로 변환하여 빈환해야함.

        memoJpaRepository.findById(memoId).orElseThrow(
                ()->new NoSuchMemoException(String.format("memoId = %d", memoId)));  // 우선 해당 memoId와 일치하는 메모가 존재하는지부터 확인.

        List<UserAndMemo> userAndMemos = userAndMemoJpaRepository.findAll();

        List<User> users = new ArrayList<>();
        for (int i = 0; i < userAndMemos.size(); i++) {  // userAndMemos리스트에서 userId와 일치하는 user객체가 존재한다면 그 memo객체를 memos리스트에 넣어줌.
            if (userAndMemos.get(i).getMemo().getId() == memoId) {
                users.add(userAndMemos.get(i).getUser());
            }
        }

        return users.stream().map(UserResponseDto::new)
                .sorted(Comparator.comparing(UserResponseDto::getId, Comparator.reverseOrder()))  // 사용자 id 내림차순 정렬 후
                .sorted(Comparator.comparing(UserResponseDto::getUsername))  // 사용자 이름 오름차순 정렬
                .collect(Collectors.toList());  // 정렬 완료한 리스트 반환
    }

    @Transactional
    @Override
    public MemoInviteResponseDto inviteUsersToMemo(UserRequestDtos userRequestDtos, Long memoId) {  // 초대할 사용자들 리스트와 memoId를 받아서 특정 메모 1개에 사용자들을 메모에 초대하고, memo와 모든 공동 사용자들 리스트 반환 기능.

        List<Long> inviteUserIds = userRequestDtos.getUserRequestDtos().stream().map(UserRequestDto::getId)
                .collect(Collectors.toList());

        Memo memoEntity = memoJpaRepository.findById(memoId).orElseThrow(
                ()->new NoSuchMemoException(String.format("memoId = %d", memoId)));  // memoId에 해당되는 Memo 객체 찾아오기
        // 여기서 사실 memo는 어차피 RequestDto로 따로 솎아낼 보안되어야할 컬럼이 없으므로 entity->dto->entity를 거치지않고 바로 사용해도 상관없다.

        List<UserAndMemoRequestDto> userAndMemoRequestDtos = new ArrayList<>();

        for (int i = 0; i < inviteUserIds.size(); i++) {
            Long userId = inviteUserIds.get(i);

            User userEntity = userJpaRepository.findById(userId).orElseThrow(
                    ()->new NoSuchUserException(String.format("userId = %d", userId)));  // userId에 해당되는 User 객체 찾아오기
            UserRequestDto userRequestDto = new UserRequestDto(userEntity.getId(), userEntity.getLoginId(), userEntity.getUsername());  // userAndMemoJpaRepository에 save하기전에 먼저, 보안되어야할 컬럼을 솎아내서 한정적으로 가져오기위헤 dto를 한번 거침.
            User userSecondEntity = userRequestDto.toEntity();  // 보안되어야할 컬럼을 솎아낸 dto를 다시 entity 형식으로 변환.

            if (userAndMemoJpaRepository.existsByUserAndMemo(userEntity, memoEntity)) {  // 이미 DB에 존재하는 사용자와 메모 관계일 경우라면,
                throw new UserAndMemoDuplicateException(String.format("userId = %d, memoId = %d", userId, memoId));  // 사용자와 메모 관계 중복 예외처리.
            }

            userAndMemoRequestDtos.add(new UserAndMemoRequestDto(userSecondEntity, memoEntity));
        }

        List<UserAndMemo> userAndMemos = userAndMemoRequestDtos.stream().map(UserAndMemoRequestDto::toEntity)
                        .collect(Collectors.toList());

        userAndMemoJpaRepository.saveAll(userAndMemos);  // UserAndMemo 테이블에 저장. (spring data jpa의 벌크 insert)

        MemoInviteResponseDto memoInviteResponseDto = new MemoInviteResponseDto(memoEntity);
        memoInviteResponseDto.setUserResponseDtos(findUsersByMemoId(memoId));
        memoInviteResponseDto.setMemoHasUsersCount(findUsersByMemoId(memoId));

        return memoInviteResponseDto;
    }

}
