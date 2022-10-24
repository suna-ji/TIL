### Hash Map
#### 설명
map에 저장되는 데이터는 key-value 형식을 가지고 있다.
#### 메서드
put
```java
HashMap<String, String> map = new HashMap<>();
map.put("people", "사람");
map.put("baseball", "야구")
```
get
```java
HashMap<String, String> map = new HashMap<>();
map.get("key값");
// value가 없는 경우 null값이 리턴된다.
```
containsKey
```java
HashMap<String, String> map = new HashMap<>();
map.containsKey("key값");
```
remove
```java
HashMap<String, String> map = new HashMap<>();
map.remove("key");
```
size
```java
HashMap<String, String> map = new HashMap<>();
map.size();
```
keySet
```java
HashMap<String, String> map = new HashMap<>();
map.keySet();
// 모든 key를 모아서 Set의 자료형으로 리턴한다.
```

출처: https://wikidocs.net/208