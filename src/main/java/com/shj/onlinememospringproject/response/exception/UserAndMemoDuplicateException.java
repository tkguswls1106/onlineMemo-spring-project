package com.shj.onlinememospringproject.response.exception;

import com.shj.onlinememospringproject.response.ResponseCode;
import lombok.Getter;

@Getter
public class UserAndMemoDuplicateException extends RuntimeException {

    private ResponseCode responseCode;

    public UserAndMemoDuplicateException() {
        this.responseCode = ResponseCode.DUPLICATE_USERANDMEMO;
    }
}
