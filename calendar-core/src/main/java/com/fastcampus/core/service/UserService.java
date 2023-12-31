package com.fastcampus.core.service;

import com.fastcampus.core.domain.entity.User;
import com.fastcampus.core.domain.entity.repository.UserRepository;
import com.fastcampus.core.dto.UserCreateReq;
import com.fastcampus.core.exception.CalendarException;
import com.fastcampus.core.exception.ErrorCode;
import com.fastcampus.core.util.Encryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final Encryptor encryptor;
    private final UserRepository userRepository;

    @Transactional
    public User create(UserCreateReq userCreateReq) {
        userRepository.findByEmail(userCreateReq.getEmail())
                .ifPresent(u -> {
                    throw new CalendarException(ErrorCode.USER_NOT_FOUND);
                });

        return userRepository.save(new User(
                userCreateReq.getName(),
                userCreateReq.getEmail(),
                encryptor.encrypt(userCreateReq.getPassword()),
                userCreateReq.getBirthday()
        ));
    }

    @Transactional
    public Optional<User> findPwMatchUser(String email, String password) {
        return userRepository.findByEmail(email)
                .map(user -> user.isMatch(encryptor, password) ? user : null);
    }

    public User getOrThrowById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new CalendarException(ErrorCode.USER_NOT_FOUND));
    }

    public User findByUserId(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

    }

}
