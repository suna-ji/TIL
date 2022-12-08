# 스프링 DB 접근 기술

## DataSource?
- DB 커넥션을 획득하는 방법을 추상화 하는 인터페이스.
- DB와 관계된 커넥션 정보를 담아서 빈으로 등록이 되며, 스프링은 해당 dataSource bean에 접근하여 DB와의 연결을 획득한다.

## DataSource 역할
- DB 서버와의 연결
- DB Connection pooling 
    - 일정량의 connection 객체를 미리 만들어 저장해두었다가 꺼내 쓰는것
    - 커넥션풀을 관리하고 커넥션 객체를 풀에서 가져다가 쓰고 반납하는 일련의 과정을 DataSource가 해준다.

## DataSource를 설정하고 빈에 등록, 주입하는 방법
- DB와의 연결을 위해 DB서버 정보를 properties파일에 설정한다.
- 설정한 property file을 통해 DataSource를 빈으로 등록한다.
- 생성된 datasource빈을 spring jdbc에 주입한다.


## JDBC?
- DB에 접근할 수 있도록 JAVA에서 제공하는 API (Java Database Connectivity)
    - 데이터베이스에 쿼리를 날리는 방법을 제공
    - jdbc를 사용하면 데이터베이스에 비종속적인 db연동 로직을 구현할 수 있다.
        - 어떤 db를 사용하는지에 관련없이 jdbc api가 여러 db driver와 호환할 수 있는 인터페이스를 제공해준다.
    
## Tmi
이전 프로젝트를 진행할 때 db로 sqlite를 사용했었다.
application properties에 datasource 설정값 적어줘서 bean이 생성됐을거라고 생각했는데 주입이 안되는 문제가 있었다.
찾아보니 스프링부트는 sqlite에 대해서는 Datasource bean을 명시해야 한다고 한다.
근데 Also, since Spring Boot doesn't provide configuration support for SQLite database out of the box, we also need to expose our own DataSource bean:
라고 나와있었다.
https://www.baeldung.com/spring-boot-sqlite

[링크](https://esoongan.tistory.com/164)