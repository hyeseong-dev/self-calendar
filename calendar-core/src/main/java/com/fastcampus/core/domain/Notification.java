package com.fastcampus.core.domain;

import com.fastcampus.core.domain.entity.Schedule;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Notification {

    private Schedule schedule;

    public Notification(Schedule schedule) {
        this.schedule = schedule;
    }
}
