package com.yoonsunmi.timetracking.domain.auth.entity;

import com.yoonsunmi.timetracking.global.config.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "app_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 생성자 캡슐화
@AllArgsConstructor
@Builder
public class AppUser extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String loginId;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default // 빌더 사용 시 기본값 유지
    private Role role = Role.USER;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private boolean isActive = true;

    @Column(length = 50)
    private String teamCode;

    @Column(length = 100)
    private String teamName;

    @Column(length = 50)
    private String position;

    @Column(length = 50)
    private String jobTitle;

    @Column(length = 20)
    private String phoneNumber;

    // 비즈니스 로직에 따른 상태 변경 메서드 (Setter 대신 사용)
    public void deactivate() {
        this.isActive = false;
    }
}