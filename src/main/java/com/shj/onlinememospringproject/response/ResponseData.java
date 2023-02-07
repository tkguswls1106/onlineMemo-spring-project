package com.shj.onlinememospringproject.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

@Getter
@Builder
@ToString
public class ResponseData<T> {

    private final int status;
    private final ResponseCode code;
    private final String message;
    private T data;

    // response data가 없을때
    public static ResponseEntity<ResponseData> toResponseEntity(ResponseCode responseCode) {
        return ResponseEntity
                .status(responseCode.getHttpStatus())
                .body(ResponseData.builder()
                        .status(responseCode.getHttpStatus())
                        .code(responseCode)
                        .message(responseCode.getMessage())
                        .build()
                );
    }

    // response data가 있을때
    public static <T> ResponseEntity<ResponseData<T>> toResponseEntity(ResponseCode responseCode, T data) {
        return ResponseEntity
                .status(responseCode.getHttpStatus())
                .body(ResponseData.<T>builder()
                        .status(responseCode.getHttpStatus())
                        .code(responseCode)
                        .message(responseCode.getMessage())
                        .data(data)
                        .build()
                );
    }

    // response data와 header가 있을때
    public static <T> ResponseEntity<ResponseData<T>> toResponseEntity(ResponseCode responseCode, MultiValueMap<String, String> header, T data) {
        return ResponseEntity
                .status(responseCode.getHttpStatus())
                .header(String.valueOf(header))
                .body(ResponseData.<T>builder()
                        .status(responseCode.getHttpStatus())
                        .code(responseCode)
                        .message(responseCode.getMessage())
                        .data(data)
                        .build()
                );
    }
}
