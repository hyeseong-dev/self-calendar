package com.fastcampus.api.dto;

import com.fastcampus.core.util.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class NotificationCreateReqTest {

    @DisplayName("repeatInfo가 null일 때 단일 알림 시간 반환 확인")
    @Test
    public void testGetRepeatTimesWithNullRepeatInfo() {
        LocalDateTime notifyAt = LocalDateTime.of(2023, 12, 12, 12, 12, 12);
        NotificationCreateReq req = new NotificationCreateReq("저녘 달리기", notifyAt, null);

        List<LocalDateTime> result = req.getRepeatTimes();

        assertEquals(1, result.size());
        assertEquals(notifyAt, result.get(0));
    }

    @DisplayName("연간 간격으로 설정된 반복 알림 시간 계산 확인")
    @Test
    public void testGetRepeatTimesWithYearlyInterval() {
        LocalDateTime notifyAt = LocalDateTime.of(2023, 12, 12, 12, 12, 12);
        NotificationCreateReq.Interval interval = new NotificationCreateReq.Interval(2, TimeUnit.YEAR);
        NotificationCreateReq.RepeatInfo repeatInfo = new NotificationCreateReq.RepeatInfo(interval, 2);

        NotificationCreateReq req = new NotificationCreateReq("저녘 달리기", notifyAt, repeatInfo);

        List<LocalDateTime> result = req.getRepeatTimes();

        assertEquals(2, result.size());
        assertEquals(notifyAt, result.get(0));
        assertEquals(notifyAt.plusYears(2), result.get(1));
    }

    @DisplayName("DAY 단위로 설정된 반복 알림 시간 계산 확인")
    @Test
    public void testGetRepeatTimesWithDayInterval(){
        LocalDateTime notifyAt = LocalDateTime.of(2023, 12, 12, 12, 12, 12);
        NotificationCreateReq.Interval interval = new NotificationCreateReq.Interval(1, TimeUnit.DAY);
        NotificationCreateReq.RepeatInfo repeatInfo = new NotificationCreateReq.RepeatInfo(interval, 2);

        NotificationCreateReq req = new NotificationCreateReq("저녘 달리기", notifyAt, repeatInfo);

        List<LocalDateTime> result = req.getRepeatTimes();

        assertEquals(2, result.size());
        assertEquals(notifyAt, result.get(0));
        assertEquals(notifyAt.plusDays(1), result.get(1));

    }

    @DisplayName("WEEK 단위로 설정된 반복 알림 시간 계산 확인")
    @Test
    public void testGetRepeatTimesWithWeekInterval(){
        LocalDateTime notifyAt = LocalDateTime.of(2023, 12, 12, 12, 12, 12);
        NotificationCreateReq.Interval interval = new NotificationCreateReq.Interval(1, TimeUnit.WEEK);
        NotificationCreateReq.RepeatInfo repeatInfo = new NotificationCreateReq.RepeatInfo(interval, 2);

        NotificationCreateReq req = new NotificationCreateReq("저녘 달리기", notifyAt, repeatInfo);

        List<LocalDateTime> result = req.getRepeatTimes();

        assertEquals(2, result.size());
        assertEquals(notifyAt, result.get(0));
        assertEquals(notifyAt.plusWeeks(1), result.get(1));
    }

    @DisplayName("MONTH 단위로 설정된 반복 알림 시간 계산 확인")
    @Test
    public void testGetRepeatTimesWithMonthInterval(){
        LocalDateTime notifyAt = LocalDateTime.of(2023, 12, 12, 12, 12, 12);
        NotificationCreateReq.Interval interval = new NotificationCreateReq.Interval(1, TimeUnit.MONTH);
        NotificationCreateReq.RepeatInfo repeatInfo = new NotificationCreateReq.RepeatInfo(interval, 2);

        NotificationCreateReq req = new NotificationCreateReq("저녘 달리기", notifyAt, repeatInfo);

        List<LocalDateTime> result = req.getRepeatTimes();

        assertEquals(2, result.size());
        assertEquals(notifyAt, result.get(0));
        assertEquals(notifyAt.plusMonths(1), result.get(1));

    }

}
























