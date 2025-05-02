### 개발 기간(4/25 ~ 4/27)

---

## 1. API 서비스 구성
- Spring Boot 기반의 REST API 서버
- AWS S3를 이용한 파일 저장
- H2 Database를 통한 메타데이터 저장
- 주요 흐름: 사용자 → API 호출 → 변환 수행 → 결과 파일 및 메타데이터 저장
  
---

## 2. 프로젝트 모듈 구성

```
sia_test (root)
 ├── api     : API 요청 처리 (Controller)
 ├── domain  : 비즈니스 로직 (Service, Repository, Entity, DTO)
 └── global  : 공통 설정
```

---

## 3. API 명세서
- Notion (요청, 응답값 확인가능)
https://succinct-hamburger-40a.notion.site/SIA-_-1e0d2d7a62ad806e86aef61901c3fea7
<img width="983" alt="스크린샷 2025-04-26 오후 11 34 33" src="https://github.com/user-attachments/assets/266a4c7b-2890-4dfd-848f-961ac7887e2c" />

---

## 4. 브랜치 역할 및 네이밍 규칙

| 브랜치명 | 설명 |
| --- | --- |
| main | 메인 브랜치로, 배포 버전을 관리합니다. |
| dev | 개발용 브랜치로, 기능을 개발하고 통합하는 데 사용됩니다. |
| feature/기능명 | 새로운 기능을 추가하는 데 사용되는 브랜치로, 각 기능의 명칭을 사용하여 생성합니다. |

---

## 5. 커밋 규칙

| 프리픽스(prefix) | 설명 |
| --- | --- |
| feature | 새로운 기능 구현 |
| add | 없던 파일을 생성함, 초기 세팅 |
| refactor | 코드 리팩토링 |
| fix | 코드 수정 |

