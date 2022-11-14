# lombok
## @NoArgsConstructor
### 의문점
자바에서 만들어주는 기본 생성자와 롬복의 @NoArgsContructor를 사용해서 만드는 기본 생성자의 차이가 뭘까?

### @NoArgsConstructor 사용하는 이유
#### 1. JPA의 Entity 클래스 정의
Entity 클래스는 전체 필드를 대상으로 하는 생성자가 필요해서 @AllArgsContructor 어노테이션이 꼭 필요하다.   
근데, @AllArgsContructor만 선언하는 경우 컴파일러는 전체 필드를 가지는 생성자를 우선적으로 생성하기 때문에 기본 생성자 호출이 필요할때도 전체 필드를 가지는 생성자가 호출된다.
따라서 이때는  @AllArgsContructor 어노테이션이 꼭 필요하다.   
또한, Jpa에서는 Lazy Loading을 통해서, 객체를 프록시 형태로 조회하는데 프록시 객체를 초기화하기 위해 부모 객체, 즉 엔티티의 NoArgsConstructor를 호출한다,
따라서 NoArgsConstructor가 필요하다.

#### 2. 접근제어 
```java
@NoargsConstructor(AccessLevel.PROTECTED)
```

### 결론
그럼 Entity 아니고 단순한 dto클래스의 경우 직접 정의한 다른 생성자도 없는데, 접근제어 목적도 없이 @NoArgsContructor 어노테이션 딱 하나만 쓰는건 잘못된거라고 할 수 있는건가..?


출처   
https://velog.io/@mincho920/Spring-NoArgsConstructor
https://yeon-kr.tistory.com/176
https://stackoverflow.com/questions/27654167/difference-between-a-no-arg-constructor-and-a-default-constructor-in-java