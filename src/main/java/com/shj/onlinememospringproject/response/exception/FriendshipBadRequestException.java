package com.shj.onlinememospringproject.response.exception;

import com.shj.onlinememospringproject.response.ResponseCode;
import lombok.Getter;

@Getter
public class FriendshipBadRequestException extends RuntimeException {

    private ResponseCode responseCode;

    public FriendshipBadRequestException() {
        this.responseCode = ResponseCode.BAD_REQUEST_FRIENDSHIP;
    }
}
