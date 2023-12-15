package com.fastcampus.api.controller;


import com.fastcampus.api.dto.*;
import com.fastcampus.api.service.*;
import com.fastcampus.core.domain.RequestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

/**
 * 이 클래스는 스케줄 관련 API 엔드포인트를 처리하는 컨트롤러입니다.
 * 작업(Task), 이벤트(Event), 알림(Notification) 등의 스케줄 관련 기능을 제공합니다.
 */
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
@RestController
public class ScheduleController {

    private final ScheduleQueryService scheduleQueryService;
    private final TaskService taskService;
    private final EventService eventService;
    private final EngagementService engagementService;
    private final NotificationService notificationService;

    /**
     * 새 작업(Task)을 생성합니다.
     *
     * @param taskCreateReq 작업 생성 요청 데이터
     * @param authUser      인증된 사용자 정보
     * @return ResponseEntity - 작업 생성 성공 여부
     */
    @PostMapping("/tasks")
    public ResponseEntity<Void> createTask(
            @Valid @RequestBody TaskCreateReq taskCreateReq,
            AuthUser authUser
    ) {
        taskService.create(taskCreateReq, authUser);
        return ResponseEntity.ok().build();
    }

    /**
     * 새 이벤트(Event)를 생성합니다.
     *
     * @param eventCreateReq 이벤트 생성 요청 데이터
     * @param authUser       인증된 사용자 정보
     * @return ResponseEntity - 이벤트 생성 성공 여부
     */
    @PostMapping("/events")
    public ResponseEntity<Void> createEvent(
            @Valid @RequestBody EventCreateReq eventCreateReq,
            AuthUser authUser
    ) {
        eventService.create(eventCreateReq, authUser);
        return ResponseEntity.ok().build();
    }

    /**
     * 새 알림(Notification)을 생성합니다.
     *
     * @param notificationCreateReq 알림 생성 요청 데이터
     * @param authUser               인증된 사용자 정보
     * @return ResponseEntity - 알림 생성 성공 여부
     */
    @PostMapping("/notifications")
    public ResponseEntity<Void> createNotification(
            @Valid @RequestBody NotificationCreateReq notificationCreateReq,
            AuthUser authUser
    ) {
        notificationService.create(notificationCreateReq, authUser);
        return ResponseEntity.ok().build();
    }

    /**
     * 지정된 날짜의 일별 스케줄 목록을 반환합니다.
     *
     * @param authUser 인증된 사용자 정보
     * @param date     날짜 (선택 사항, 기본값은 현재 날짜)
     * @return List<ScheduleDto> - 일별 스케줄 목록
     */
    @GetMapping("/day")
    public List<ScheduleDto> getScheduleByDay(
            AuthUser authUser,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return scheduleQueryService.getScheduleByDay(authUser, date == null ? LocalDate.now() : date);
    }

    /**
     * 지정된 주의 주간 스케줄 목록을 반환합니다.
     *
     * @param authUser    인증된 사용자 정보
     * @param startOfWeek 주의 시작 날짜 (선택 사항, 기본값은 현재 날짜)
     * @return List<ScheduleDto> - 주간 스케줄 목록
     */
    @GetMapping("/week")
    public List<ScheduleDto> getScheduleByWeek(
            AuthUser authUser,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startOfWeek
    ) {
        return scheduleQueryService.getScheduleByWeek(authUser, startOfWeek == null ? LocalDate.now() : startOfWeek);
    }

    /**
     * 지정된 연도 및 월의 월간 스케줄 목록을 반환합니다.
     *
     * @param authUser  인증된 사용자 정보
     * @param yearMonth 연도 및 월 (선택 사항, 기본값은 현재 연도 및 월)
     * @return List<ScheduleDto> - 월간 스케줄 목록
     */
    @GetMapping("/month")
    public List<ScheduleDto> getScheduleByMonth(
            AuthUser authUser,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM") String yearMonth
    ) {
        return scheduleQueryService.getScheduleByMonth(authUser, yearMonth == null ? YearMonth.now() : YearMonth.parse(yearMonth));
    }

    @PutMapping("/events/engagements/{engagementId}")
    public RequestStatus updateEngagement(
        @Valid @RequestBody ReplyEngagementReq replyEngagementReq,
        @PathVariable Long engagementId,
        AuthUser authUser
    ){
        return engagementService.update(authUser, engagementId, replyEngagementReq.getType());
    }
}
