```roomsql
CREATE TABLE TP_CITY_TRAVEL
(
    TRAVEL_ID BIGINT NOT NULL,   
    CITY_ID BIGINT NOT NULL,    
    FOREIGN KEY (TRAVEL_ID) REFERENCES TP_TRAVEL(TRAVEL_ID),
    UNIQUE (TRAVEL_ID, CITY_ID)
);
```
### FOREIGN KEY 제약조건?
두 테이블 사이의 관계를 선언함으로써 데이터의 참조 무결성을 보장해준다.
### 참조 무결성?
부모 데이터의 삭제 또는 업데이트로 인해 참조가 불가능한 경우를 만들지 않도록 보장하는 무결성

### FOREIGN KEY 제약조건 사용
```roomsql
ADD FOREIGN KEY (자식 테이블 컬러명) REFERENCES 부모 테이블명 (부모 테이블의 컬럼명)
ON UPDATE 옵션 ON DELETE 옵션;
```
### FOREIGN KEY 옵션
1) On Delete   
   Cascade : 부모 데이터 삭제 시 자식 데이터도 동시 삭제.     
   Set null : 부모 데이터 삭제 시 해당되는 자식 데이터의 Column은 null로 처리     
   Set Default : 부모 데이터 삭제시 자식 데이터의 Column은 기본값으로 Update  
   Restrict : 자식 테이블에 데이터가 남아있는 경우 부모 테이블의 데이터는 삭제 불가   
   

2) On Update   
   Cascade : 부모 데이터 수정 시 자식 데이터도 동시 수정.   
   Set null : 부모 데이터 수정 시 해당되는 자식 데이터의 Column은 null로 처리   
   Set Default : 부모 데이터 수정시 자식 데이터의 Column은 기본값으로 Update
   Restrict : 자식 테이블에 데이터가 남아있는 경우 부모 테이블의 데이터는 수정 불가


[링크](https://congi.tistory.com/entry/Foreign-Key-%EC%84%A4%EC%A0%95-%EB%B0%A9%EB%B2%95-%EB%B0%8F-%EC%98%B5%EC%85%98-%EC%84%A4%EB%AA%85)