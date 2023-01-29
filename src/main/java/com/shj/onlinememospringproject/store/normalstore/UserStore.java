package com.shj.onlinememospringproject.store.normalstore;

import com.shj.onlinememospringproject.domain.dto.User.UserRequestDto;
import com.shj.onlinememospringproject.domain.dto.User.UserResponseDto;

public interface UserStore {

    // 데이터를 가져와서 DB에 넣어야하므로, toEntity를 해줘야해서 파라미터는 UserRequestDto가 와야한다. "DB에 접근하도록 '요청' -> 'request'"
    Long create(UserRequestDto userRequestDto);  // 사용자 생성하고, 해당 사용자id 반환.

    // DB에서 응답을 받아 조회하여 가져와서 계층간이동에 사용되어야하므로, 조회한 entity를 dto로 변환하는 메소드가 담긴 UserResponseDto가 와야한다. "'응답'을 받아와서 사용 -> 'response'"
    UserResponseDto retrieveById(Long userId);  // 사용자id로 해당 사용자 1명 조회하여, 사용자 객체 반환.

    void updatePw(UserRequestDto userRequestDto, String firstPw);  // 해당 사용자의 1차비밀번호 수정.

    void updateName(UserRequestDto userRequestDto, String username);  // 해당 사용자의 이름 수정.

    void delete(Long userId);  // 해당 사용자id를 가진 사용자 1명을 삭제.

}
