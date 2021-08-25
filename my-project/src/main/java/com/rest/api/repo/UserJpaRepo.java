package com.rest.api.repo;

import com.rest.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 회원가입 시 이메일 조회를 위한 선언
public interface UserJpaRepo extends JpaRepository<User, Long> {
    Optional<User> findByUid(String email);
}
