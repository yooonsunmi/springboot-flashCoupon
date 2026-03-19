# 🎫 Flash Coupon: 선착순 쿠폰 발급 시스템

Flash Coupon은 대규모 트래픽 환경에서 데이터 정합성을 보장하며 선착순 쿠폰을 발급하고, Spring Batch를 통해 대량의 이력을 관리하는 백엔드 시스템입니다.

---

## 🚀 Key Features
1. 선착순 발급 (동시성 제어)
- Pessimistic Lock: DB 락을 활용하여 수천 명의 동시 요청에도 정확한 재고 차감 보장.
- 중복 체크: 동일 유저의 중복 발급을 차단하여 무결성 유지.

2. 쿠폰 관리 및 마감
- 관리자 기능: 쿠폰명, 총 수량, 유효기간 설정을 통한 쿠폰 발행.
- 상태 마감: 사용/만료된 쿠폰의 수정을 방지하는 데이터 보호 로직.

3. 일괄 처리 (Spring Batch)
- 만료 자동화: 매일 자정 유효기간이 지난 쿠폰을 EXPIRED 상태로 일괄 전환.
- 성능 최적화: Chunk 지향 처리를 통한 대용량 데이터 업데이트 최적화.

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
| **Batch** | Spring Batch      |

---

## 📂 Project Structure

```text
src/main/java/com/yoonsunmi/flashCoupon
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
```

## 🔌 Core API
- POST /api/v1/admin/coupons : [Admin] 선착순 쿠폰 등록
- POST /api/v1/coupons/{id}/issue : [User] 쿠폰 발급 요청 (동시성 처리)
- GET /api/v1/my-coupons : [User] 내 쿠폰 함 조회