package com.fastcampus.api.service;

import com.fastcampus.api.dto.EngagementEmailStuff;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("test")
@Service
public class FakeEmailService implements EmailService{

    @Override
    public void sendEngagement(EngagementEmailStuff stuff)
    {
        System.out.println("send email. email: " + stuff.getSubject());
    }
}
