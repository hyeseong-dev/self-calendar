package com.fastcampus.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 이벤트 생성을 위한 요청 데이터를 나타내는 DTO(Data Transfer Object) 클래스입니다.
 * 이벤트의 제목, 설명, 시작 및 종료 시간, 참석자 ID 목록을 포함합니다.
 */
@Data
public class EventCreateReq {

    /**
     * 이벤트의 제목입니다. 공백이 아닌 문자열이어야 합니다.
     */
    @NotBlank
    private final String title;

    /**
     * 이벤트의 설명입니다. null일 수 있습니다.
     */
    private final String description;

    /**
     * 이벤트의 시작 시간입니다. null이 아니어야 합니다.
     */
    @NotNull
    private final LocalDateTime startAt;

    /**
     * 이벤트의 종료 시간입니다. null이 아니어야 합니다.
     */
    @NotNull
    private final LocalDateTime endAt;

    /**
     * 이벤트에 참석할 사용자들의 ID 목록입니다.null일 수 있습니다.
     */
    private final List<Long> attendeeIds;
}
