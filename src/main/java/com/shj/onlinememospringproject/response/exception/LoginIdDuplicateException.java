package com.shj.onlinememospringproject.response.exception;

import com.shj.onlinememospringproject.response.ResponseCode;
import lombok.Getter;

@Getter
public class LoginIdDuplicateException extends RuntimeException {

    private ResponseCode responseCode;

    public LoginIdDuplicateException() {
        this.responseCode = ResponseCode.DUPLICATE_USER;
    }
}
