package com.fastcampus.api.service;

import com.fastcampus.api.dto.AuthUser;
import com.fastcampus.core.domain.RequestReplyType;
import com.fastcampus.core.domain.RequestStatus;
import com.fastcampus.core.domain.entity.repository.EngagementRepository;
import com.fastcampus.core.exception.CalendarException;
import com.fastcampus.core.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EngagementService {
    private final EngagementRepository engagementRepository;

    @Transactional
    public RequestStatus update(AuthUser authUser, Long engagementId, RequestReplyType type) {
        // engagemnt 조회
        // 참석자가 auth user와 같은지 비교
        // requested 상태인지 체크
        // update
        engagementRepository.findById(engagementId)
                .filter(e -> e.getRequestStatus() == RequestStatus.REQUESTED)
                .filter(e ->e.getAttendee().getId().equals(authUser.getId()))
                .map(e -> e.reply(type))
                .orElseThrow(() -> new CalendarException(ErrorCode.BAD_REQUEST))
                .getRequestStatus();
        return null;
    }
}
