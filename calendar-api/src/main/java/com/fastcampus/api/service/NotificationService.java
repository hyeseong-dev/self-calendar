package com.fastcampus.api.service;

import com.fastcampus.api.dto.AuthUser;
import com.fastcampus.api.dto.NotificationCreateReq;
import com.fastcampus.core.domain.entity.Schedule;
import com.fastcampus.core.domain.entity.User;
import com.fastcampus.core.domain.entity.repository.ScheduleRepository;
import com.fastcampus.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final UserService userService;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public void create(NotificationCreateReq notificationCreateReq, AuthUser authUser) {
        final User user = userService.findByUserId(authUser.getId());
        final List<LocalDateTime> notifyAtList = notificationCreateReq.getRepeatTimes();
        notifyAtList.forEach((notifyAt -> {
            final Schedule notificationSchedule = Schedule.notification(
                                                                        notificationCreateReq.getTitle(),
                                                                        notifyAt,
                                                                        user);
            scheduleRepository.save(notificationSchedule);
        }));
    }
}
