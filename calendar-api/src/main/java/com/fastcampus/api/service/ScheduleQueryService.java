package com.fastcampus.api.service;

import com.fastcampus.api.dto.AuthUser;
import com.fastcampus.api.dto.ScheduleDto;
import com.fastcampus.api.util.DtoConverter;
import com.fastcampus.core.domain.entity.repository.EngagementRepository;
import com.fastcampus.core.domain.entity.repository.ScheduleRepository;
import com.fastcampus.core.util.Period;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ScheduleQueryService {

    private final ScheduleRepository scheduleRepository;
    private final EngagementRepository engagementRepository;


    public List<ScheduleDto> getScheduleByDay(AuthUser authUser, LocalDate date) {
        return getScheduleByPeriod(authUser, Period.of(date, date));
    }

    public List<ScheduleDto> getScheduleByWeek(AuthUser authUser, LocalDate startOfWeek) {
        return getScheduleByPeriod(authUser, Period.of(startOfWeek, startOfWeek.plusDays(6)));
    }

    public List<ScheduleDto> getScheduleByMonth(AuthUser authUser, YearMonth yearMonth) {
        return getScheduleByPeriod(authUser, Period.of(yearMonth.atDay(1), yearMonth.atEndOfMonth()));
    }

    private List<ScheduleDto> getScheduleByPeriod(AuthUser authUser, Period period) {
        Stream<ScheduleDto> schedules = getSchedulesStream(authUser.getId(), period);
        Stream<ScheduleDto> engagements = getEngagementsStream(authUser.getId(), period);

        return Stream.concat(schedules, engagements).collect(Collectors.toList());
    }

    private Stream<ScheduleDto> getSchedulesStream(Long userId, Period period) {
        return scheduleRepository
                .findAllByWriter_Id(userId)
                .stream()
                .filter(schedule -> schedule.isOverlapped(period))
                .map(DtoConverter::fromSchedule);
    }

    private Stream<ScheduleDto> getEngagementsStream(Long userId, Period period) {
        return engagementRepository
                .findAllByAttendee_Id(userId)
                .stream()
                .filter(engagement -> engagement.isOverlapped(period))
                .map(engagement -> DtoConverter.fromSchedule(engagement.getSchedule()));
    }
}
