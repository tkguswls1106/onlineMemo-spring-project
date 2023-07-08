package com.shj.onlinememospringproject.response.exception;

import com.shj.onlinememospringproject.response.responseitem.MessageItem;
import com.shj.onlinememospringproject.response.responseitem.StatusItem;
import lombok.Getter;

@Getter
public class FriendshipBadRequestException extends RuntimeException {

    private Integer errorStatus;
    private String errorMessage;

    private String message;

    public FriendshipBadRequestException(String message) {
        this.errorStatus = StatusItem.BAD_REQUEST;
        this.errorMessage = MessageItem.BAD_REQUEST_FRIENDSHIP;

        this.message = message;
    }
}
