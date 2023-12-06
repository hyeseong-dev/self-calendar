package com.fastcampus.core;

import com.fastcampus.core.domain.ScheduleType;
import com.fastcampus.core.domain.entity.Schedule;
import com.fastcampus.core.domain.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DomainCreateTest {

    @DisplayName("이벤트 생성 - 약속 - 유저")
    @Test
    void eventCreate(){
        final User me = new User("meme", "email", "pw", LocalDate.now());
        final Schedule taskSchedule = Schedule.task("할일", "청소하기", LocalDateTime.now(), me);
        assertEquals(taskSchedule.getScheduleType(), ScheduleType.TASK);
        assertEquals(taskSchedule.toTask().getTitle(), "할일");


    }
}
