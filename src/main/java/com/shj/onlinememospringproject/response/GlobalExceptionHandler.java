package com.shj.onlinememospringproject.response;

import com.shj.onlinememospringproject.response.ResponseCode;
import com.shj.onlinememospringproject.response.ResponseData;
import com.shj.onlinememospringproject.response.exception.LoginIdDuplicateException;
import com.shj.onlinememospringproject.response.exception.NoSuchMemoException;
import com.shj.onlinememospringproject.response.exception.NoSuchUserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception ex) {
        log.error("handleException", ex);
        return ResponseData.toResponseEntity(ResponseCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(LoginIdDuplicateException.class)
    public ResponseEntity handleLoginIdDuplicateException(LoginIdDuplicateException ex) {
        log.error("handleLoginIdDuplicateException", ex);
        return ResponseData.toResponseEntity(ResponseCode.DUPLICATE_USER);
    }

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity handleNoSuchUserException(NoSuchUserException ex) {
        log.error("handleNoSuchUserException", ex);
        return ResponseData.toResponseEntity(ResponseCode.NOT_FOUND_USER);
    }

    @ExceptionHandler(NoSuchMemoException.class)
    public ResponseEntity handleNoSuchMemoException(NoSuchMemoException ex) {
        log.error("handleNoSuchMemoException", ex);
        return ResponseData.toResponseEntity(ResponseCode.NOT_FOUND_MEMO);
    }

}
