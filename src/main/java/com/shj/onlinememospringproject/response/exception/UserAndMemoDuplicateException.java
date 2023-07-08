package com.shj.onlinememospringproject.response.exception;

import com.shj.onlinememospringproject.response.responseitem.MessageItem;
import com.shj.onlinememospringproject.response.responseitem.StatusItem;
import lombok.Getter;

@Getter
public class UserAndMemoDuplicateException extends RuntimeException {

    private Integer errorStatus;
    private String errorMessage;

    private String message;

    public UserAndMemoDuplicateException(String message) {
        this.errorStatus = StatusItem.BAD_REQUEST;
        this.errorMessage = MessageItem.DUPLICATE_USERANDMEMO;

        this.message = message;
    }
}
