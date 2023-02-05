package com.shj.onlinememospringproject.service.logic;

import com.shj.onlinememospringproject.domain.memo.Memo;
import com.shj.onlinememospringproject.domain.memo.MemoJpaRepository;
import com.shj.onlinememospringproject.domain.user.User;
import com.shj.onlinememospringproject.domain.user.UserJpaRepository;
import com.shj.onlinememospringproject.domain.userandmemo.UserAndMemo;
import com.shj.onlinememospringproject.domain.userandmemo.UserAndMemoJpaRepository;
import com.shj.onlinememospringproject.dto.memo.MemoRequestDto;
import com.shj.onlinememospringproject.dto.memo.MemoResponseDto;
import com.shj.onlinememospringproject.dto.user.UserJoinRequestDto;
import com.shj.onlinememospringproject.dto.user.UserRequestDto;
import com.shj.onlinememospringproject.dto.user.UserResponseDto;
import com.shj.onlinememospringproject.dto.userandmemo.UserAndMemoRequestDto;
import com.shj.onlinememospringproject.dto.userandmemo.UserAndMemoResponseDto;
import com.shj.onlinememospringproject.service.UserAndMemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
public class UserAndMemoServiceLogic implements UserAndMemoService {

    private final UserAndMemoJpaRepository userAndMemoJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final MemoJpaRepository memoJpaRepository;


    @Transactional(readOnly = true)
    @Override
    public List<MemoResponseDto> findMemosByUserId(Long userId) {  // userId와 일치하는 사용자의 메모들 리스트를 정렬후 반환 기능.
        // 클라이언트에게 전달해야하므로, 이미 DB 레이어를 지나쳤기에 다시 entity 형식을 ResponseDto 형식으로 변환하여 빈환해야함.

//        Sort sort = Sort.by(Sort.Direction.DESC, "id");
//        List<UserAndMemo> userAndMemos = userAndMemoJpaRepository.findAll(sort);
        List<UserAndMemo> userAndMemos = userAndMemoJpaRepository.findAll();

        List<Memo> memos = new ArrayList<>();
        for (int i = 0; i < userAndMemos.size(); i++) {  // userAndMemos리스트에서 userId와 일치하는 user객체가 존재한다면 그 memo객체를 memos리스트에 넣어줌.
            if (userAndMemos.get(i).getUser().getId() == userId) {
                memos.add(userAndMemos.get(i).getMemo());
            }
        }

        return memos.stream().map(MemoResponseDto::new)
                .sorted(Comparator.comparing(MemoResponseDto::getId).reversed()  // 메모 id 내림차순 정렬 후
                        .thenComparing(MemoResponseDto::getDateTimeModifiedDate).reversed())  // 메모 수정날짜 내림차순 정렬
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserResponseDto> findUsersByMemoId(Long memoId) {  // memoId와 일치하는 메모의 사용자들 리스트를 정렬후 반환 기능.
        // 클라이언트에게 전달해야하므로, 이미 DB 레이어를 지나쳤기에 다시 entity 형식을 ResponseDto 형식으로 변환하여 빈환해야함.

//        Sort sort = Sort.by(Sort.Direction.DESC, "id");
//        List<UserAndMemo> userAndMemos = userAndMemoJpaRepository.findAll(sort);
        List<UserAndMemo> userAndMemos = userAndMemoJpaRepository.findAll();

        List<User> users = new ArrayList<>();
        for (int i = 0; i < userAndMemos.size(); i++) {  // userAndMemos리스트에서 userId와 일치하는 user객체가 존재한다면 그 memo객체를 memos리스트에 넣어줌.
            if (userAndMemos.get(i).getMemo().getId() == memoId) {
                users.add(userAndMemos.get(i).getUser());
            }
        }

        return users.stream().map(UserResponseDto::new)
                .sorted(Comparator.comparing(UserResponseDto::getId).reversed()  // 사용자 id 내림차순 정렬 후
                        .thenComparing(UserResponseDto::getUsername))  // 사용자 이름 오름차순 정렬
                .collect(Collectors.toList());  // 사용자 이름 오름차순 정렬하여 반환.
    }

    // 추후에, 리액트에서 구현해야할것: 다수의 친구(예로 5명) 초대시, 리액트에서 몇명인지 카운트하여 이 메소드 쿼리를 5번 실행할수있도록 처리하자.
    @Transactional
    @Override
    public List<UserResponseDto> inviteUserToMemo(Long userId, Long memoId) {  // memoId와 userId를 받아 특정 메모 1개에 친구(사용자) 1명을 메모에 초대하고 모든 공동 사용자들 리스트 반환 기능.
        // 초대한 사용자를 포함하여 사용자들의 리스트를 메모 우측에 표기하기위해 List<UserResponseDto> 형식으로 반환받음.

        User userEntity = userJpaRepository.findById(userId).orElseThrow(
                ()->new IllegalArgumentException("해당 userId의 사용자는 존재하지 않습니다!! => userId: " + userId));  // userId에 해당되는 User 객체 찾아오기
        UserRequestDto userRequestDto = new UserRequestDto(userEntity.getId(), userEntity.getLoginId(), userEntity.getUsername());  // userAndMemoJpaRepository에 save하기전에 먼저, 보안되어야할 컬럼을 솎아내서 한정적으로 가져오기위헤 dto를 한번 거침.
        User userSecondEntity = userRequestDto.toEntity();  // 보안되어야할 컬럼을 솎아낸 dto를 다시 entity 형식으로 변환.

        Memo memoEntity = memoJpaRepository.findById(memoId).orElseThrow(
                ()->new IllegalArgumentException("해당 memoId의 메모는 존재하지 않습니다!! => memoId: " + memoId));  // memoId에 해당되는 Memo 객체 찾아오기
        // 여기서 사실 memo는 어차피 RequestDto로 따로 솎아낼 보안되어야할 컬럼이 없으므로 entity->dto->entity를 거치지않고 바로 사용해도 상관없다.

        UserAndMemoRequestDto userAndMemoRequestDto = new UserAndMemoRequestDto(userSecondEntity, memoEntity);
        userAndMemoJpaRepository.save(userAndMemoRequestDto.toEntity());  // UserAndMemo 테이블에도 저장.

        return findUsersByMemoId(memoId);
    }
}
