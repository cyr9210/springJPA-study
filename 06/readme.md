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


    
