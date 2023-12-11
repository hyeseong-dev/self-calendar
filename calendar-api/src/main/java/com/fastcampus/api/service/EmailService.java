package com.fastcampus.api.service;

import com.fastcampus.core.domain.entity.Engagement;

public interface EmailService {
    void sendEngagement(Engagement engagement);
}
