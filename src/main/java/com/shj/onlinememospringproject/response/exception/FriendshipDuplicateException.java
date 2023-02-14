package com.shj.onlinememospringproject.response.exception;

import com.shj.onlinememospringproject.response.ResponseCode;
import lombok.Getter;

@Getter
public class FriendshipDuplicateException extends RuntimeException  {

    private ResponseCode responseCode;

    public FriendshipDuplicateException() {
        this.responseCode = ResponseCode.DUPLICATE_FRIENDSHIP;
    }
}
