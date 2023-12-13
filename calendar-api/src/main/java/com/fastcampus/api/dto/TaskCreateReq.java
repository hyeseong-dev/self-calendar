package com.fastcampus.api.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 이 클래스는 새로운 작업(Task)을 생성하기 위한 데이터 전송 객체(DTO)입니다.
 * 작업의 제목, 설명 및 작업 예정 일시 정보를 포함합니다.
 */
@Data
public class TaskCreateReq {

    /**
     * 작업의 제목
     */
    @NotBlank
    private final String title;

    /**
     * 작업의 설명 (선택 사항)
     */
    private final String description;

    /**
     * 작업 예정 일시
     */
    @NotNull
    private final LocalDateTime taskAt;
}
