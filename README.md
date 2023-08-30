# 👫 하나메이트

## 👥 Team Members

|Role | Name | Email                 | Github                                        |
|-----|------|-----------------------|-----------------------------------------------|
|Leader| 민새미  | msw4585@gmail.com     | [Github Link](https://github.com/petcu1004)   |
|Member| 권민선  | jms393497@gmail.com   | [Github Link](https://github.com/helloalpaca) |
|Member| 최안식  | choiansik98@naver.com | [Github Link](https://github.com/Ansix1207)   |

<br>

## 📄 Project Description
아이들을 위한 맞춤형 금융 교육 서비스입니다. 부모-아이 역할 기반으로 상호작용을 통해서 금융을 학습할 수 있습니다.

<br>

## 💻 기술 스택 🛠️

### 개발 환경

| IDE                                                                                                            | JDK                                                                                                      | Build                                                                                                  |
|----------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------|
| <img src="https://img.shields.io/badge/IntelliJ-000000?style=for-the-badge&logo=IntelliJIDEA&logoColor=white"> | <img  src="https://img.shields.io/badge/JDK 11-007396?style=for-the-badge&logo=Java&logoColor=white"> | <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white"> | 

### Frontend

| HTML                                                                                                 | CSS                                                                                               | JavaScript                                                                                                     | REACT                                                                                        |
|------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------|
| <img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=HTML5&logoColor=white"> | <img src="https://img.shields.io/badge/CSS-1572B6?style=for-the-badge&logo=css3&logoColor=white"> | <img src="https://img.shields.io/badge/Javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=white"> | <img src="https://img.shields.io/badge/-ReactJs-61DAFB?logo=react&logoColor=white&style=for-the-badge"> |

| REDUX                                                                                             | 
|---------------------------------------------------------------------------------------------------| 
| <img src="https://img.shields.io/badge/Redux-593D88?style=for-the-badge&logo=redux&logoColor=white"> |

### Backend

| Java                                                                                               | SpringBoot                                                                                             | SpringSecurity                                                                                                           |
|----------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------|
| <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=Java&logoColor=white"> | <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> | <img src="https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white"> |

### DevOps

| AWS                                                                                                     | RDS                                                                                                | Redis                                                                                                 |
|---------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------|
| <img src="https://img.shields.io/badge/AWS-232F3E?style=for-the-badge&logo=Amazon AWS&logoColor=white"> | <img src="https://img.shields.io/badge/RDS-232F3E?style=for-the-badge&logo=Amazon AWS&logoColor=white"> | <img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white"> |

### 협업

| GIT                                                                                                | Notion                                                                                                 | Swagger                                                                                                       |
|----------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------|
| <img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white">   | <img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white"> | <img src="https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white"> |

<br>

## 📂 Folder Structure

전체적으로 DDD(도메인 주도 개발)의 아이디어를 차용해서 폴더 구조를 잡았습니다.

- Controller: 사용자의 요청을 처리하고, 해당 요청에 따른 적절한 동작을 수행한 후, 응답을 생성합니다. 요청은 URL, HTTP 메소드, 헤더, 쿠키, 요청 본문 등을 포함할 수 있습니다.

- Service: 비즈니스 로직을 담당합니다. Service는 Controller에 의해 호출되어 실제로 데이터를 처리하고, DAO를 통해 데이터를 저장, 수정, 삭제, 조회합니다. 이러한 연산을 수행하는 로직이 포함됩니다.

- Repository : 데이터베이스 접근 로직을 담당합니다. Repository는 Service에 의해 호출되며, 데이터베이스에 직접 접근하여 데이터를 CRUD(생성, 읽기, 업데이트, 삭제)하는 역할을 합니다.

- DTO: 애플리케이션의 데이터를 담는 객체입니다. 이들은 복잡한 비즈니스 로직을 수행하는데 필요한 정보를 담기도 하고, 프론트엔드와 소통할 때 사용하기도 합니다.

- resources: 환경설정 파일들이 저장되는 디렉터리입니다. 데이터베이스 연결정보, 보안설정 등이 포함될 수 있습니다.

```
├── main
│   ├── java
│   │   ├── com
│   │   │   ├── domain                    # 로직을 기능 단위별로 묶은 패키지
│   │   │   │   ├── controller            # 사용자의 요청 처리 및 응답을 담당하는 컨트롤러들
│   │   │   │   ├── service               # 비즈니스 로직을 담당하는 서비스 클래스들
│   │   │   │   ├── dto                   # DTO (Data Transfer Object) 클래스들
│   │   │   │   ├── repository            # 데이터베이스 접근을 담당하는 코드들
│   │   │   ├── entities                  # DB 엔티티를 모아놓은 폴더
│   │   │   ├── global                    # 에러 처리, 핸들러, Config 파일들을 모아놓은 폴더
│   │   │   ├── infra                     # 프로젝트 배포를 위한 파일
│   │   │   ├── jwt                       # JWT 기반 인증/인가 시스템을 구현하기 위한 로직, 필터
│   │   │   ├── security                  # 스프링 시큐리티와 관련된 로직
│   ├── resources
│   │   ├── application.properties             # 프로젝트 실행시 필요한 정보를 저장
│   │   ├── application-S3.properties          # AWS S3 연결 정보를 저장
│   │   ├── data.sql                           # 테스트를 위한 초기 데이터
└── README.md                            # 프로젝트에 대한 설명이 적힌 README 파일
```

<br>

## 🌐 배포환경

![image](https://github.com/HANAMATE/Hanamate_Server/src/main/resources/readme/architecture.png)
<br>

<br>

## 🗄️ 데이터베이스 모델링

![image](https://github.com/HANAMATE/Hanamate_Server/src/main/resources/readme/erd.png)
<br>

## 🎨 UI
![image](https://github.com/HANAMATE/Hanamate_Server/src/main/resources/readme/ui.png)

<br>
