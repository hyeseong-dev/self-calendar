package com.fastcampus.api.service;

import com.fastcampus.api.dto.AuthUser;
import com.fastcampus.api.dto.ScheduleDto;
import com.fastcampus.api.util.DtoConverter;
import com.fastcampus.core.domain.entity.repository.EngagementRepository;
import com.fastcampus.core.domain.entity.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ScheduleQueryService {

    private final ScheduleRepository scheduleRepository;
    private final EngagementRepository engagementRepository;


    public List<ScheduleDto> getScheduleByDay(AuthUser authUser, LocalDate date){
        Stream<ScheduleDto> schedules = getSchedulesStream(authUser, date);
        Stream<ScheduleDto> engagements = getEngagementsStream(authUser, date);

        return Stream.concat(schedules, engagements).collect(Collectors.toList());
    }

    private Stream<ScheduleDto> getSchedulesStream(AuthUser authUser, LocalDate date) {
        return scheduleRepository
                .findAllByWriter_Id(authUser.getId())
                .stream()
                .filter(schedule -> schedule.isOverlapped(date))
                .map(DtoConverter::fromSchedule);
    }

    private Stream<ScheduleDto> getEngagementsStream(AuthUser authUser, LocalDate date) {
        return engagementRepository
                .findAllByAttendee_Id(authUser.getId())
                .stream()
                .filter(engagement -> engagement.isOverlapped(date))
                .map(engagement -> DtoConverter.fromSchedule(engagement.getSchedule()));
    }
}



























