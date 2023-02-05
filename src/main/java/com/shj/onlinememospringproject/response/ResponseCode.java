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
    READ_MEMO(StatusItem.OK, MessageItem.READ_MEMO),
    CREATED_MEMO(StatusItem.CREATED, MessageItem.CREATED_MEMO),

    DUPLICATE_USER(StatusItem.BAD_REQUEST, MessageItem.DUPLICATE_USER),
    INTERNAL_SERVER_ERROR(StatusItem.INTERNAL_SERVER_ERROR, MessageItem.INTERNAL_SERVER_ERROR),
    NOT_FOUND_USER(StatusItem.NOT_FOUND, MessageItem.NOT_FOUND_USER),
    NOT_FOUND_MEMO(StatusItem.NOT_FOUND, MessageItem.NOT_FOUND_MEMO),
    ;

    private int httpStatus;
    private String message;
}
