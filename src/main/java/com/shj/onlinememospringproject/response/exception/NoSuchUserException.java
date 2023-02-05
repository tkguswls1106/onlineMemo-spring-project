package com.shj.onlinememospringproject.response.exception;

import com.shj.onlinememospringproject.response.ResponseCode;
import lombok.Getter;

@Getter
public class NoSuchUserException extends RuntimeException  {

    private ResponseCode responseCode;

    public NoSuchUserException() {
        this.responseCode = ResponseCode.NOT_FOUND_MEMO;
    }
}
