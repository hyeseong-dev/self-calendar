package com.fastcampus.api.util;

import com.fastcampus.api.dto.EventDto;
import com.fastcampus.api.dto.NotificationDto;
import com.fastcampus.api.dto.ScheduleDto;
import com.fastcampus.api.dto.TaskDto;
import com.fastcampus.core.domain.entity.Schedule;

public abstract class DtoConverter {
    public static ScheduleDto fromSchedule(Schedule schedule){
        switch (schedule.getScheduleType()){
            case EVENT:
                return EventDto.builder()
                        .scheduleId(schedule.getId())
                        .description(schedule.getDescription())
                        .startAt(schedule.getStartAt())
                        .endAt(schedule.getEndAt())
                        .title(schedule.getTitle())
                        .writerId(schedule.getWriter().getId())
                        .build();
            case TASK:
                return TaskDto.builder()
                        .scheduleId(schedule.getId())
                        .taskAt(schedule.getStartAt())
                        .description(schedule.getDescription())
                        .writerId(schedule.getWriter().getId())
                        .title(schedule.getTitle())
                        .build();
            case NOTIFICATION:
                return NotificationDto.builder()
                        .scheduleId(schedule.getId())
                        .notifyAt(schedule.getStartAt())
                        .writerId(schedule.getWriter().getId())
                        .title(schedule.getTitle())
                        .build();
            default:
                throw new RuntimeException("bad request. not matched schedule type");

        }
    }
}
