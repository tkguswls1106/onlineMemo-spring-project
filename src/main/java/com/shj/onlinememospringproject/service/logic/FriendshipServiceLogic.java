package com.shj.onlinememospringproject.service.logic;

import com.shj.onlinememospringproject.domain.friendship.Friendship;
import com.shj.onlinememospringproject.domain.friendship.FriendshipJpaRepository;
import com.shj.onlinememospringproject.domain.user.User;
import com.shj.onlinememospringproject.domain.user.UserJpaRepository;
import com.shj.onlinememospringproject.dto.friendship.FriendshipRequestDto;
import com.shj.onlinememospringproject.dto.friendship.FriendshipResponseDto;
import com.shj.onlinememospringproject.dto.friendship.FriendshipSendRequestDto;
import com.shj.onlinememospringproject.dto.friendship.FriendshipUpdateRequestDto;
import com.shj.onlinememospringproject.dto.user.UserRequestDto;
import com.shj.onlinememospringproject.dto.user.UserResponseDto;
import com.shj.onlinememospringproject.response.exception.FriendshipBadRequestException;
import com.shj.onlinememospringproject.response.exception.FriendshipDuplicateException;
import com.shj.onlinememospringproject.response.exception.NoSuchFriendshipException;
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
    public void sendFriendship(Long senderUserId, FriendshipSendRequestDto friendshipSendRequestDto) {  // 친구요청 보내기 기능.

        User senderUserEntity = userJpaRepository.findById(senderUserId).orElseThrow(
                ()->new NoSuchUserException());  // senderUserId에 해당되는 사용자가 존재하는지 여부 확인. 굳이 리턴받아 값을 할당한 이유는 요청 반대의 경우도 확인해야하기에 적었다.
        // 이는 save로 저장하지 않고 확인만 할것이기에, 따로 보안되어야할 컬럼을 솎아내는 작업을 하지 않아도 된다.

        User userEntity = userJpaRepository.findByLoginId(friendshipSendRequestDto.getLoginId()).orElseThrow(
                ()->new NoSuchUserException());  // loginId에 해당되는 User 객체 찾아오기 및 사용자가 존재하는지 여부 확인.
        UserRequestDto userRequestDto = new UserRequestDto(userEntity.getId(), userEntity.getLoginId(), userEntity.getUsername());  // friendshipJpaRepository에 save하기전에 먼저, 보안되어야할 컬럼을 솎아내서 한정적으로 가져오기위헤 dto를 한번 거침.
        User userSecondEntity = userRequestDto.toEntity();  // 보안되어야할 컬럼을 솎아낸 dto를 다시 entity 형식으로 변환.

        if (friendshipJpaRepository.existsByUserAndSenderUserId(userEntity, senderUserId)) {  // 이미 DB에 존재하는 친구요청일 경우라면,
            throw new FriendshipDuplicateException();  // 친구요청 관계 중복 예외처리.
        }
        else if (friendshipJpaRepository.existsByUserAndSenderUserId(senderUserEntity, userEntity.getId())) {  // 또는 반대로 이미 친구요청을 받은상태인데 친구요청한 경우라면,
            throw new FriendshipDuplicateException();  // 친구요청 관계 중복 예외처리.
        }

        FriendshipRequestDto friendshipRequestDto = new FriendshipRequestDto(userSecondEntity, senderUserId);
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

    @Transactional
    @Override
    public void updateFriendship(Long userId, FriendshipUpdateRequestDto friendshipUpdateRequestDto) {  // 친구요청 수신자는 userId, 발신자는 senderUserId에 해당되는 요청 친구에 대한 친구 맺기 여부 수정 기능.

        User userEntity = userJpaRepository.findById(userId).orElseThrow(
                ()->new NoSuchUserException());  // 우선 userId와 일치하는 사용자가 존재하는지부터 확인.
        User senderUserEntity = userJpaRepository.findById(friendshipUpdateRequestDto.getSenderUserId()).orElseThrow(
                ()->new NoSuchUserException());  // 우선 senderUserId와 일치하는 사용자가 존재하는지부터 확인.

        Friendship friendshipEntity = friendshipJpaRepository.findByUserAndSenderUserId(userEntity, friendshipUpdateRequestDto.getSenderUserId()).orElseThrow(
                ()->new NoSuchFriendshipException());  // 이에 해당되는 사용자와 senderUserId를 가진 튜플 데이터가 존재하는지 확인.

        if (friendshipUpdateRequestDto.getIsFriend() == 1 && friendshipUpdateRequestDto.getIsWait() == 0) {  // 친구요청 수락일 경우
            friendshipJpaRepository.updateFriendship(friendshipEntity.getId(), friendshipUpdateRequestDto.getIsFriend(), friendshipUpdateRequestDto.getIsWait());  // 과정 1. 친구관계 상태를 친구로 업데이트하고 그 후
            // 쿼리메소드 사용하여 JPA 영속성 주기없이 바로 DB에 반영하고 flush & clear.

            UserRequestDto userRequestDto = new UserRequestDto(senderUserEntity.getId(), senderUserEntity.getLoginId(), senderUserEntity.getUsername());  // friendshipJpaRepository에 save하기전에 먼저, 보안되어야할 컬럼을 솎아내서 한정적으로 가져오기위헤 dto를 한번 거침.
            User senderUserSecondEntity = userRequestDto.toEntity();  // 보안되어야할 컬럼을 솎아낸 dto를 다시 entity 형식으로 변환.
            FriendshipRequestDto friendshipReverseRequestDto = new FriendshipRequestDto(senderUserSecondEntity, userEntity.getId());
            friendshipJpaRepository.save(friendshipReverseRequestDto.toEntity());  // 과정 2. 반대의 경우도 친구관계 상태를 친구요청 상태로 새로 신규 생성해주고 그 후

            Friendship friendshipReverseEntity = friendshipJpaRepository.findByUserAndSenderUserId(senderUserSecondEntity, userEntity.getId()).orElseThrow(
                    ()->new NoSuchFriendshipException());  // 이에 해당되는 사용자와 senderUserId를 가진 튜플 데이터가 존재하는지, 방금 저장했지만 다시 한번 확인.
            friendshipJpaRepository.updateFriendship(friendshipReverseEntity.getId(), friendshipUpdateRequestDto.getIsFriend(), friendshipUpdateRequestDto.getIsWait());  // 과정 3. 반대의 경우도 친구관계 상태를 친구로 바로 업데이트 해준다.
            // 쿼리메소드 사용하여 JPA 영속성 주기없이 바로 DB에 반영하고 flush & clear.
        }
        else if (friendshipUpdateRequestDto.getIsFriend() == 0 && friendshipUpdateRequestDto.getIsWait() == 0) {  // 친구요청 거절일 경우
            friendshipJpaRepository.deleteById(friendshipEntity.getId());  // 친구요청 관계 자체를 삭제시킴. 이는 요청만 온 상태에서 삭제이므로, 반대의 경우는 삭제하지 않아도 된다.
        }
        else if (friendshipUpdateRequestDto.getIsFriend() == 1 && friendshipUpdateRequestDto.getIsWait() == 1) {  // 잘못된 친구관계 수정 요청일 경우
            throw new FriendshipBadRequestException();  // 잘못된 친구관계 요청 에러 예외처리.
        }
    }

    @Transactional
    @Override
    public void deleteFriendship(Long userId, Long senderUserId) {  // 해당 userId 사용자의 친구들중에서 해당 senderUserId의 사용자를 친구 목록에서 삭제 기능.

        User userEntity = userJpaRepository.findById(userId).orElseThrow(
                ()->new NoSuchUserException());  // 우선 userId와 일치하는 사용자가 존재하는지부터 확인.
        User senderUserEntity = userJpaRepository.findById(senderUserId).orElseThrow(
                ()->new NoSuchUserException());  // 우선 senderUserId와 일치하는 사용자가 존재하는지부터 확인.

        Friendship friendshipEntity = friendshipJpaRepository.findByUserAndSenderUserId(userEntity, senderUserId).orElseThrow(
                ()->new NoSuchFriendshipException());  // 이에 해당되는 사용자와 senderUserId를 가진 튜플 데이터가 존재하는지 확인.
        Friendship friendshipReverseEntity = friendshipJpaRepository.findByUserAndSenderUserId(senderUserEntity, userId).orElseThrow(
                ()->new NoSuchFriendshipException());  // 위와 반대의 경우도 친구관계 튜플 데이터가 존재하는지 확인.

        friendshipJpaRepository.deleteById(friendshipEntity.getId());
        friendshipJpaRepository.deleteById(friendshipReverseEntity.getId());
        // 친구 관계 자체를 삭제시킴. 뿐만아니라 이는 요청만 온 상태가 아닌 친구가 된 이후의 삭제이므로, 반대의 경우도 마찬가지로 삭제해주어야 한다.
    }

}
