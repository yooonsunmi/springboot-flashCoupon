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

| Category | Tech                |
|---------|---------------------|
| Language | Java 17             |
| Framework | Spring Boot 3.3.4   |
| DB | PostgreSQL          |
| Persistence | MyBatis             |
| Build Tool | Gradle 8.14.3       |
| API Docs | Swagger (SpringDoc) |
| Version Control | GitHub              |

---

## 🏗 Architecture
Controller → Service → DAO → DB
- 계층형 구조로 설계하여 **비즈니스 로직과 DB 접근 분리**
- 트랜잭션 단위 서비스 로직 처리
- 도메인 중심 설계로 유지보수성과 확장성 고려

---

## 🚀 Main Features

### 1️⃣ Time Session Management
- 사용자의 시간 활동을 **세션 단위(Start ~ End)** 로 기록
- 하루 단위 시간 기록 조회 가능

### 2️⃣ Event-based Time Recording
- 출근/퇴근에 국한되지 않고 **시간 이벤트(Event)** 단위로 기록 가능
- 확장 가능한 이벤트 구조 설계

### 3️⃣ Flexible Schedule Policy
- 사용자별 시간 정책 설정
- 특정 기간 동안 정책 적용 가능

### 4️⃣ Period Lock (마감 기능)
- 특정 기간의 데이터 잠금 처리
- 마감 이후 수정 방지 로직 구현

### 5️⃣ History Tracking
- 데이터 변경 시 History 테이블에 기록 저장
- 변경 이력 추적 가능 구조

---

## 🗄 Database Design

### 주요 테이블

| Table | Description |
|-------|-------------|
| `user` | 사용자 정보 |
| `work_session` | 시간 활동 세션 기록 |
| `time_event` | 세션 내 이벤트 기록 |
| `schedule_policy` | 시간 정책 설정 |
| `period_lock` | 기간 잠금 정보 |
| `work_session_hist` | 세션 변경 이력 |

### 설계 포인트

- **Event 기반 시간 기록 구조**
- 이력 테이블 분리 설계
- 기간 잠금(마감) 구조로 데이터 무결성 보장
- 기간 조회 성능을 고려한 인덱스 설계

---

## 📌 API Example

### 시간 세션 등록

**POST** `/api/sessions`

```json
{
  "userId": 5,
  "startTime": "2026-01-25T09:00:00",
  "endTime": "2026-01-25T18:00:00"
}

