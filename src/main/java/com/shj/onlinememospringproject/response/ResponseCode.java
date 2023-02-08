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
    DUPLICATE_USER(StatusItem.BAD_REQUEST, MessageItem.DUPLICATE_USER),
    NOT_FOUND_USER(StatusItem.NOT_FOUND, MessageItem.NOT_FOUND_USER),

    // ===================== //

    // Memo 관련 성공 응답
    CREATED_MEMO(StatusItem.CREATED, MessageItem.CREATED_MEMO),
    READ_MEMO(StatusItem.OK, MessageItem.READ_MEMO),
    READ_MEMOLIST(StatusItem.OK, MessageItem.READ_MEMOLIST),
    UPDATE_MEMO(StatusItem.NO_CONTENT, MessageItem.UPDATE_MEMO),
    DELETE_MEMO(StatusItem.NO_CONTENT, MessageItem.DELETE_MEMO),

    // Memo 관련 실패 응답
    NOT_FOUND_MEMO(StatusItem.NOT_FOUND, MessageItem.NOT_FOUND_MEMO),

    // ===================== //

    // UserAndMemo 관련 성공 응답
    DUPLICATE_USERANDMEMO(StatusItem.BAD_REQUEST, MessageItem.DUPLICATE_USERANDMEMO),

    // UserAndMemo 관련 실패 응답

    // ===================== //

    // 기타 실패 응답
    INTERNAL_SERVER_ERROR(StatusItem.INTERNAL_SERVER_ERROR, MessageItem.INTERNAL_SERVER_ERROR),

    // ===================== //
    ;

    private int httpStatus;
    private String message;
}
