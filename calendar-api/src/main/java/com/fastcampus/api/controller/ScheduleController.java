package com.fastcampus.api.controller;


import com.fastcampus.api.dto.AuthUser;
import com.fastcampus.api.dto.TaskCreateReq;
import com.fastcampus.api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static com.fastcampus.api.service.LoginService.LOGIN_SESSION_KEY;

@RequiredArgsConstructor
@RequestMapping("/api/schedules")
@RestController
public class ScheduleController {

    private final TaskService taskService;

    @PostMapping("/tasks")
    public ResponseEntity<Void> createTask(
            @RequestBody TaskCreateReq taskCreateReq,
            HttpSession httpSession)
    {
        final Long userId = (Long) httpSession.getAttribute(LOGIN_SESSION_KEY);
        if (userId == null){
            throw new RuntimeException("bad request. no session");
        }
        taskService.create(taskCreateReq, AuthUser.of(userId));
        return ResponseEntity.ok().build();
    }

}
