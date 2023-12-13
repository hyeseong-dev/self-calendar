package com.fastcampus.api.exception;

import com.fastcampus.core.exception.ErrorCode;
import org.springframework.http.HttpStatus;

/**
 * ErrorCode를 HTTP 상태 코드로 매핑하는 유틸리티 클래스.
 */
public class ErrorHttpStatusMapper {

    /**
     * 주어진 ErrorCode에 대응하는 HttpStatus를 반환합니다.
     *
     * @param errorCode 에러 코드
     * @return 대응하는 HttpStatus
     */
    public static HttpStatus mapToStatus(ErrorCode errorCode){
        switch (errorCode){
            case ALREADY_EXISTS_USER:
            case VALIDATION_FAIL:
            case BAD_REQUEST:
            case EVENT_CREATE_OVERLAPPED_PERIOD:
                return HttpStatus.BAD_REQUEST;
            case PASSWORD_NOT_MATCH:
            case USER_NOT_FOUND:
                return HttpStatus.UNAUTHORIZED;
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
