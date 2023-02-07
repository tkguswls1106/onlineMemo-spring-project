package com.shj.onlinememospringproject.response.exception;

import com.shj.onlinememospringproject.response.ResponseCode;
import lombok.Getter;

@Getter
public class NoSuchMemoException extends RuntimeException  {

    private ResponseCode responseCode;

    public NoSuchMemoException() {
        this.responseCode = ResponseCode.NOT_FOUND_MEMO;
    }
}
