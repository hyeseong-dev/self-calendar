package com.fastcampus.core.exception;

import lombok.Getter;

/**
 * 캘린더 애플리케이션 전용 예외 클래스.
 * ErrorCode를 기반으로 상세한 예외 정보를 제공합니다.
 */
@Getter
public class CalendarException extends RuntimeException {

    private final ErrorCode errorCode;

    /**
     * 특정 ErrorCode에 해당하는 예외를 생성합니다.
     *
     * @param errorCode 발생한 에러의 종류를 나타내는 ErrorCode 열거형
     */
    public CalendarException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
