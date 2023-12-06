package com.fastcampus.core.domain.entity;

import com.fastcampus.core.domain.RequestStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@NoArgsConstructor
@Getter
@Table(name = "engagements")
@Entity
public class Engagement extends BaseEntity{


    @JoinColumn(name="schedule_id")
    @ManyToOne
    private Schedule schedule;

    @JoinColumn(name = "attendee_id")
    @ManyToOne
    private User attendee;
    private RequestStatus requestStatus;

}
