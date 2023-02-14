package com.shj.onlinememospringproject.service.logic;

import com.shj.onlinememospringproject.domain.friendship.Friendship;
import com.shj.onlinememospringproject.domain.friendship.FriendshipJpaRepository;
import com.shj.onlinememospringproject.domain.user.User;
import com.shj.onlinememospringproject.domain.user.UserJpaRepository;
import com.shj.onlinememospringproject.dto.friendship.FriendshipRequestDto;
import com.shj.onlinememospringproject.dto.friendship.FriendshipResponseDto;
import com.shj.onlinememospringproject.dto.friendship.FriendshipSendRequestDto;
import com.shj.onlinememospringproject.dto.user.UserRequestDto;
import com.shj.onlinememospringproject.dto.user.UserResponseDto;
import com.shj.onlinememospringproject.response.exception.FriendshipDuplicateException;
import com.shj.onlinememospringproject.response.exception.NoSuchUserException;
import com.shj.onlinememospringproject.service.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
public class FriendshipServiceLogic implements FriendshipService {

    private final FriendshipJpaRepository friendshipJpaRepository;
    private final UserJpaRepository userJpaRepository;


    @Transactional
    @Override
    public void sendFriendship(FriendshipSendRequestDto friendshipSendRequestDto) {  // 친구요청 보내기 기능.

        User senderUserEntity = userJpaRepository.findById(friendshipSendRequestDto.getSenderUserId()).orElseThrow(
                ()->new NoSuchUserException());  // senderUserId에 해당되는 사용자가 존재하는지 여부 확인. 굳이 리턴받아 값을 할당한 이유는 요청 반대의 경우도 확인해야하기에 적었다.
        // 이는 save로 저장하지 않고 확인만 할것이기에, 따로 보안되어야할 컬럼을 솎아내는 작업을 하지 않아도 된다.

        User userEntity = userJpaRepository.findByLoginId(friendshipSendRequestDto.getLoginId()).orElseThrow(
                ()->new NoSuchUserException());  // loginId에 해당되는 User 객체 찾아오기 및 사용자가 존재하는지 여부 확인.
        UserRequestDto userRequestDto = new UserRequestDto(userEntity.getId(), userEntity.getLoginId(), userEntity.getUsername());  // userAndMemoJpaRepository에 save하기전에 먼저, 보안되어야할 컬럼을 솎아내서 한정적으로 가져오기위헤 dto를 한번 거침.
        User userSecondEntity = userRequestDto.toEntity();  // 보안되어야할 컬럼을 솎아낸 dto를 다시 entity 형식으로 변환.

        if (friendshipJpaRepository.existsByUserAndSenderUserId(userEntity, friendshipSendRequestDto.getSenderUserId())) {  // 이미 DB에 존재하는 친구요청일 경우라면,
            throw new FriendshipDuplicateException();  // 친구요청 관계 중복 예외처리.
        }
        else if (friendshipJpaRepository.existsByUserAndSenderUserId(senderUserEntity, userEntity.getId())) {  // 또는 반대로 이미 친구요청을 받은상태인데 친구요청한 경우라면,
            throw new FriendshipDuplicateException();  // 친구요청 관계 중복 예외처리.
        }

        FriendshipRequestDto friendshipRequestDto = new FriendshipRequestDto(userSecondEntity, friendshipSendRequestDto.getSenderUserId());
        friendshipJpaRepository.save(friendshipRequestDto.toEntity());
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserResponseDto> findSendersByUserId(Long userId) {  // 해당 userId 사용자의 친구요청 응답대기중인 요청발신자 사용자들 리스트 반환 기능.

        User userEntity = userJpaRepository.findById(userId).orElseThrow(
                ()->new NoSuchUserException());  // 우선 userId와 일치하는 사용자가 존재하는지부터 확인.

        List<Friendship> friendships = friendshipJpaRepository.findAllByUser(userEntity);
        List<FriendshipResponseDto> friendshipResponseDtos = friendships.stream().map(FriendshipResponseDto::new)
                .collect(Collectors.toList());

//        List<UserResponseDto> userResponseDtos = new ArrayList<>();
//        for (int i = 0; i < friendshipResponseDtos.size(); i++) {
//            Long senderUserId = friendshipResponseDtos.get(i).getSenderUserId();
//            userResponseDtos.add(userServiceLogic.findById(senderUserId));
//        }
        List<User> users = new ArrayList<>();
        for (int i = 0; i < friendshipResponseDtos.size(); i++) {
            if (friendshipResponseDtos.get(i).getIsFriend() == 0 && friendshipResponseDtos.get(i).getIsWait() == 1) {  // 친구요청 대기상태일 경우에만
                Long senderUserId = friendshipResponseDtos.get(i).getSenderUserId();
                users.add(
                        userJpaRepository.findById(senderUserId).orElseThrow(
                                () -> new NoSuchUserException())
                );
            }
        }

        return users.stream().map(UserResponseDto::new)
                .sorted(Comparator.comparing(UserResponseDto::getId, Comparator.reverseOrder()))  // 사용자 id 내림차순 정렬 후
                .sorted(Comparator.comparing(UserResponseDto::getUsername))  // 사용자 이름 오름차순 정렬
                .collect(Collectors.toList());  // 정렬 완료한 리스트 반환
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserResponseDto> findFriendsByUserId(Long userId) {  // 해당 userId 사용자의 친구들 리스트 반환 기능.

        User userEntity = userJpaRepository.findById(userId).orElseThrow(
                ()->new NoSuchUserException());  // 우선 userId와 일치하는 사용자가 존재하는지부터 확인.

        List<Friendship> friendships = friendshipJpaRepository.findAllByUser(userEntity);
        List<FriendshipResponseDto> friendshipResponseDtos = friendships.stream().map(FriendshipResponseDto::new)
                .collect(Collectors.toList());

//        List<UserResponseDto> userResponseDtos = new ArrayList<>();
//        for (int i = 0; i < friendshipResponseDtos.size(); i++) {
//            Long senderUserId = friendshipResponseDtos.get(i).getSenderUserId();
//            userResponseDtos.add(userServiceLogic.findById(senderUserId));
//        }
        List<User> users = new ArrayList<>();
        for (int i = 0; i < friendshipResponseDtos.size(); i++) {
            if (friendshipResponseDtos.get(i).getIsFriend() == 1 && friendshipResponseDtos.get(i).getIsWait() == 0) {  // 친구일 경우에만
                Long senderUserId = friendshipResponseDtos.get(i).getSenderUserId();
                users.add(
                        userJpaRepository.findById(senderUserId).orElseThrow(
                                () -> new NoSuchUserException())
                );
            }
        }

        return users.stream().map(UserResponseDto::new)
                .sorted(Comparator.comparing(UserResponseDto::getId, Comparator.reverseOrder()))  // 사용자 id 내림차순 정렬 후
                .sorted(Comparator.comparing(UserResponseDto::getUsername))  // 사용자 이름 오름차순 정렬
                .collect(Collectors.toList());  // 정렬 완료한 리스트 반환
    }

}
