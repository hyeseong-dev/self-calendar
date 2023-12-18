package com.fastcampus.batch.job;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j // Lombok 라이브러리의 로깅 기능을 위한 어노테이션.
@RequiredArgsConstructor // 롬복을 사용하여 필수 생성자를 자동으로 생성합니다.
@Configuration // 이 클래스를 스프링의 설정 클래스로 지정합니다.
public class SimpleJobConfiguration {

    // Job과 Step을 생성하기 위한 팩토리 클래스들을 자동 주입받습니다.
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean // 스프링 빈으로 등록될 Job을 정의합니다.
    public Job simpleJob(){
        return jobBuilderFactory.get("simpleJob")   // "simpleJob"이라는 이름의 Job을 생성합니다.
                .start(simpleStep1())               // simpleStep1을 Job의 첫 번째 단계로 설정합니다.
                .build();                           // Job을 빌드합니다.
    }

    @Bean // 스프링 빈으로 등록될 Step을 정의합니다.
    public Step simpleStep1(){
        return stepBuilderFactory.get("simpleStep1")        // "simpleStep1"이라는 이름의 Step을 생성합니다.
                .tasklet(((contribution, chunkContext) -> { // Tasklet을 사용하여 간단한 작업을 정의합니다.
                    log.info(">>>>> This is Step1");        // 로그를 출력합니다.
                    return RepeatStatus.FINISHED;           // 작업이 완료됨을 나타냅니다.
                })).build();                                // Step을 빌드합니다.
    }
}
