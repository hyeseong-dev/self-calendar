package com.fastcampus.core;

import com.fastcampus.core.domain.Engagement;
import com.fastcampus.core.domain.Event;
import com.fastcampus.core.domain.RequestStatus;
import com.fastcampus.core.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DomainCreateTest {

    @DisplayName("이벤트 생성 - 약속 - 유저")
    @Test
    void eventCreate(){
        final User writer = new User("writer", "email@email", "pw", LocalDate.now(), LocalDateTime.now());
        final User attendee = new User("attendee", "email@email", "pw", LocalDate.now(), LocalDateTime.now());
        final Event event = new Event(LocalDateTime.now(), LocalDateTime.now(), "title", "description", writer, LocalDateTime.now());

        event.addEngagement(new Engagement(event, attendee, LocalDateTime.now(), RequestStatus.REQUESTED));
        assertEquals(event.getEngagements().get(0).getEvent().getWriter().getName(), "writer");

    }
}
