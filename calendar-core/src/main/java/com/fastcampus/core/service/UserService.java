package com.fastcampus.core.service;

import com.fastcampus.core.domain.entity.User;
import com.fastcampus.core.domain.entity.repository.UserRepository;
import com.fastcampus.core.dto.UserCreateReq;
import com.fastcampus.core.util.Encryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                    throw new RuntimeException("user already existed!");
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

    @Transactional
    public User getByUserIdOrThrow(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("no user by id."));
    }
}
