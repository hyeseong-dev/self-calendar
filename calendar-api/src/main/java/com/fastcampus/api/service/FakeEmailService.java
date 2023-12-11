package com.fastcampus.api.service;

import com.fastcampus.core.domain.entity.Engagement;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("dev")
@Service
public class FakeEmailService implements EmailService{

    @Override
    public void sendEngagement(Engagement engagement) {
        System.out.println("send email. email: " + engagement.getAttendee().getEmail() + ", scheduleId: " + engagement.getAttendee().getId());
    }
}
