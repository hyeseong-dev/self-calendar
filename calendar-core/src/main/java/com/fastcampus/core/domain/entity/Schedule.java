package com.fastcampus.core.domain.entity;

import com.fastcampus.core.domain.Event;
import com.fastcampus.core.domain.Notification;
import com.fastcampus.core.domain.ScheduleType;
import com.fastcampus.core.domain.Task;
import com.fastcampus.core.util.Period;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * 일정(Schedule)을 나타내는 엔티티 클래스입니다.
 * 일정의 시작 및 종료 시간, 제목, 설명, 작성자, 일정 유형을 포함합니다.
 */
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name="schedules")
@Entity
public class Schedule extends BaseEntity{

    private LocalDateTime startAt; // 일정 시작 시간
    private LocalDateTime endAt;   // 일정 종료 시간
    private String title;          // 일정 제목
    private String description;    // 일정 설명

    @JoinColumn(name = "writer_id")
    @ManyToOne
    private User writer;           // 일정 작성자

    @Enumerated(EnumType.STRING)
    private ScheduleType scheduleType; // 일정 유형 (TASK, EVENT, NOTIFICATION)

    /**
     * Task 타입의 일정을 생성합니다.
     *
     * @param title       일정 제목
     * @param description 일정 설명
     * @param taskAt      일정 시작 시간
     * @param writer      일정 작성자
     * @return Task 타입의 Schedule 객체
     */
    public static Schedule task(String title, String description, LocalDateTime taskAt, User writer){
        return Schedule.builder()
                .startAt(taskAt)
                .title(title)
                .description(description)
                .writer(writer)
                .scheduleType(ScheduleType.TASK)
                .build();
    }

    /**
     * Event 타입의 일정을 생성합니다.
     *
     * @param title       일정 제목
     * @param description 일정 설명
     * @param startAt     일정 시작 시간
     * @param endAt       일정 종료 시간
     * @param writer      일정 작성자
     * @return Event 타입의 Schedule 객체
     */
    public static Schedule event(String title, String description, LocalDateTime startAt, LocalDateTime endAt, User writer){
        return Schedule.builder()
                .startAt(startAt)
                .endAt(endAt)
                .title(title)
                .description(description)
                .writer(writer)
                .scheduleType(ScheduleType.EVENT)
                .build();
    }

    /**
     * Notification 타입의 일정을 생성합니다.
     *
     * @param title    알림 제목
     * @param notifyAt 알림 시간
     * @param writer   일정 작성자
     * @return Notification 타입의 Schedule 객체
     */
    public static Schedule notification(String title, LocalDateTime notifyAt, User writer){
        return Schedule.builder()
                .startAt(notifyAt)
                .title(title)
                .writer(writer)
                .scheduleType(ScheduleType.NOTIFICATION)
                .build();
    }

    /**
     * 현재 Schedule 인스턴스를 Task 객체로 변환합니다.
     *
     * @return Task 객체
     */
    public Task toTask(){
        return new Task(this);
    }

    /**
     * 현재 Schedule 인스턴스를 Event 객체로 변환합니다.
     *
     * @return Event 객체
     */
    public Event toEvent(){
        return new Event(this);
    }

    /**
     * 현재 Schedule 인스턴스를 Notification 객체로 변환합니다.
     *
     * @return Notification 객체
     */
    public Notification toNotification(){
        return new Notification(this);
    }

    /**
     * 주어진 날짜가 이 일정과 겹치는지 여부를 확인합니다.
     *
     * @param date 확인할 날짜
     * @return 날짜가 일정과 겹치면 true, 그렇지 않으면 false
     */
    public boolean isOverlapped(LocalDate date) {
        return Period.of(this.getStartAt(), this.getEndAt()).isOverlapped(date);
    }

    /**
     * 주어진 기간이 이 일정과 겹치는지 여부를 확인합니다.
     *
     * @param period 확인할 기간
     * @return 기간이 일정과 겹치면 true, 그렇지 않으면 false
     */
    public boolean isOverlapped(Period period) {
        return Period.of(this.getStartAt(), this.getEndAt()).isOverlapped(period);
    }
}