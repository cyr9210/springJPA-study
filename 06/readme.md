### 스프링 데이터 Common: Web 2부: DomainClassConverter

#### 스프링 Converter
- [참고문서](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/convert/converter/Converter.html)

#### DomainClassConverter
- 사용 전
    ```
    @GetMapping("/posts/{id}")
        public String getAPost(@PathVariable Long id) {
            Optional<Post> byId = postRepository.findById(id);
            Post post = byId.get();
            return post.getTitle();
        }
    ```
    
- 사용
    - 해당아이디 값으 찾아서 Domain객체로 변환해준다.
        ```
        @GetMapping("/posts/{id}")
            public String getAPost(@PathVariable(“id”) Post post) {
                return post.getTitle();
            }
        ```

- Formatter
    - 문자열 기반
    - 문자열 -> 다른타입
    - 어떤타입 -> 문자열 프린팅
<br><br>

### 스프링 데이터 Common: Web 3부: Pageable과 Sort 매개변수
#### 스프링 MVC HandlerMethodArgumentResolver
- 스프링 MVC 핸들러 메소드의 매개변수로 받을 수 있는 객체를 확장하고 싶을 때 사용하는 인터페이스
- [참고문서](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/method/support/HandlerMethodArgumentResolver.html)

#### 페이징과 정렬 관련 매개변수
- page: 0부터 시작.
- size: 기본값 20.
- sort: property,property(,ASC|DESC)
- 예) sort=created,desc&sort=title (asc가 기본값)

#### 예제
- 컨트롤러
    ```
    @GetMapping("/posts")
        public Page<Post> getPost(Pageable pageable){
            return postRepository.findAll(pageable);
    }
    ```
- test
    ```
     @Test
     public void getPosts() throws Exception {
         Post post = new Post();
         post.setTitle("jpa");
         postRepository.save(post);
 
         mockMvc.perform(get("/posts/")
                 .param("page", "0")
                 .param("size", "10")
                 .param("sort", "created,desc")
                 .param("sort", "title"))
                 .andDo(print())
                 .andExpect(status().isOk())
         .andExpect(jsonPath("$.content[0].title", is("jpa")));
     }
    ```
<br><br>

### 스프링 데이터 Common: Web 4부: HATEOAS
#### Page를 PagedResource로 변환하기
- 일단 HATEOAS 의존성 추가 (starter-hateoas)
- 핸들러 매개변수로 PagedResourcesAssembler

#### 예제
- 컨트롤러
    ```
     @GetMapping("/posts")
     public PagedResources<Resource<Post>> getPosts (Pageable pageable, PagedResourcesAssembler<Post> assembler){
         return assembler.toResource(postRepository.findAll(pageable));
     }
    ```
- test
    ```
     @Test
     public void getPosts() throws Exception {
         createPost("jpa");
 
 
         mockMvc.perform(get("/posts/")
                 .param("page", "0")
                 .param("size", "10")
                 .param("sort", "created,desc")
                 .param("sort", "title"))
                 .andDo(print())
                 .andExpect(status().isOk())
         .andExpect(jsonPath("$.content[0].title", is("jpa")));
     }
    
     private void createPost(String title) {
         int postcount = 0;
 
         while(postcount <= 100){
             Post post = new Post();
             post.setTitle(title);
             postRepository.save(post);
             postcount++;
         }
    ```
    
- 결과
    ```
       "_embedded":{  
          "postList":[  
             {  
                "id":140,
                "title":"jpa",
                "created":null
             },
    ...
             {  
                "id":109,
                "title":"jpa",
                "created":null
             }
          ]
       },
       "_links":{  
          "first":{  
             "href":"http://localhost/posts?page=0&size=10&sort=created,desc&sort=title,asc"
          },
          "prev":{  
             "href":"http://localhost/posts?page=1&size=10&sort=created,desc&sort=title,asc"
          },
          "self":{  
             "href":"http://localhost/posts?page=2&size=10&sort=created,desc&sort=title,asc"
          },
          "next":{  
             "href":"http://localhost/posts?page=3&size=10&sort=created,desc&sort=title,asc"
          },
          "last":{  
             "href":"http://localhost/posts?page=19&size=10&sort=created,desc&sort=title,asc"
          }
       },
       "page":{  
          "size":10,
          "totalElements":100,
          "totalPages":20,
          "number":2
       }
    }
    ```

<br>

## 스프링 데이터 JPA

### JPA Repository

#### @EnableJpaRepositories
- 스프링 부트 사용할 때는 사용하지 않아도 자동 설정 됨.
- 스프링 부트 사용하지 않을 때는 @Configuration과 같이 사용.
- @Repository 안붙여도 된다. (실제 구현체에서 가지고 있다. 중복임..)

#### @Repository
- SQLExcpetion 또는 JPA 관련 예외를 스프링의 DataAccessException으로 변환 해준다.

### 엔티티 저장하기
    
#### Transient인지 Detached 인지 어떻게 판단 하는가?
- 엔티티의 @Id 프로퍼티를 찾는다. 해당 프로퍼티가 null이면 Transient 상태로 판단하고 id가 null이 아니면 Detached 상태로 판단한다.
- 엔티티가 Persistable 인터페이스를 구현하고 있다면 isNew() 메소드에 위임한다.
- JpaRepositoryFactory를 상속받는 클래스를 만들고 getEntityInfomration()을 오버라이딩해서 자신이 원하는 판단 로직을 구현할 수도 있다.

