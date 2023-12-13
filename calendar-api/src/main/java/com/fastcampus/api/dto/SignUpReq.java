package com.fastcampus.api.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * 이 클래스는 사용자 회원가입 요청에 사용되는 데이터 전송 객체(DTO)입니다.
 * 사용자의 이름, 이메일, 비밀번호 및 생년월일 정보를 포함합니다.
 */
@Data
public class SignUpReq {

    /**
     * 사용자 이름
     */
    @NotBlank
    private final String name;

    /**
     * 사용자 이메일 (이메일 형식)
     */
    @Email
    @NotBlank
    private final String email;

    /**
     * 사용자 비밀번호 (최소 6자리 이상)
     */
    @Size(min=6, message="6자리 이상 입력해주세요.")
    @NotBlank
    private final String password;

    /**
     * 사용자 생년월일
     */
    @NotNull
    private final LocalDate birthday;
}
