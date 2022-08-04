# studyHelper
Web project CRUD & RESTapi

#VERSION 1.0

##CRUD
1. 회원 정보 등록 및 삭제
2. 회원의 글 등록 및 삭제
3. 회원의 친구 등록 및 삭제

##채팅 시스템 구현
1. 접속자끼리 자유로운 채팅 구현

##개인 정보 수정 기능
1. 각 회원은 자신만의 프로필 이미지와 상태 메세지를 설정할 수 있음


#추가/수정 해야할 것들

1. 채팅화면 모바일에 맞게 수정하기
2. 채팅 시 화면 자동으로 올라가게 만들기
3. 회원정보 수정 시 오류 있음
4. 회원의 memberId와 id를 구분해서 memberId 수정 기능 넣기
5. 채팅서버 접속 시 memberId값으로 들어가게 만들기
6. 채팅서버 접속 시 개인의 프로필이 보이도록 수정하기
7. 채팅화면에서 본인 메세지는 우측, 상대방 메세지는 좌측에서 보이도록 수정하기
8. 친구 글 조회 시 본인 글로 돌아가지 못함
9. 친구 글 조회 시 누구의 글을 읽고 있는지 표시기능 추가


# Study Helper 문서


리액트와 스프링부트로 개발을 진행


## 220622

> 리액트와 스프링부트 연동

- 개발 환경 세팅
- 스프링부트 프로젝트에 리액트 설치
- cors 관련 오류를 방지하기 위한 proxy 설정 - package.json 에 proxy 를 어떻게 설정할 것인지 작성
- 빌드 시 react 프로젝트가 먼저 build 되고, 결과물을 SpringBoot 프로젝트 build 결과물에 포함시킨다는 스크립트를 build.gradle 에 추가
#### 참고자료
- https://velog.io/@jung-co/Spring-boot-%EC%99%80-React-%EC%97%B0%EB%8F%99%ED%95%98%EA%B8%B0

> database 설계

- users
- groups
- group_members
- schedules

ERDiagram 


> jpa 설정해주기

인프런 강의보기 (일단 기본 강의 시청 후 -> 조금씩 jpa 강의 완강)

> Spa 이해하기

location.hash와 HTML5의 history API를 통해서 예전 웹페이지 처럼 논리적으로 페이지를 분리하고 그 분리된 페이지를 이동하는 것이 가능하다
Hash는 URI에서 #으로 시작하는 문자열을 의미하는데 정확히는 Fragment Identifier라고 한다.
=======


# 방학 기념 업데이트 하기

1. 기존 html,css,js 를 리액트로 convert 하기
2. jdbc -> jpa 업데이트하기
3. 여러가지 기능 추가 및 코드 변경
4. 앱의 의미 재설정 
- 변경 전  : 사용자별로 스케줄을 추가하고 친구끼리 그 스케줄을 공유하는 것
- 변경 후  : 단체 팀방을 만들어서 to-do-list를 공유하고 서로 확인이 가능함, 또한 팀방에서 채팅을 할 수 있음.





# 구현 기능 종류

1. 회원가입 
2. 로그인
3. 채팅
4. 사진 업로드
5. 프로필 사진
6. ...


# Domain
- Member
- Friend
- Group
- GroupMember
- Notice



# 2022 08 04 - 현재까지 작동하는 기능

1. 로그인
2. notice 조회
3. notice 작성 및 수정

### 추가해야 할 것

1. 팀 조회
2. 팀 검색
3. 팀 참가
4. 팀 탈퇴
5. 팀 생성 및 삭제

6. 팀원 글 수정 권한 
7. 팀 글 수정 기능




