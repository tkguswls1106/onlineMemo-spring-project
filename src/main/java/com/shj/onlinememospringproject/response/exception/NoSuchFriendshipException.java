package com.shj.onlinememospringproject.response.exception;

import com.shj.onlinememospringproject.response.responseitem.MessageItem;
import com.shj.onlinememospringproject.response.responseitem.StatusItem;
import lombok.Getter;

@Getter
public class NoSuchFriendshipException extends RuntimeException {

    private Integer errorStatus;
    private String errorMessage;

    private String message;

    public NoSuchFriendshipException(String message) {
        this.errorStatus = StatusItem.NOT_FOUND;
        this.errorMessage = MessageItem.NOT_FOUND_FRIENDSHIP;

        this.message = message;
    }
}
