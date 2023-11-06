package me.hongbin.user.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.hongbin.user.domain.User;
import me.hongbin.user.domain.UserRepository;

@Repository
public interface UserJpaRepository extends UserRepository, JpaRepository<User, Long> {
}
