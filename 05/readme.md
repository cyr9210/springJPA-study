### 스프링 데이터 Common: Web 1부: 웹 지원 기능 소개

#### 제공하는 기능
- 도메인 클래스 컨버터
- @RequestHandler 메소드에서 Pageable과 Sort 매개변수 사용 
- Page 관련 HATEOAS 기능 제공
    - PagedResourcesAssembler
    - PagedResoure
- Payload 프로젝션
    - 요청으로 들어오는 데이터 중 일부만 바인딩 받아오기
    - @ProjectedPayload, @XBRead, @JsonPath
- 요청 쿼리 매개변수를 QueryDSLdml Predicate로 받아오기 (위험해보임.. 권장하지 않는다.)
    - ?firstname=Mr&lastname=White => Predicate

