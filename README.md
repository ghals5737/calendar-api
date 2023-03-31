# MINICAL

---

## 1.프로젝트 소개
다른 사람들과 친구를 맺어 캘린더를 공유하는 서비스를 제공


[기능]
 1. 친구 요청, 수락, 거절
 2. 친구에게 캘린더 공유
 3. 캘랜더 CRUD
 4. 일정 CRUD
 5. 알림 닫기를 누르면 더이상 보이지 않도록
 6. 회원가입


## 2.프로젝트 아키텍처

### Architecture
![Auto 3 (1)](https://user-images.githubusercontent.com/52785761/221881591-5815e211-1fe3-431a-a056-7b86c0711be0.png)
## 3.ERD
![erd PNG](https://user-images.githubusercontent.com/52785761/219866704-6aebe409-b928-4a03-8b60-28c46bbe87bc.png)
## 4.기술스택
### JAVA11

### SPRING BOOT 2.7.7

- 스프링 부트 스타터의 라이브러리 관리: 라이브러리 버전 간의 충돌 문제 해결
- 자동 구성: condition에 따라 빈이 동적으로 등록
- 외부 설정과 프로필: 검증/운영 환경에 따른 외부 설정, 프로필에 따른 정보 변경 용이
- 액츄에이터: 애플리케이션의 상태 정보와 구성 정보를 확인, 운영중인 서비스의 특정 메서드에 로그레벨 변경 가능

### JUNIT5

<img width="732" alt="스크린샷 2023-03-31 오후 10 43 50" src="https://user-images.githubusercontent.com/89332391/229136991-c00c02a8-88fd-4392-8b32-cab94f270b7b.png">

### JPA

### QueryDsl

### Spring Restdoc

- 테스트 코드를 작성 및 통과하면 API 문서를 자동으로 생성 
- API 문서화의 효율성과 신뢰성 향상 
- Swagger와 비교하면 컨트롤러 단 코드에 어노테이션 등 부가코드가 불필요해져 Restdoc이 깔끔함 
- 하지만 초기 설정 시 스웨거보다 까다롭다는 단점이 있음

<img width="700" alt="스크린샷 2023-03-31 오후 10 37 23" src="https://user-images.githubusercontent.com/89332391/229136051-c19d204e-8db3-4dba-8d66-adb92b9babae.png">




## 5. 트러블슈팅
https://gray-cake-b29.notion.site/84e7f859e35a4ac9abdd37c18fd9be71

<img width="532" alt="스크린샷 2023-03-31 오후 10 22 48" src="https://user-images.githubusercontent.com/89332391/229131826-e208afbf-33d0-4a6b-aa36-49c3739e300e.png">

## 6. 회의록
https://gray-cake-b29.notion.site/5aee5f3dcb374992916b3ae87e467d41

<img width="800" alt="스크린샷 2023-03-31 오후 11 04 35" src="https://user-images.githubusercontent.com/89332391/229142476-dd5737a3-e833-41c1-aeb9-6508c3c13f47.png">