#### EntityManager.persist()
- [참고문서](https://docs.oracle.com/javaee/6/api/javax/persistence/EntityManager.html#persist(java.lang.Object))
- Persist() 메소드에 넘긴 그 엔티티 객체를 Persistent 상태로 변경한다.

#### EntityManager.merge()
- [참고문서](https://docs.oracle.com/javaee/6/api/javax/persistence/EntityManager.html#merge(java.lang.Object))
- Merge() 메소드에 넘긴 그 엔티티의 복사본을 만들고, 그 복사본을 다시 Persistent 상태로 변경하고 그 복사본을 반환한다.

#### JpaRepository의 save()
- JpaRepository의 save()는 단순히 새 엔티티를 추가하는 메소드가 아님
    - Transient 상태의 객체라면 EntityManager.persist()
    - Detached 상태의 객체라면 EntityManager.merge()
    - **merge / persist 관련 오류 발생이 있을 수 있음으로 언제나 Repository에서 리턴되는 값을 사용하자.**
    ![springjpa](image/image1.PNG);

---
#### 테스트 범위에 따라서 달라지는 결과
- @SpringBootTest
    - postRepository.save()에만 트랜잭션이 적용됨
    - 테스트 내에서는 entitymanager가 객체를 모른다.
- @DataJpaTest
    - 트랜잭션이 테스트 단위이다.
    - postRepository의 오퍼레이션과  EntityManager 모두 한 트랜잭션
    - entitymanager가 객체를 안다.
---


### 쿼리 메소드
#### 쿼리 생성하기
- [참고문서](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation)
- And, Or
- Is, Equals
- LessThan, LessThanEqual, GreaterThan, GreaterThanEqual
- After, Before
- IsNull, IsNotNull, NotNull
- Like, NotLike
- StartingWith, EndingWith, Containing
- OrderBy
- Not, In, NotIn
- True, False
- IgnoreCase

#### 쿼리 찾아쓰기
- 엔티티에 정의한 쿼리 찾아 사용하기 JPA Named 쿼리
    - @NamedQuery
    - @NamedNativeQuery
- 리포지토리 메소드에 정의한 쿼리 사용하기
    - @Query
    - @Query(nativeQuery=true)
<br><br>

### Sort
- **이전과 마찬가지로 Pageable이나 Sort를 매개변수로 사용할 수 있는데, @Query와 같이 사용할 때 제약 사항이 하나 있다.**
- Sort는 그 안에서 사용한 프로퍼티 또는 alias가 엔티티에 없는 경우에는 예외가 발생합니다.
- Order by 절에서 함수를 호출하는 경우에는 Sort를 사용하지 못합니다. 그 경우에는 JpaSort.unsafe()를 사용 해야 합니다.
    - JpaSort.unsafe()를 사용하면 함수 호출을 할 수 있습니다.
    - JpaSort.unsafe(“LENGTH(firstname)”);
<br><br>
### Named Parameter과 SpEL
#### Named Parameter
- @Query에서 참조하는 매개변수를 ?1, ?2 이렇게 채번으로 참조하는게 아니라 이름으로 :title 이렇게 참조할 수 있다.
    ```
    @Query("SELECT p FROM Post AS p WHERE p.title = :title")
    List<Post> findByTitle(@Param("title") String title, Sort sort);
    ```

#### SpEL
- 스프링 표현 언어
- [참고문서](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#expressions)
- @Query에서 엔티티 이름을 #{#entityName} 으로 표현할 수 있습니다.
    ```
     @Query("SELECT p FROM #{#entityName} AS p WHERE p.title = :title")
     List<Post> findByTitle(@Param("title") String title, Sort sort);
    ```
<br>

### Update 쿼리 메소드
#### Update 또는 Delete 쿼리 직접 정의하기
- 객체의 변화를 인지하고 데이터베이스에 동기화 (flush)
- update쿼리를 직접만들일이 별로 없다.
- 하지만 update가 자주 일어나는 경우, 만들어 쓸수 있다.(추천하진 않는다.)
    ```
    @Modifying
    @Query("UPDATE Post p SET p.title = ?2 WHERE p.id = ?1")
    int updateTitle(Long id, String title);
    ```
        
- 추천하지 않는 이유
    - 1차캐싱때문에 Persistent 상태의 객체를 select 하지 않고 그냥 가져온다.(트랜잭션이 끝나지 않았다.)
        ![springjpa](image/image2.PNG)
    - 문제를 해결하려면...
        - clearAutomatically = true
            - 실행 후, Persistent Context에 들어있던 캐쉬를 비워준다.   
            그래야 find할 때,  다시 새로 읽어온다.
        - flushAutomatically = true
            - 실행 전, Persistent Context상태를 flush 한다.   
            데이터 변경사항을 update하기위해
<br><br>

### EntityGraph
- 쿼리 메소드 마다 연관 관계의 Fetch 모드를 설정 할 수 있다.
- 예).. fetch = FetchType.LAZY를 기본으로 하되 필요한 경우에따라 EAGER로 사용하고 싶을때

#### @NamedEntityGraph
- @Entity에서 재사용할 여러 엔티티 그룹을 정의할 때 사용

#### @EntityGraph
- @NamedEntityGraph에 정의되어 있는 엔티티 그룹을 사용 함.
    ![springjpa](image/image4.PNG)
- 그래프 타입 설정 가능
    - (기본값) FETCH: 설정한 엔티티 애트리뷰트는 EAGER 패치 나머지는 LAZY 패치.
    - LOAD: 설정한 엔티티 애트리뷰트는 EAGER 패치 나머지는 기본 패치 전략 따름.
- @NamedEntityGraph 정의 되어있지않아도 attributePaths를 설정하여 사용할 수 있다.
    ![springjpa](image/image5.PNG)
    
#### 비교 
![springjpa](image/image3.PNG)
<br>
