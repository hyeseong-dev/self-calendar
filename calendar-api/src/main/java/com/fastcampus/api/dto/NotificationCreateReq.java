package com.fastcampus.api.dto;

import com.fastcampus.core.util.TimeUnit;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 알림 생성을 위한 요청 데이터를 나타내는 DTO(Data Transfer Object) 클래스입니다.
 * 알림의 제목, 알림 시간, 반복 정보를 포함합니다.
 */
@Data
public class NotificationCreateReq {

    /**
     * 알림의 제목입니다. 공백이 아닌 문자열이어야 합니다.
     */
    @NotBlank
    private final String title;

    /**
     * 알림이 발생할 시간입니다. null이 아니어야 합니다.
     */
    @NotNull
    private final LocalDateTime notifyAt;

    /**
     * 알림 반복에 대한 정보입니다. null일 수 있습니다.
     */
    private final RepeatInfo repeatInfo;

    /**
     * 알림이 반복되는 시간들을 계산하여 반환합니다.
     * 반복 정보가 없는 경우 단일 알림 시간을 반환합니다.
     *
     * @return LocalDateTime 객체의 리스트로, 반복되는 알림 시간을 나타냅니다.
     * @throws RuntimeException 반복 정보의 간격이나 단위가 부적절한 경우 발생합니다.
     */
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

    /**
     * 알림의 반복 정보를 나타내는 클래스입니다.
     */
    @Data
    public static class RepeatInfo{
        /**
         * 반복 간격에 대한 정보입니다.
         */
        private final Interval interval;
        /**
         * 반복 횟수입니다.
         */
        private final int times;
    }

    /**
     * 반복 간격을 나타내는 클래스입니다.
     */
    @Data
    public static class Interval{
        /**
         * 간격 값입니다.
         */
        private final int intervalValue;

        /**
         * 시간 단위입니다. 예: DAY, WEEK, MONTH, YEAR
         */
        private final TimeUnit timeUnit;
    }
}
