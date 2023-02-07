package com.shj.onlinememospringproject.response;

import com.shj.onlinememospringproject.response.item.MessageItem;
import com.shj.onlinememospringproject.response.item.StatusItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode {

    READ_USER(StatusItem.OK, MessageItem.READ_USER),
    CREATED_USER(StatusItem.CREATED, MessageItem.CREATED_USER),

    DUPLICATE_USER(StatusItem.BAD_REQUEST, MessageItem.DUPLICATE_USER),
    NOT_FOUND_USER(StatusItem.NOT_FOUND, MessageItem.NOT_FOUND_USER),

    //

    READ_MEMO(StatusItem.OK, MessageItem.READ_MEMO),
    CREATED_MEMO(StatusItem.CREATED, MessageItem.CREATED_MEMO),

    NOT_FOUND_MEMO(StatusItem.NOT_FOUND, MessageItem.NOT_FOUND_MEMO),

    //

    DUPLICATE_USERANDMEMO(StatusItem.BAD_REQUEST, MessageItem.DUPLICATE_USERANDMEMO),

    //

    INTERNAL_SERVER_ERROR(StatusItem.INTERNAL_SERVER_ERROR, MessageItem.INTERNAL_SERVER_ERROR),
    ;

    private int httpStatus;
    private String message;
}
