package com.shj.onlinememospringproject.response.exception;

import com.shj.onlinememospringproject.response.responseitem.MessageItem;
import com.shj.onlinememospringproject.response.responseitem.StatusItem;
import lombok.Getter;

@Getter
public class LoginIdDuplicateException extends RuntimeException {

    private Integer errorStatus;
    private String errorMessage;

    private String loginId;

    public LoginIdDuplicateException(String loginId) {
        this.errorStatus = StatusItem.BAD_REQUEST;
        this.errorMessage = MessageItem.DUPLICATE_USER;

        this.loginId = loginId;
    }
}
