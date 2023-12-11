package com.fastcampus.api.dto;

import com.fastcampus.core.util.TimeUnit;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class NotificationCreateReq {
    private final String title;
    private final LocalDateTime notifyAt;
    private final RepeatInfo repeatInfo;

    public List<LocalDateTime> getRepeatTimes(){
        if(repeatInfo == null){
            return Collections.singletonList(notifyAt);
        }
        if (repeatInfo.interval == null || repeatInfo.interval.timeUnit == null) {
            throw new RuntimeException("bad request. not match time unit");
        }

        return IntStream.range(0, repeatInfo.times)
                .mapToObj(i -> {
                    long increment = (long) repeatInfo.interval.intervalValue * i;
                    switch (repeatInfo.interval.timeUnit){
                        case DAY:
                            return notifyAt.plusDays(increment);
                        case WEEK:
                            return notifyAt.plusWeeks(increment);
                        case MONTH:
                            return notifyAt.plusMonths(increment);
                        case YEAR:
                            return notifyAt.plusYears(increment);
                    }
                    // 이 경우는 발생하지 않지만, 컴파일러 오류를 방지하기 위해 추가
                    throw new IllegalStateException("Unexpected time unit: " + repeatInfo.interval.timeUnit);
                }).collect(Collectors.toList());
    }

    @Data
    public static class RepeatInfo{
        private final Interval interval;
        private final int times;
    }

    @Data
    public static class Interval{
        private final int intervalValue;
        private final TimeUnit timeUnit;
    }
}
