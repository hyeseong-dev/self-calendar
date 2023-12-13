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

/**
 * 일정 관련 작업을 처리하는 서비스 클래스입니다.
 * 이 서비스는 날짜별, 주별, 월별로 일정을 검색하는 메서드를 제공합니다.
 */
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ScheduleQueryService {

    private final ScheduleRepository scheduleRepository;
    private final EngagementRepository engagementRepository;


    /**
     * 특정 날짜에 대한 일정 데이터 목록을 검색합니다.
     *
     * @param authUser 검색할 일정의 사용자 인증 정보입니다.
     * @param date     일정을 검색할 날짜입니다.
     * @return 해당 날짜에 대한 ScheduleDto 객체 목록입니다.
     */
    public List<ScheduleDto> getScheduleByDay(AuthUser authUser, LocalDate date) {
        return getScheduleByPeriod(authUser, Period.of(date, date));
    }

    /**
     * 주어진 주의 일정 데이터 목록을 검색합니다.
     * 주는 startOfWeek 날짜로부터 시작하는 7일간으로 정의됩니다.
     *
     * @param authUser    일정을 검색할 사용자 인증 정보입니다.
     * @param startOfWeek 주의 시작 날짜를 나타냅니다.
     * @return 해당 주에 대한 ScheduleDto 객체 목록입니다.
     */
    public List<ScheduleDto> getScheduleByWeek(AuthUser authUser, LocalDate startOfWeek) {
        return getScheduleByPeriod(authUser, Period.of(startOfWeek, startOfWeek.plusDays(6)));
    }

    /**
     * 주어진 달의 일정 데이터 목록을 검색합니다.
     * 달은 YearMonth 매개변수로 정의됩니다.
     *
     * @param authUser  일정을 검색할 사용자 인증 정보입니다.
     * @param yearMonth 일정을 검색할 연도와 달입니다.
     * @return 해당 달에 대한 ScheduleDto 객체 목록입니다.
     */
    public List<ScheduleDto> getScheduleByMonth(AuthUser authUser, YearMonth yearMonth) {
        return getScheduleByPeriod(authUser, Period.of(yearMonth.atDay(1), yearMonth.atEndOfMonth()));
    }

    /**
     * 주어진 기간에 대한 일정 데이터 목록을 검색합니다.
     * 이 메서드는 지정된 기간에 대한 일정과 약속을 결합하여 필터링합니다.
     *
     * @param authUser 일정을 검색할 사용자 인증 정보입니다.
     * @param period   일정을 검색할 기간입니다.
     * @return 해당 기간에 대한 ScheduleDto 객체 목록입니다.
     */
    private List<ScheduleDto> getScheduleByPeriod(AuthUser authUser, Period period) {
        Stream<ScheduleDto> schedules = getSchedulesStream(authUser.getId(), period);
        Stream<ScheduleDto> engagements = getEngagementsStream(authUser.getId(), period);

        return Stream.concat(schedules, engagements).collect(Collectors.toList());
    }

    /**
     * 사용자 ID와 기간을 기준으로 일정을 스트림 형태로 검색합니다.
     * 해당 기간에 사용자가 작성한 모든 일정을 포함합니다.
     *
     * @param userId 검색할 사용자의 ID입니다.
     * @param period 검색할 기간입니다.
     * @return 주어진 기간 동안 사용자가 작성한 일정을 나타내는 ScheduleDto의 스트림입니다.
     */
    private Stream<ScheduleDto> getSchedulesStream(Long userId, Period period) {
        return scheduleRepository
                .findAllByWriter_Id(userId)
                .stream()
                .filter(schedule -> schedule.isOverlapped(period))
                .map(DtoConverter::fromSchedule);
    }

    /**
     * 사용자 ID와 기간을 기준으로 약속(engagement)을 스트림 형태로 검색합니다.
     * 해당 기간에 사용자가 참석하는 모든 약속을 포함합니다.
     *
     * @param userId 검색할 사용자의 ID입니다.
     * @param period 검색할 기간입니다.
     * @return 주어진 기간 동안 사용자가 참석하는 약속을 나타내는 ScheduleDto의 스트림입니다.
     */
    private Stream<ScheduleDto> getEngagementsStream(Long userId, Period period) {
        return engagementRepository
                .findAllByAttendee_Id(userId)
                .stream()
                .filter(engagement -> engagement.isOverlapped(period))
                .map(engagement -> DtoConverter.fromSchedule(engagement.getSchedule()));
    }
}
