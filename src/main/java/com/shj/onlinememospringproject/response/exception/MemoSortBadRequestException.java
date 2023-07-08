package com.shj.onlinememospringproject.response.exception;

import com.shj.onlinememospringproject.response.responseitem.MessageItem;
import com.shj.onlinememospringproject.response.responseitem.StatusItem;
import lombok.Getter;

@Getter
public class MemoSortBadRequestException extends RuntimeException {

    private Integer errorStatus;
    private String errorMessage;

    private String order;

    public MemoSortBadRequestException(String order) {
        this.errorStatus = StatusItem.BAD_REQUEST;
        this.errorMessage = MessageItem.BAD_REQUEST_MEMOSORT;

        this.order = order;
    }
}
