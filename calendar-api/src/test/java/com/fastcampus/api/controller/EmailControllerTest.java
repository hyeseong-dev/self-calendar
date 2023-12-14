package com.fastcampus.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class EmailControllerTest {

    private JavaMailSender emailSender; // JavaMailSender 모의 객체 선언
    private EmailController emailController; // 테스트 대상인 EmailController 선언
    private ArgumentCaptor<MimeMessagePreparator> messagePreparatorCaptor; // MimeMessagePreparator에 대한 ArgumentCaptor 선언

    @DisplayName("지메일 발송 이메일 테스트 - 발신자, 제목, 내용")
    @Test
    public void testSendTestMail() throws Exception {
        // GIVEN: 모의 객체와 테스트 대상을 초기화
        emailSender = mock(JavaMailSender.class);                                        // JavaMailSender 모의 객체 초기화
        emailController = new EmailController(emailSender);                              // EmailController 객체 생성 및 초기화
        messagePreparatorCaptor = ArgumentCaptor.forClass(MimeMessagePreparator.class);  // ArgumentCaptor 초기화
        MimeMessage mimeMessage = new MimeMessage(Session.getInstance(new Properties()));// 테스트용 MimeMessage 객체 생성

        // WHEN: 테스트 메일 발송
        emailController.sendTestMail(); // sendTestMail 메소드 실행

        // THEN: emailSender.send()가 올바른 인자와 함께 호출되었는지 확인
        verify(emailSender, times(1)).send(messagePreparatorCaptor.capture()); // send 메소드 호출 검증 및 인자 포착
        MimeMessagePreparator capturedPreparator = messagePreparatorCaptor.getValue(); // 포착된 MimeMessagePreparator 가져오기

        // MimeMessage 준비 및 검증
        capturedPreparator.prepare(mimeMessage); // MimeMessage 준비
        assertEquals(
                "lhs4859@naver.com",
                mimeMessage.getRecipients(MimeMessage.RecipientType.TO)[0].toString()); // 수신자 주소 검증
        assertEquals(
                "제목입니다!!",
                mimeMessage.getSubject()); // 이메일 제목 검증
        assertEquals(
                "여기는 테스트 내용입니다.^^",
                mimeMessage.getContent().toString().trim()); // 이메일 내용 검증
    }
}


