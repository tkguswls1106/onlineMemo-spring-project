package com.shj.onlinememospringproject.store.repository;

import com.shj.onlinememospringproject.domain.dto.User.UserJoinRequestDto;
import com.shj.onlinememospringproject.domain.dto.User.UserResponseDto;
import com.shj.onlinememospringproject.domain.jpo.User;
import com.shj.onlinememospringproject.store.jpastore.UserJpaStore;
import com.shj.onlinememospringproject.store.normalstore.UserStore;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository implements UserStore {

    private final UserJpaStore userJpaStore;
    public UserRepository(UserJpaStore userJpaStore) {
        this.userJpaStore = userJpaStore;
    }


    @Override
    public Long create(UserJoinRequestDto userJoinRequestDto) {  // 사용자 생성하고, 해당 사용자id 반환.
        User entity = userJpaStore.save(userJoinRequestDto.toEntity());
        return entity.getId();

        // 중복 사용자 검사는 서비스 레이어에서 진행한다.
        // 이런 세밀한 과정은 서비스 레이어에서 진행하고, 그저 이와같은 스토어 레이어에서는 DB에 접근만하는 메소드만 작성한다.
    }

    @Override
    public UserResponseDto retrieveById(Long userId) {  // 사용자id로 해당 사용자 1명 조회하여, 사용자 객체 반환.
        Optional<User> entity = userJpaStore.findById(userId);

        entity.orElseThrow();  // 해당 userId를 가진 사용자가 존재하지 않는다면, throw.

        UserResponseDto userResponseDto = new UserResponseDto(entity.get());
        return userResponseDto;

//        if(!entity.isPresent()) {  // 해당 userId를 가진 사용자가 존재하지 않는다면
//            throw new Exception("Error!! 해당 사용자id를 가진 사용자는 존재하지 않습니다!!");
//        }
    }

    @Override
    public void updatePw(UserJoinRequestDto userJoinRequestDto, String firstPw) {  // 해당 사용자의 1차비밀번호 수정.
        User entity = userJoinRequestDto.toEntity();
        entity.updateFirstPw(firstPw);
        userJpaStore.save(entity);

        // 사용자id를 받아 해당하는 사용자를 찾아 수정하는 과정은 서비스 레이어에서 진행한다.
        // 이런 세밀한 과정은 서비스 레이어에서 진행하고, 그저 이와같은 스토어 레이어에서는 DB에 접근만하는 메소드만 작성한다.
    }

    @Override
    public void updateName(UserJoinRequestDto userJoinRequestDto, String username) {  // 해당 사용자의 이름 수정.
        User entity = userJoinRequestDto.toEntity();
        entity.updateUsername(username);
        userJpaStore.save(entity);

        // 사용자id를 받아 해당하는 사용자를 찾아 수정하는 과정은 서비스 레이어에서 진행한다.
        // 이런 세밀한 과정은 서비스 레이어에서 진행하고, 그저 이와같은 스토어 레이어에서는 DB에 접근만하는 메소드만 작성한다.
    }

    @Override
    public void delete(Long userId) {  // 해당 사용자id를 가진 사용자 1명을 삭제.
        userJpaStore.deleteById(userId);
    }
}
