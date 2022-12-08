# 프록시와 연관관계 관리

## 1.프록시
### 프록시가 뭘까?
1) em.find()   
-> 데이터베이스를 통해서 실제 엔티티 객체 조회
2) em.getReference()   
-> 데이터베이스 조회를 미루는 가짜 (프록시) 엔티티 객체 조회
   (db에 쿼리가 날라가지 않음)
   
### 프록시 특징
- 실제 클래스를 상속 받아서 만들어짐
- 실제 클래스와 겉 모양이 같다.
    - hibernate가 내부적으로 여러 프록시 라이브러리를 사용하여 해주는 동작임
    
### 프록시 객체의 초기화
```java
Member member = em.getReference(Member.class, "id1");
membr.getName();
```
member.getName();을 호출하는 시점에, MemberProxy에 name이 없기 때문에 프록시가 영속성 컨텍스트에 초기화 요청을 한다.   
영속성 컨텍스트는 초기화 요청을 받으면 DB를 조회한다.   
DB 조회 후, 실제 Entity(Member)를 생성한다.       
그럼 MemberProxy가 실제 Entity에서 name을 가져온다.

### 프록시 특징
- 프록시 객체는 처음 사용할 때 한번만 초기화
- 프록시 객체를 초기화 할 때, 프록시 객체가 실제 엔티티로 바뀌는건 아님
- 영속성 컨텍스트에 찾는 엔티티가 이미 있으면 em.getReference()를 호출해도 실제 엔티티가 반환된다.
- 영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때, 프록시를 초기화하면 문제가 발생한다.


### 2. 즉시 로딩과 지연 로딩
프록시가 활용되는 즉시로딩과 지연로딩이 뭔지 알아보자.
- 지연로딩
  - 지연로딩 LAZY를 사용해서 프록시로 조회가 가능하다.
    ```java
    public class Member {
        @Id
        @Generated
        private Long id;
    
        @Column(name="USERNANE")
        private String name;
    
        @ManyToOne(fetch=FetchType.LAZY) // 지연로딩 설정
        @JoinColumn(name="TEAM_ID")
        private Team tema;
    }
    ```
    Member엔티티 클래스에서 Team 필드를 지연로딩 설정을 해주었다.
    
    ```java
    Member member = new Member();
    member.setUserName("member1");
    member.setTeam(team);
    em.persist(member);
    
    em.flush();
    em.clear();
    
    Member m = em.find(Member.class, member.getId()); // 여기서 쿼리 날라갈 때 team은 조회하지 않는다.
    System.out.println("m = " + m.getTeam().getClass()); // 이제 여기서 team은 proxy로 가져오게 된다.
    m.getTeam().getName(); // 이제 이 시점에 초기화를 하겠지!
    ```
- 즉시로딩
### 3. 지연 로딩 활용
### 4. 영속성 전이: CASCADE
### 5. 고아 객체
### 6. 영속성 전이 + 고아 객체, 생명주기