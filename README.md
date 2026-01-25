# ⏱ Event-based Time Tracking System API

사용자의 시간 활동을 세션(Event) 단위로 기록하고 관리하는 백엔드 시스템입니다.  
출퇴근 개념에 한정되지 않고, **시간 기록 · 이력 관리 · 기간 잠금(마감)** 구조를 중심으로 설계된 범용 Time Tracking API 입니다.

> ⚠️ 본 프로젝트는 특정 회사 시스템을 공개한 것이 아니라,  
> 시간 기록(Time Tracking) 도메인을 학습 및 설계 연습 목적으로 재구성한 개인 프로젝트입니다.

---

## 🎯 Project Goals

- 시간 기록 도메인의 데이터 모델링 경험
- 이력(History) 관리 구조 설계
- 데이터 정합성을 고려한 트랜잭션 처리
- 기간 단위 데이터 잠금(마감) 로직 설계
- 조회 성능을 고려한 인덱스 전략 적용

---

## 🛠 Tech Stack

| Category | Tech |
|---------|------|
| Language | Java 17 |
| Framework | Spring Boot |
| DB | PostgreSQL |
| Persistence | MyBatis |
| Build Tool | Gradle |
| API Docs | Swagger (SpringDoc) |
| Version Control | GitHub |

---

## 🏗 Architecture

