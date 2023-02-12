package com.example.calendar.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    INVALID_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰이 유효하지 않습니다"),
    MISMATCH_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰의 유저 정보가 일치하지 않습니다"),
    INVALID_TYPE(BAD_REQUEST, "유효 값이 아닙니다"),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "권한 정보가 없는 토큰입니다"),
    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    MEMBER_NOT_FOUND(NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"),
    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "로그아웃 된 사용자입니다"),
    USER_NOT_FOUND(NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"),
    CALENDAR_NOT_FOUND(NOT_FOUND, "해당 캘린더 정보를 찾을 수 없습니다"),
    EMAIL_NOT_FOUND(NOT_FOUND, "해당 이메일 정보를 찾을 수 없습니다"),
    USER_CALENDAR_NOT_FOUND(NOT_FOUND, "해당 유저 캘린더 매핑 정보를 찾을 수 없습니다"),
    SCHEDULE_NOT_FOUND(NOT_FOUND,"해당 일정 정보를 찾을수 없습니다."),
    CALENDAR_SCHEDULE_NOT_FOUND(NOT_FOUND,"해당 캘린더 일정정보를 찾을 수 없습니다."),
    NOTI_NOT_FOUND(NOT_FOUND,"해당 알림 정보를 찾을 수 없습니다."),
    FRIEND_NOT_FOUND(NOT_FOUND,"해당 친구 정보를 찾을 수 없습니다."),
    DELETE_NOTI_FAILED(NOT_FOUND,"해당 알림 정보 삭제를 실패했습니다."),
    UPDATE_NOTI_TYPE_FAILED(NOT_FOUND,"알림 타입 갱신에 실패했습니다."),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다"),
    DUPLICATE_FRIEND_REQUEST(CONFLICT, "친구 요청이 이미 존재합니다"),
    DUPLICATE_USER(CONFLICT, "유저가 이미 존재합니다"),
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}