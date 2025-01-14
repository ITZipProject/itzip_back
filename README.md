## 📽️ 프로젝트 개요

### Software Architecture
<img width="2199" alt="software architecture" src="https://github.com/user-attachments/assets/68c3ee1a-17b5-4898-9c82-7fc221990bbb" />

### ERD - 다이어그램
![entityManagerFactory(EntityManagerFactoryBuilder, PersistenceManagedTypes)](https://github.com/user-attachments/assets/0e4f14c5-9ae5-41bc-8b56-4a78860eb4b9)

### 💡 아이디어명
**프로덕트 이름** : ITZip

### 웹 사이트 URL : [취준생사이트](https://itzip.co.kr)

### 📝 아이디어 계기
- **기획 배경** : **효율적인 개발자 취업 준비 플랫폼** <br/>
개발자 취업 준비 과정에서 느꼈던 여러 불편함을 해결하고, 더 나은 경험을 제공하기 위해 이 플랫폼을 개발했습니다.


### 📄 아이디어 설명
- **설명** : 이 플랫폼은 알고리즘 학습, 이력서 작성, 채용 공고 검색 등 취업 준비의 핵심 요소를 한 곳에서 관리할 수 있도록 설계되었습니다.


### 🪄 기대 효과
- **설명** : 사용자가 효율적으로 취업을 준비할 수 있도록 불편한 점을 개선하고, 취업 성공까지 전 과정을 지원하는 것을 목표로 하고 있습니다.

## 🛠️ 기술 스택

### 💻 백엔드

- **JWT** : Spring Security를 활용하기 위한 수단으로 사용한다.

- **OAuth2** : 추후 기입 예정

- **Redis** : 키-값 구조의 토큰을 저장하는데 적합하고, 사용자 인증, 인가 토큰에 대한 접근성을 위해 채택한다.

- **MongoDB** : 추후 기입 예정

- **PostgreSQL** : 오픈소스이면서 호환성 및 유연성이 좋고, 대량의 데이터를 처리하는 성능이 뛰어나기 때문에 채택한다.

- **ElasticSearch** : 학교 데이터를 인덱싱을 통하여 검색어 최적화를 위해 채택하였다.

- **Java** : 자바 21버전 채택하였다. 

- **Spring Boot** : 스프링 부트는 대중적이면서도 확장성이 좋아 이후 고도화 단계까지 고려해서 채택한다.

- **Spring Security** : 인증, 권한 관리, 데이터 보호 등 인가, 보호 관리를 위해 채택한다.

<br/>

## 📲 CI/CD 및 배포

- **Gradle** :  추후 기입 예정

- **GitHub** : 버전 관리 및 기능 개발 협업을 위해 채택한다.

- **GihubActions** :  깃허브 액션을 통하여 CI/CD를 구축을 위하여 채택하였다. 

- **Docker** :  Docker, Docker-Compose 사용하여 Nginx, Certbot, LetsEncrypt, Backend, Front 도커 컨테이너 생성하여 관리하기 위해 채택하였다. 

- **Nginx** :  추후 기입 예정

- **AWS(Amazon Web Services)** : 클라우드 컴퓨팅 플랫폼으로, 서버 배포 및 이미지 저장소로 활용한다.

<br/>

## 모니터링 도구

- **Prometheus** :  추후 기입 예정

- **Grafana** :  추후 기입 예정

- **Sentry** :  추후 기입 예정

<br/>

## ⌨️ 개발 도구

- **IntelliJ IDEA** : Spring Boot Project, Java, Html, CSS, JavaScript 파일 작성한다.

- **Visual Studio Code** : md, sql, docker.yml 등 파일 작성한다.

<br/>

## 📮 협업 도구

- **Slack** : 프론트, 백엔드 실시간 회의록 작성 및 내용을 공유한다.

- **Discord** : 팀원들과의 실시간 음성대화, 파일전송 기능, GihubActions CI/CD 성공 or 실패 메시지기능 활용한다. 

- **Notion** : 비지니스 로직 및 아키텍쳐 설계를 공유한다.

<br/>

## 📜 문서 도구

- **ERD Cloud** :  [ERD 사이트](https://www.erdcloud.com/d/uBYB6CmA26fGitZtY)

- **Swagger** :  [swagger 사이트](http://3.39.78.0:8080/api/swagger-ui/index.html)

<br/>

## 👥 업무 분담

- **한승균** : ERD , 이력서, 공통파일 업로드, 초기 환경 구축, 채용 공고, 테스트 코드 Wiki,  깃허브 이슈 Wiki 문서 작성

- **김채민** : ERD , 회원 , Security, OAuth2.0, Wiki 깃허브 작성, CORS 오류 설정, JWT

- **신효승** : ERD , 세부 인프라 구축, Blog ,랭킹 시스템 (예정)

- **박석원** : ERD, 공통 응답 처리, 스웨거, 알고리즘

<br/>

## 📏 그라운드 룰
- **네이밍 규칙** :
    - **Class** : PascalCase
    - **Variable** : camelCase
    - **Constant** : UPPER_CASE

- **들여쓰기:** 4 Space

- **Git 브랜치 전략** : GitHub-Flow

- **커밋 메시지 규칙** : commit 컨벤션 Page wiki 참고

<br/>
