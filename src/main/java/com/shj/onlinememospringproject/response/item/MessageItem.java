package com.shj.onlinememospringproject.response.item;

public class MessageItem {
    public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String LOGIN_FAIL = "로그인 실패";

    public static final String READ_USER = "회원 정보 조회 성공";
    public static final String NOT_FOUND_USER = "회원을 찾을 수 없습니다.";
    public static final String CREATED_USER = "회원 가입 성공";
    public static final String UPDATE_USER = "회원 정보 수정 성공";
    public static final String DELETE_USER = "회원 탈퇴 성공";
    public static final String DUPLICATE_USER = "회원가입 로그인아이디 중복 에러";

    public static final String READ_MEMO = "메모 정보 조회 성공";
    public static final String NOT_FOUND_MEMO = "메모를 찾을 수 없습니다.";
    public static final String CREATED_MEMO = "메모 생성 성공";
    public static final String UPDATE_MEMO = "메모 수정 성공";
    public static final String DELETE_MEMO = "메모 삭제 성공";

    public static final String DUPLICATE_USERANDMEMO = "사용자와 메모 관계 중복 에러";

    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";
    public static final String DB_ERROR = "데이터베이스 에러";
}
