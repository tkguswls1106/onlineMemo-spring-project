package com.shj.onlinememospringproject.response.exception;

import com.shj.onlinememospringproject.response.responseitem.MessageItem;
import com.shj.onlinememospringproject.response.responseitem.StatusItem;
import lombok.Getter;

@Getter
public class FriendshipDuplicateException extends RuntimeException  {

    private Integer errorStatus;
    private String errorMessage;

    private String message;

    public FriendshipDuplicateException(String message) {
        this.errorStatus = StatusItem.BAD_REQUEST;
        this.errorMessage = MessageItem.DUPLICATE_FRIENDSHIP;

        this.message = message;
    }
}
