## 1. java에서의 Callback
### 상황
브로커 서버를 만들면서 callback을 사용해야 할 상황이 생겼다.   
브로커 서버는 구조는 다음과 같다.   
resolver <-> 서비스 <-> command 모듈   
api 요청이 들어오면 resolver에서는 scanId와 projectKey를 응답으로 보내줘야 하고,    
서비스에서는 비동기로 분석을 진행하여야 한다.   
분석이 얼마나 걸릴지 모르기 때문이다.   
command 모듈에서는 실제로 분석 cli를 실행하게 된다.   
리졸버가 바로 응답으로 보내줘야 할 scanId는 command 모듈을 실행했을 때 로그에 찍히는 정보에서 가져와야 했다. 
```shell
분석 시작---
분석 ID: 20
진행중
분석이 완료되었습니다.
```
대충 이런 로그들이 찍히는데 여기서 분석 Id를 가져와야 했다.
리졸버는 해당 로그가 찍히는 시점을 알 수 없지만, 찍히는 시점에 바로 응답을 보내야 해서 callback 함수가 꼭 필요했다.
### 구현
resolver(caller) <-> command 모듈 (Callee)   
-> Command 모듈에서 Resolver위에 작동하는 메서드를 호출하는 것!

1) Calle(Command모듈)에서 콜백 인터페이스랑 메서드를 선언하자.
```java
Command.java
public class Command {
    
    private CommandCallback commandCallback;
    
    public interface CommandCallback { // 1) 콜백 인터페이스, 메서드 선언
        public void getScanId(Command command) throws ExecutionException, InterruptedException;
    }
    
    public void setCommandCallback(CommandCallback commandCallback) { 
        this.commandCallback = commandCallback;
    }
    ...

    RuntimeOutputStream runtimeOutputStream = new RuntimeOutputStream() {
        @Override
        public void print(String line) throws ExecutionException, InterruptedException {
            if (line.contains("분석 ID :")) {
                scanId = Integer.parseInt(line.substring(8));
                commandCallback.getScanId(ScaCommand.this); // 2) Calle에서 호출
            }
            System.out.println(line);
        }
    };

}

```

3) Caller인 resolver에서 콜백 인터페이스를 구현하고 Callee 객체에 등록하자.
```java
resolver.java
public class resolver {
    
    public resonseDto performAnalysis() {
        command.setCommandCallback(new Command.setCommandCallback() {
            @Override
            public void getScanId(Command command) {
                logger.info("resolver gets sca scanID from Command");
                scanId = command.getScanId();
                countDownLatch.countDown();
                System.out.println("resolver gets sca scanID from Command => " + scanId);
            }
        });
    }
}
```
### 출처
https://devsophia.tistory.com/entry/JAVA-%EC%BD%9C%EB%B0%B1-%EB%A9%94%EC%84%9C%EB%93%9C-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0


## 2. CountdownLatch
### 상황
command모듈에서 scanId를 얻는 시점에 리졸버에서도 scanId를 받을수 있게 되었는데, 문제는 scanId를 받기전에 리졸버거 리턴한다는것이였다.   
이 문제를 해결하기 위해서 생각한 방법은 3가지 정도가 있었다.
1. while문   
   가장 쉽게 해결되겠지만 이건 좋은 방법이 아니라고 생각했다.
2. Future   
   3번에서 작성하겠지만 시도해봤지만 실패했다.
3. CountdownLatch   
### 설명
CountdownLatch클래스는 다른 클래스가 작업을 완료할때까지 스레드를 block하기 위해 사용되는 클래스이다.  
CountdownLatch는 카운터 필드를 가지고 있고, 원하는 만큼 감소시킬 수 있다. 
해당 카운터 필드가 0이 될때까지 스레드는 블락된다.

### 구현
```java
resolver.java
public class resolver {
    
    public resonseDto performAnalysis() {
        CountDownLatch countDownLatch = new CountDownLatch(1); // 1) CountdownLatch 객체 생성 
        command.setCommandCallback(new Command.setCommandCallback() {
            @Override
            public void getScanId(Command command) {
                logger.info("resolver gets sca scanID from Command");
                scanId = command.getScanId();
                countDownLatch.countDown(); // 2) callback 실행되는 시점에 1감소
                System.out.println("resolver gets sca scanID from Command => " + scanId);
            }
        });
        countDownLatch.await(); // 3) callback이 완료될때까지 대기
        responseDto.setScanId(scanId); // 4) scanID 담아서 리턴
        return responseDto;
    }
}
```
### 출처
https://www.baeldung.com/java-countdown-latch

## 3. Java Future
### 설명
- 비동기적 연산 또는 작업을 수행한 후 도출된 결과를 나타내는 것
- 다른 스레드에서 리턴한값을 메인 스레드에서 받고싶을 때 사용
- java 1.5
### 예제
```java
ExecutorService executorService = Executors.newCachedThreadPool(); 
Future<String> future = executorService.submit(() -> {
   new Callable<String>() {
       public String call() {
           return "ddd";
        }
  }
});
future.get(); // return 될때까지 기다림
```
### 적용하지 못한 이유
Callable에 넣을 내용은 scanId를 받아오는 부분이였는데,    
리졸버에서는 콜백 인터페이스를 구현해서 Callee에 객체 등록하는 코드를 통해 scanId를 받아오기 때문에 Callable에 등록할 수 있는 내용이 아니였다.   
### Callable vs Runnable
Thread는 Runnable과 Callable의 구현된 함수를 수행한다는 공통점이 있다.   
Callable은 Runnable의 개선된 버전으로, 반환값과 Exception을 추가할 수 있다.

### 출처 
https://www.baeldung.com/java-future
https://stackoverflow.com/questions/55126968/wait-for-the-async-callback-function-to-return-in-java
https://stackoverflow.com/questions/4956822/whats-the-difference-between-future-and-futuretask-in-java