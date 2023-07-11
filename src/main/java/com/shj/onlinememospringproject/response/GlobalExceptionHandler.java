package com.shj.onlinememospringproject.response;

import com.shj.onlinememospringproject.response.exception.*;
import com.shj.onlinememospringproject.response.responseitem.MessageItem;
import com.shj.onlinememospringproject.response.responseitem.StatusItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception ex) {
        log.error(StatusItem.INTERNAL_SERVER_ERROR + " " + MessageItem.INTERNAL_SERVER_ERROR + "\n" + "==> error_messege / " + ex.getMessage() + "\n" + "==> error_cause / " + ex.getCause());
        return ResponseData.toResponseEntity(ResponseCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity handleUnauthorizedException(Exception ex) {
        log.error(StatusItem.UNAUTHORIZED + " " + MessageItem.UNAUTHORIZED + "\n" + "==> error_messege / " + ex.getMessage() + "\n" + "==> error_cause / " + ex.getCause());
        return ResponseData.toResponseEntity(ResponseCode.UNAUTHORIZED_ERROR);
        // 사실 이건 의미가 없는게, 예외처리권한이 JwtAuthenticationEntryPoint 에게 넘어가기에 크롬콘솔에선 설정한방식대로 출력되지않는다.
        // 하지만 이는 postman 프로그램 에서 출력받아 확인할 수 있다.
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity handleForbiddenException(Exception ex) {
        log.error(StatusItem.FORBIDDEN + " " + MessageItem.FORBIDDEN  + "\n" + "==> error_messege / " + ex.getMessage() + "\n" + "==> error_cause / " + ex.getCause());
        return ResponseData.toResponseEntity(ResponseCode.FORBIDDEN_ERROR);
        // 사실 이건 의미가 없는게, 예외처리권한이 JwtAccessDeniedHandler 에게 넘어가기에 크롬콘솔에선 설정한방식대로 출력되지않는다.
        // 하지만 이는 postman 프로그램 에서 출력받아 확인할 수 있다.
    }

    @ExceptionHandler(LoginIdDuplicateException.class)
    public ResponseEntity handleLoginIdDuplicateException(LoginIdDuplicateException ex) {
        log.error(ex.getErrorStatus() + " " + ex.getErrorMessage() + "\n" + "==> error_data by duplicate / " + "loginId = " + ex.getLoginId());
        return ResponseData.toResponseEntity(ResponseCode.DUPLICATE_USER);
    }

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity handleNoSuchUserException(NoSuchUserException ex) {
        log.error(ex.getErrorStatus() + " " + ex.getErrorMessage() + "\n" + "==> error_data / " + ex.getMessage());
        return ResponseData.toResponseEntity(ResponseCode.NOT_FOUND_USER);
    }

    @ExceptionHandler(NoSuchMemoException.class)
    public ResponseEntity handleNoSuchMemoException(NoSuchMemoException ex) {
        log.error(ex.getErrorStatus() + " " + ex.getErrorMessage() + "\n" + "==> error_data / " + ex.getMessage());
        return ResponseData.toResponseEntity(ResponseCode.NOT_FOUND_MEMO);
    }

    @ExceptionHandler(MemoSortBadRequestException.class)
    public ResponseEntity handleMemoSortBadRequestException(MemoSortBadRequestException ex) {
        log.error(ex.getErrorStatus() + " " + ex.getErrorMessage() + "\n" + "==> error_data / " + "order_string = " + ex.getOrder());
        return ResponseData.toResponseEntity(ResponseCode.BAD_REQUEST_MEMOSORT);
    }

    @ExceptionHandler(UserAndMemoDuplicateException.class)
    public ResponseEntity handleUserAndMemoDuplicateException(UserAndMemoDuplicateException ex) {
        log.error(ex.getErrorStatus() + " " + ex.getErrorMessage() + "\n" + "==> error_data by duplicate / " + ex.getMessage());
        return ResponseData.toResponseEntity(ResponseCode.DUPLICATE_USERANDMEMO);
    }

    @ExceptionHandler(FriendshipDuplicateException.class)
    public ResponseEntity handleFriendshipDuplicateException(FriendshipDuplicateException ex) {
        log.error(ex.getErrorStatus() + " " + ex.getErrorMessage() + "\n" + "==> error_data by duplicate / " + ex.getMessage());
        return ResponseData.toResponseEntity(ResponseCode.DUPLICATE_FRIENDSHIP);
    }

    @ExceptionHandler(FriendshipBadRequestException.class)
    public ResponseEntity handleFriendshipBadRequestException(FriendshipBadRequestException ex) {
        log.error(ex.getErrorStatus() + " " + ex.getErrorMessage() + "\n" + "==> error_reason / " + ex.getMessage());
        return ResponseData.toResponseEntity(ResponseCode.BAD_REQUEST_FRIENDSHIP);
    }

    @ExceptionHandler(NoSuchFriendshipException.class)
    public ResponseEntity handleNoSuchFriendshipException(NoSuchFriendshipException ex) {
        log.error(ex.getErrorStatus() + " " + ex.getErrorMessage() + "\n" + "==> error_data / " + ex.getMessage());
        return ResponseData.toResponseEntity(ResponseCode.NOT_FOUND_FRIENDSHIP);
    }

}
