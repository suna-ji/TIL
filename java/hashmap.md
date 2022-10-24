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

#### 정렬
1. TreeMap을 사용하여 정렬   
TreeMap은 sortedMap 인터페이스를 상속받는 클래스이다.   
comparator에서 문자열을 비교하는 경우 compareTo() 메서드가 실행된다.   
이 메서드는 문자열을 사전순으로 비교하고 오름차순으로 정렬한다.   
```java
Map<String, String> sortedMap = new TreeMap<>(map);
```
2. 직접 정의   
직접정의한 comparator를 사용하여 정렬할 수 있다.
```java
Map<String, String> sortedMap = new TreeMap<>(new Comparator<String>() {
    @Override
    public int compare(String o1, String o2) {
       
    }
})
sortedMap.putAll(map);
```   

출처: https://developer-talk.tistory.com/395