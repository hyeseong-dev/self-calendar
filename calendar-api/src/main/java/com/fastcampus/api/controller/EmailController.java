package com.fastcampus.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.MimeMessage;
import org.springframework.ui.Model;

import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class EmailController {
    private final JavaMailSender emailSender;

    @GetMapping("/api/mail")
    public void sendTestMail(){
        final MimeMessagePreparator preparator = (MimeMessage message) ->{
            final MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo("lhs4859@naver.com");
            helper.setSubject("제목입니다!!");
            helper.setText("여기는 테스트 내용입니다.^^");
        };
        emailSender.send(preparator);
    }

    @GetMapping("test/template")
    public ModelAndView testTemplate(Model model){
        final Map<String, Object> props = new HashMap<>();
        ModelAndView modelAndView = new ModelAndView("engagement-email"  );
        modelAndView.addObject("title", "타이틀입니다!");
        modelAndView.addObject("calendar", "sample@gmail.com!");
        modelAndView.addObject("period", "언제부터 언제까지");
        modelAndView.addObject("attendees", List.of("test3@mail.io",
                                        "test2@mail.io",
                                        "test1@mail.io"));
        modelAndView.addObject("acceptUrl", "http://localhost:8080");
        modelAndView.addObject("rejectUrl", "http://localhost:8080");

        return modelAndView;
    }

}
