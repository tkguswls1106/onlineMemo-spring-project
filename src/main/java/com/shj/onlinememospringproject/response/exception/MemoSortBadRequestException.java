package com.shj.onlinememospringproject.response.exception;

import com.shj.onlinememospringproject.response.ResponseCode;
import lombok.Getter;

@Getter
public class MemoSortBadRequestException extends RuntimeException {

    private ResponseCode responseCode;

    public MemoSortBadRequestException() {
        this.responseCode = ResponseCode.BAD_REQUEST_MEMOSORT;
    }
}
