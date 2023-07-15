package com.shj.onlinememospringproject.response;

import com.shj.onlinememospringproject.response.responseitem.MessageItem;
import com.shj.onlinememospringproject.response.responseitem.StatusItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode {

    // ===================== //

    // User 관련 성공 응답
    CREATED_USER(StatusItem.CREATED, MessageItem.CREATED_USER),
    READ_USER(StatusItem.OK, MessageItem.READ_USER),
    UPDATE_USER(StatusItem.NO_CONTENT, MessageItem.UPDATE_USER),
    DELETE_USER(StatusItem.NO_CONTENT, MessageItem.DELETE_USER),

    // User 관련 실패 응답
    NOT_FOUND_USER(StatusItem.NOT_FOUND, MessageItem.NOT_FOUND_USER),
    DUPLICATE_USER(StatusItem.BAD_REQUEST, MessageItem.DUPLICATE_USER),

    // ===================== //

    // Memo 관련 성공 응답
    CREATED_MEMO(StatusItem.CREATED, MessageItem.CREATED_MEMO),
    READ_MEMO(StatusItem.OK, MessageItem.READ_MEMO),
    READ_MEMOLIST(StatusItem.OK, MessageItem.READ_MEMOLIST),
    UPDATE_MEMO(StatusItem.NO_CONTENT, MessageItem.UPDATE_MEMO),
    DELETE_MEMO(StatusItem.NO_CONTENT, MessageItem.DELETE_MEMO),

    // Memo 관련 실패 응답
    NOT_FOUND_MEMO(StatusItem.NOT_FOUND, MessageItem.NOT_FOUND_MEMO),
    BAD_REQUEST_MEMOSORT(StatusItem.BAD_REQUEST, MessageItem.BAD_REQUEST_MEMOSORT),

    // ===================== //

    // UserAndMemo 관련 성공 응답

    // UserAndMemo 관련 실패 응답
    DUPLICATE_USERANDMEMO(StatusItem.BAD_REQUEST, MessageItem.DUPLICATE_USERANDMEMO),

    // ===================== //

    // Friendship 관련 성공 응답
    CREATED_SENDFRIENDSHIP(StatusItem.CREATED, MessageItem.CREATED_SENDFRIENDSHIP),
    READ_SENDERLIST(StatusItem.OK, MessageItem.READ_SENDERLIST),
    READ_FRIENDLIST(StatusItem.OK, MessageItem.READ_FRIENDLIST),
    UPDATE_FRIENDSHIP(StatusItem.NO_CONTENT, MessageItem.UPDATE_FRIENDSHIP),
    DELETE_FRIENDSHIP(StatusItem.NO_CONTENT, MessageItem.DELETE_FRIENDSHIP),

    // Friendship 관련 실패 응답
    NOT_FOUND_FRIENDSHIP(StatusItem.NOT_FOUND, MessageItem.NOT_FOUND_FRIENDSHIP),
    DUPLICATE_FRIENDSHIP(StatusItem.BAD_REQUEST, MessageItem.DUPLICATE_FRIENDSHIP),
    BAD_REQUEST_FRIENDSHIP(StatusItem.BAD_REQUEST, MessageItem.BAD_REQUEST_FRIENDSHIP),

    // ===================== //

    // 기타 성공 응답
    READ_IS_LOGIN(StatusItem.OK, MessageItem.READ_IS_LOGIN),
    LOGIN_SUCCESS(StatusItem.OK, MessageItem.LOGIN_SUCCESS),
    UPDATE_PASSWORD(StatusItem.NO_CONTENT, MessageItem.UPDATE_PASSWORD),
    HEALTHY_SUCCESS(StatusItem.OK, MessageItem.HEALTHY_SUCCESS),

    // 기타 실패 응답
    INTERNAL_SERVER_ERROR(StatusItem.INTERNAL_SERVER_ERROR, MessageItem.INTERNAL_SERVER_ERROR),
    UNAUTHORIZED_ERROR(StatusItem.UNAUTHORIZED, MessageItem.UNAUTHORIZED),
    FORBIDDEN_ERROR(StatusItem.FORBIDDEN, MessageItem.FORBIDDEN),

    // ===================== //
    ;

    private int httpStatus;
    private String message;
}
