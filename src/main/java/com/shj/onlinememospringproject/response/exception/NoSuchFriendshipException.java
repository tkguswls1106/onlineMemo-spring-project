package com.shj.onlinememospringproject.response.exception;

import com.shj.onlinememospringproject.response.ResponseCode;
import lombok.Getter;

@Getter
public class NoSuchFriendshipException extends RuntimeException {

    private ResponseCode responseCode;

    public NoSuchFriendshipException() {
        this.responseCode = ResponseCode.NOT_FOUND_FRIENDSHIP;
    }
}
