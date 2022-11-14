### HashTable
#### 상황
scanId별로 returnUrl을 저장했다가, 웹훅 요청이 들어오면 해당 scanId에 맞는 returnUrl에 callback을 날려줘야 했다.
이때 scanId별로 returnUrl을 저장하는 자료구조가 필요했다.

먼저 생각났던건 hashMap이였다.
근데 hashMap이 동기화가 되나? 싶었다.
그래서 HashMap 동기화를 찾아보기 시작했고 HashTable을 알게되었다.

#### HashTable 특징
- 동기화된다.
- key-value 쌍을 저장한다.
- key가 hash된다.


#### HashTable vs HashMap
- HashMap과 HashTable은 동일하지만, HashTable은 동기화 된다는 특징이 있다.
- HashMap은 value에 null을 입력할 수 있지만, HashTable은 입력할 수 없다.


#### 예제
```java
import java.util.Hashtable;

Hashtable<Integer, String> hashTable = new Hashtable<Integer, String>();
hashTable.put(68, "http://0.0.0.0/scanResult");
hashTable.get(68);
```

#### 출처
https://www.geeksforgeeks.org/hashtable-in-java/