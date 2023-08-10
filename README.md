### 프로젝트 수행 내용
— 상품 관리를 위한 Rest API 기반 Java Application 개발
- SpringBoot 활용
- 저장소는 RDBMS 사용
- REST API Response Content-Type은 application/json
- Product 애플리케이션을 개발하고, 다음 API를 구성
    - GET - getProduct
    - GET - getProductsByPagination
    - POST - addProduct
    - PUT - updateProduct
    - DELETE - deleteProduct
- Order 애플리케이션을 개발하고, 다음 API를 구성
    - GET - getOrders
    - GET - getOrder
    - POST - orderProduct
    - PUT - changeOrder
    - DELETE - deleteOrder
- REST Client 를 구현하여
    - 커맨드 라인 기반으로 상기 API 시연
        또는
    - 화면을 구성하여 시연
        — 화면을 구성하는 경우, 사용하는 기술은 어떤 기술을 활용해도 무방
- 아래의 개념을 포함해서 구현
    - JPA
    - JpaRepository interface
    - Rest API 설계 (링크)
- 리뷰 시간에 아래의 개념을 설명
    - @SpringBootApplication이 수행하는 역할
    - ComponentScan이란
    - @Autowired 동작 과정
    - Spring Bean LifeCycle
    - RestTemplate
    - @OneToMany, @ManyToOne
- 아래의 개념을 참고
    - Maven
    - OpenJDK 11 ~ 17
    - SpringBoot 2.*

### 프로젝트 수행 계획
- 2023-08-09
    - 프로젝트 수행 내용 분석
    - 참조 자료 읽기
- 2023-08-10
    - RDBMS 선택 및 데이터베이스 모델링
    - Spring Boot 프로젝트 생성
- 2023-08-11
    - 데이터베이스 생성
    - Spring Boot API Controller 구현
- 2023-08-12
    - Spring Boot API Service 구현
    - 커맨드 라인 및 화면 구현
- 2023-08-13
    - 1차 셀프 리뷰
    - 코드 리팩토링
    - 포함 개념 정리
- 2023-08-14
    - 테스트
    - 예외 처리 추가
    - 유효성 검사 추가
- 2023-08-15
    - 2차 셀프 리뷰
    - 코드 리팩토링
    - 프로젝트 수행 내용과 실제 구현 비교
    - 포함 개념 설명 연습
- 2023-08-16
    - 1주차 프로젝트 리뷰

### 프로젝트 리뷰 일정
- 1주차 프로젝트 리뷰
    - 2023-08-16
- 셀프 리뷰
    - 2023-08-13
    - 2023-08-15