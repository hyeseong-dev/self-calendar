package com.fastcampus.api.service;

import com.fastcampus.api.dto.EngagementEmailStuff;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import java.util.Locale;

/**
 * 이메일 서비스의 구현체로, 이메일 발송 기능을 제공합니다.
 * {@link JavaMailSender}와 {@link SpringTemplateEngine}을 사용하여 이메일을 구성하고 발송합니다.
 */
@RequiredArgsConstructor// 롬복 라이브러리를 사용하여 필수 생성자를 자동 생성합니다.
@Service                // 스프링에게 이 클래스가 서비스 레이어의 구성 요소임을 알립니다.
public class RealEmailService implements EmailService {

    private final JavaMailSender emailSender;           // 이메일을 보내는 데 사용되는 스프링의 JavaMailSender.
    private final SpringTemplateEngine templateEngine;  // 이메일 템플릿을 처리하는 데 사용되는 Thymeleaf의 SpringTemplateEngine.

    /**
     * 참여 관련 정보를 담은 이메일을 발송합니다.
     *
     * @param stuff 이메일 발송에 필요한 정보를 담고 있는 {@link EngagementEmailStuff} 객체.
     */
    @Override
    public void sendEngagement(EngagementEmailStuff stuff) {
        MimeMessagePreparator preparator = createMimeMessagePreparator(stuff); // 이메일을 준비하는 MimeMessagePreparator를 생성합니다.
        emailSender.send(preparator);   // 준비된 메시지를 실제로 발송합니다.
    }

    /**
     * 이메일 발송을 위한 {@link MimeMessagePreparator}를 생성합니다.
     * 이메일 설정을 위한 {@link MimeMessageHelper}를 구성합니다.
     *
     * @param stuff 이메일 발송에 필요한 정보를 담고 있는 {@link EngagementEmailStuff} 객체.
     * @return 이메일 발송 준비를 완료한 {@link MimeMessagePreparator} 객체.
     */
    private MimeMessagePreparator createMimeMessagePreparator(EngagementEmailStuff stuff) {
        return message -> {                                         // MimeMessagePreparator 인터페이스의 구현을 제공합니다.
            MimeMessageHelper helper = new MimeMessageHelper(message); // 이메일 구성을 돕는 MimeMessageHelper를 생성합니다.
            setupMessageHelper(helper, stuff);                         // helper를 사용하여 이메일의 세부 사항을 설정합니다.
        };
    }

    /**
     * {@link MimeMessageHelper}를 사용하여 이메일의 수신자, 제목, 내용 등을 설정합니다.
     *
     * @param helper 이메일 설정을 돕는 {@link MimeMessageHelper} 객체.
     * @param stuff 이메일 내용 구성에 필요한 정보를 담고 있는 {@link EngagementEmailStuff} 객체.
     * @throws MessagingException 이메일 설정 과정에서 오류가 발생한 경우.
     */
    private void setupMessageHelper(MimeMessageHelper helper, EngagementEmailStuff stuff) throws MessagingException {
        helper.setTo(stuff.getToEmail()); // 이메일의 수신자를 설정합니다.
        helper.setSubject(stuff.getSubject()); // 이메일의 제목을 설정합니다.
        String emailContent = generateEmailContent(stuff); // 이메일의 내용을 생성합니다.
        helper.setText(emailContent, true); // 생성된 이메일 내용을 설정합니다. 'true'는 HTML 형식임을 나타냅니다.
    }

    /**
     * {@link SpringTemplateEngine}을 사용하여 이메일의 HTML 내용을 생성합니다.
     *
     * @param stuff 이메일 내용 구성에 필요한 정보를 담고 있는 {@link EngagementEmailStuff} 객체.
     * @return 생성된 이메일 내용 HTML 문자열.
     */
    private String generateEmailContent(EngagementEmailStuff stuff) {
        Context context = new Context(Locale.KOREAN, stuff.getProps()); // 이메일 템플릿에 전달될 컨텍스트를 생성합니다.
        return templateEngine.process("engagement-email", context); // 템플릿 엔진을 사용하여 이메일 내용을 생성합니다.
    }
}