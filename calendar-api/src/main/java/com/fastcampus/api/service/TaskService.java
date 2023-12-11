package com.fastcampus.api.service;

import com.fastcampus.api.dto.AuthUser;
import com.fastcampus.api.dto.TaskCreateReq;
import com.fastcampus.core.domain.entity.Schedule;
import com.fastcampus.core.domain.entity.repository.ScheduleRepository;
import com.fastcampus.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final UserService userService;
    private final ScheduleRepository scheduleRepository;
    public void create(TaskCreateReq taskCreateReq, AuthUser authUser) {
        final Schedule taskSchedule = Schedule.task(
                taskCreateReq.getTitle(),
                taskCreateReq.getDescription(),
                taskCreateReq.getTaskAt(),
                userService.getOrThrowById(authUser.getId())
        );
        scheduleRepository.save(taskSchedule);
    }
}
