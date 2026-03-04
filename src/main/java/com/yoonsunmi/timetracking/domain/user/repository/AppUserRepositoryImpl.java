package com.yoonsunmi.timetracking.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yoonsunmi.timetracking.domain.user.entity.AppUser;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.yoonsunmi.timetracking.domain.user.entity.QAppUser.appUser;

@RequiredArgsConstructor
public class AppUserRepositoryImpl implements AppUserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<AppUser> searchUsers(String loginId) {
        return queryFactory
                .selectFrom(appUser) // 이제 정상적으로 인식될 겁니다!
                .where(appUser.loginId.contains(loginId))
                .fetch();
    }
}