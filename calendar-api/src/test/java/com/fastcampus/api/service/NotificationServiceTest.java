package com.fastcampus.api.service;

import com.fastcampus.core.domain.entity.repository.ScheduleRepository;
import com.fastcampus.core.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class NotificationServiceTest {
    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private UserService userService;

    @Mock
    private ScheduleRepository scheduleRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("NotificationService create 메소드 검증")
    public void testCreateNotification() {
//        // 테스트 데이터 준비
//        String title = "저녘 달리기";
//        LocalDateTime notifyAt = LocalDateTime.of(2023, 1, 1, 0, 0, 0);
//        NotificationCreateReq notificationCreateReq = new NotificationCreateReq(title, notifyAt, null);
//        AuthUser authUser = AuthUser.of(10L);
//        User user = new User();
//
//        // UserService 모의 객체 동작 정의
//        when(userService.findByUserId(authUser.getId())).thenReturn(user);
//
//        // 테스트 실행
//        notificationService.create(notificationCreateReq, authUser);
//
//        // 검증
//        verify(userService).findByUserId(authUser.getId());
//        verify(scheduleRepository).save(any(Schedule.class));
    }
}