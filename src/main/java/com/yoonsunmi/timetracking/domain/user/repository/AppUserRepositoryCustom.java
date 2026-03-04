package com.yoonsunmi.timetracking.domain.user.repository;

import com.yoonsunmi.timetracking.domain.user.entity.AppUser;

import java.util.List;

public interface AppUserRepositoryCustom {
    List<AppUser> searchUsers(String nickname);
}