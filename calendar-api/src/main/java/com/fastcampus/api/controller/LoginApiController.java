package com.fastcampus.api.controller;

import com.fastcampus.api.dto.LoginReq;
import com.fastcampus.api.dto.SignUpReq;
import com.fastcampus.api.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


/**
 * 이 클래스는 사용자 인증 관련 API 엔드포인트를 처리하는 컨트롤러입니다.
 * 사용자의 가입(sign-up), 로그인(login), 로그아웃(logout)을 처리합니다.
 */
@RequiredArgsConstructor
@RestController
public class LoginApiController {

    private final LoginService loginService;

    /**
     * 사용자 가입(sign-up) 요청을 처리합니다.
     *
     * @param signUpReq 가입 요청 데이터
     * @param session   HttpSession 객체
     * @return ResponseEntity - 가입 처리 성공 여부
     */
    @PostMapping("/api/sign-up")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpReq signUpReq, HttpSession session) {
        loginService.signUp(signUpReq, session);
        return ResponseEntity.ok().build();
    }

    /**
     * 사용자 로그인(login) 요청을 처리합니다.
     *
     * @param loginReq 로그인 요청 데이터
     * @param session  HttpSession 객체
     * @return ResponseEntity - 로그인 처리 성공 여부
     */
    @PostMapping("/api/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginReq loginReq, HttpSession session) {
        loginService.login(loginReq, session);
        return ResponseEntity.ok().build();
    }

    /**
     * 사용자 로그아웃(logout) 요청을 처리합니다.
     *
     * @param session HttpSession 객체
     * @return ResponseEntity - 로그아웃 처리 성공 여부
     */
    @PostMapping("/api/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        loginService.logout(session);
        return ResponseEntity.ok().build();
    }
}