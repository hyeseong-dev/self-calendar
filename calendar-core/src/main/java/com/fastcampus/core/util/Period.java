package com.fastcampus.core.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 특정 기간을 나타내는 유틸리티 클래스입니다.
 * 시작 시간과 종료 시간을 기반으로 기간을 정의하고, 기간 중복 여부를 판단하는 기능을 제공합니다.
 */
public class Period {
    private final LocalDateTime startAt; // 기간의 시작 시간
    private final LocalDateTime endAt;   // 기간의 종료 시간

    /**
     * Period 객체를 생성합니다.
     *
     * @param startAt 기간의 시작 시간
     * @param endAt 기간의 종료 시간, null인 경우 startAt으로 설정됩니다.
     */
    public Period(LocalDateTime startAt, LocalDateTime endAt) {
        this.startAt = startAt;
        this.endAt = endAt == null ? startAt : endAt;
    }

    /**
     * LocalDateTime 기반으로 Period 객체를 생성합니다.
     *
     * @param startAt 기간의 시작 시간
     * @param endAt 기간의 종료 시간
     * @return 새로운 Period 객체
     */
    public static Period of(LocalDateTime startAt, LocalDateTime endAt) {
        return new Period(startAt, endAt);
    }

    /**
     * LocalDate 기반으로 Period 객체를 생성합니다.
     *
     * @param startDate 기간의 시작 날짜
     * @param endDate 기간의 종료 날짜
     * @return 새로운 Period 객체
     */
    public static Period of(LocalDate startDate, LocalDate endDate) {
        return new Period(startDate.atStartOfDay(), LocalDateTime.of(endDate, LocalTime.of(23, 59, 59, 999999999)));
    }

    /**
     * 다른 Period 객체와의 중복 여부를 확인합니다.
     *
     * @param period 확인할 다른 Period 객체
     * @return 중복되면 true, 그렇지 않으면 false
     */
    public boolean isOverlapped(Period period) {
        return isOverlapped(period.startAt, period.endAt);
    }

    /**
     * 주어진 LocalDateTime 범위와의 중복 여부를 확인합니다.
     *
     * @param startAt 확인할 기간의 시작 시간
     * @param endAt 확인할 기간의 종료 시간
     * @return 중복되면 true, 그렇지 않으면 false
     */
    public boolean isOverlapped(LocalDateTime startAt, LocalDateTime endAt) {
        return this.startAt.isBefore(endAt) && startAt.isBefore(this.endAt);
    }

    /**
     * 기간의 시작 시간을 반환합니다.
     *
     * @return 기간의 시작 시간
     */
    public boolean isOverlapped(LocalDate date) {
        return isOverlapped(date.atStartOfDay(), LocalDateTime.of(date, LocalTime.of(23, 59, 59, 999999999)));
    }

    /**
     * 기간의 시작 시간을 반환합니다.
     *
     * @return 기간의 시작 시간
     */
    public LocalDateTime getStartAt(){
        return startAt;
    }

    /**
     * 기간의 종료 시간을 반환합니다.
     *
     * @return 기간의 종료 시간
     */
    public LocalDateTime getEndAt(){
        return endAt;
    }
}
