package com.fastcampus.api.service;

import com.fastcampus.api.dto.EngagementEmailStuff;

public interface EmailService {
    void sendEngagement(EngagementEmailStuff stuff);
}
