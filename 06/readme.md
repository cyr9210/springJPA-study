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



