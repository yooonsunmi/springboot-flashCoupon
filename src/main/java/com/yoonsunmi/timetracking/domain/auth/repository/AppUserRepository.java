package com.yoonsunmi.timetracking.domain.auth.repository;

import com.yoonsunmi.timetracking.domain.auth.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByLoginId(String loginId);
    boolean existByLoginId(String loginId);
}
