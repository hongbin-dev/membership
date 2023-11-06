package me.hongbin.user.service;

import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.hongbin.generic.config.RedisLock;
import me.hongbin.generic.config.Retry;
import me.hongbin.user.domain.User;
import me.hongbin.user.domain.UserRepository;
import me.hongbin.user.dto.UserCreateResponse;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @RedisLock
    public UserCreateResponse save(String name) {
        var user = new User(name);
        var persistedUser = userRepository.save(user);

        return new UserCreateResponse(persistedUser.id(), persistedUser.getName());
    }
}
