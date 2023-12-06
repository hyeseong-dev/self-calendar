package com.fastcampus.core.domain.entity;

import com.fastcampus.core.domain.Event;
import com.fastcampus.core.domain.Notification;
import com.fastcampus.core.domain.ScheduleType;
import com.fastcampus.core.domain.Task;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name="schedules")
@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String title;
    private String description;

    @JoinColumn(name = "writer_id")
    @ManyToOne
    private User writer;

    @Enumerated(EnumType.STRING)
    private ScheduleType scheduleType;

    private LocalDateTime createdAt = LocalDateTime.now();

    public static Schedule task(String title, String description, LocalDateTime taskAt, User writer){
        return Schedule.builder()
                .title(title)
                .description(description)
                .startAt(taskAt)
                .scheduleType(ScheduleType.TASK)
                .build();
    }

    public static Schedule event(String title, String description, LocalDateTime startAt, LocalDateTime endAt, User writer){
        return Schedule.builder()
                .title(title)
                .description(description)
                .startAt(startAt)
                .endAt(endAt)
                .scheduleType(ScheduleType.EVENT)
                .build();
    }

    public static Schedule notification(String title, String description, LocalDateTime notifyAt, User writer){
        return Schedule.builder()
                .title(title)
                .description(description)
                .startAt(notifyAt)
                .scheduleType(ScheduleType.NOTIFICATION)
                .build();
    }

    public Task toTask(){
        return new Task(this);
    }

    public Event toEvent(){
        return new Event(this);
    }

    public Notification toNotification(){
        return new Notification(this);
    }
}
