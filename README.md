# ⏱ Event-based Time Tracking System API

사용자의 시간 활동을 세션(Event) 단위로 기록하고 관리하는 백엔드 시스템입니다.  
단순한 출퇴근 기록을 넘어 **시간 기록 · 이력 관리 · 기간 잠금(마감)** 구조를 중심으로 설계된 범용 Time Tracking API입니다.
---

## 🎯 Project Goals

- JPA(Spring Data JPA)를 활용한 객체 지향적 도메인 모델링 및 엔티티 설계
- Querydsl을 도입하여 컴파일 타임 타입 체크가 가능한 동적 쿼리 구현 및 코드 기반의 쿼리 작성
- Dirty Checking 및 Auditing을 활용한 데이터 변경 이력(History) 자동화
- 데이터 정합성을 위한 트랜잭션 격리 수준 및 낙관적/비관적 락(Locking) 활용
- 대용량 시계열 데이터 조회를 고려한 Index 최적화 및 페이징 처리

---

## 🛠 Tech Stack

| Category | Tech                                    |
| :--- |:----------------------------------------|
| **Language** | Java 17                                 |
| **Framework** | Spring Boot 3.3.4                       |
| **DB** | MySQL 8.0                               |
| **Persistence** | Spring Data JPA, Querydsl 5.1 (Jakarta) |
| **Build Tool** | Gradle 8.11.1                           |
| **API Docs** | Swagger (SpringDoc)                     |
| **Security** | Spring Security, JWT (jjwt 0.12.5)      |

---

## 📂 Project Structure

```text
src/main/java/com/yoonsunmi/timetracking
├── domain
│   └── user
│       ├── api          # Controller (진입점)
│       ├── application  # Service (비즈니스 로직)
│       ├── dto          # Request/Response 객체
│       ├── entity       # AppUser 엔티티 및 Role Enum
│       └── repository   # JPA + Querydsl Custom Repository
└── global
    ├── config           # Security, Querydsl, JPA Audit 설정
    ├── entity           # BaseTimeEntity (MappedSuperclass)
    ├── exception        # GlobalExceptionHandler
    └── security         # JwtProvider, AuthFilter