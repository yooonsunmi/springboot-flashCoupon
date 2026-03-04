package com.yoonsunmi.timetracking.domain.user.repository;

import com.yoonsunmi.timetracking.domain.user.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long>, AppUserRepositoryCustom {
    Optional<AppUser> findByLoginId(String loginId);
}
