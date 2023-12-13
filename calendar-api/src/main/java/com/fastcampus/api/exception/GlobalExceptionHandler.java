package com.fastcampus.api.exception;

import com.fastcampus.core.exception.CalendarException;
import com.fastcampus.core.exception.ErrorCode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

import static com.fastcampus.api.exception.ErrorHttpStatusMapper.mapToStatus;

/**
 * 전역적으로 예외를 처리하는 핸들러 클래스.
 * 애플리케이션 전반에서 발생하는 예외를 일관된 방식으로 처리합니다.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * CalendarException을 처리합니다.
     *
     * @param ex 처리할 CalendarException 객체
     * @return 에러 응답을 담은 ResponseEntity 객체
     */
    @ExceptionHandler(CalendarException.class)
    public ResponseEntity<ErrorResponse> handle(CalendarException ex){
        final ErrorCode errorCode = ex.getErrorCode();
        return new ResponseEntity<>(new ErrorResponse(errorCode, errorCode.getMessage()), mapToStatus(errorCode));
    }

    /**
     * MethodArgumentNotValidException을 처리합니다.
     * 주로 요청 데이터의 유효성 검증 실패 시 발생합니다.
     *
     * @param ex 처리할 MethodArgumentNotValidException 객체
     * @return 에러 응답을 담은 ResponseEntity 객체
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException ex){
        final ErrorCode code = ErrorCode.VALIDATION_FAIL;
        return new ResponseEntity<>(new ErrorResponse(code, Optional.ofNullable(ex.getBindingResult().getFieldError())
                                                                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                                                    .orElse(code.getMessage())
                                                     ), mapToStatus(code)
                                    );
    }

    /**
     * 에러 응답을 정의하는 내부 클래스.
     */
    @Data
    public static class ErrorResponse{
        private final ErrorCode errorCode;
        private final String errorMessage;
    }
}
