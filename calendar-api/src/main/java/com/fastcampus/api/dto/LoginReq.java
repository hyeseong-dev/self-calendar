package com.fastcampus.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 이 클래스는 로그인 요청에 사용되는 데이터 전송 객체(DTO)입니다.
 * 사용자의 이메일과 비밀번호를 포함합니다.
 */
@Data
public class LoginReq {

    /**
     * 사용자 이메일
     */
    @NotBlank
    private final String email;

    /**
     * 사용자 비밀번호
     */
    @NotBlank
    private final String password;
}