## 리눅스 명령어
업무하면서 가장 많이 쓰이는 리눅스 명령어를 적어보려고 한다.  
자꾸 까먹어서 속상하다.

### scp
옵션값을 잘 알아두면 좋겠다.   

|옵션 |설명|
|---|:---|
|r|디렉토리 및 하위 모든 파일 복사|
|P|원본 속성값 복사|

근데 r은 무슨 약자인데 하위 모든것 이라는 의미로 사용되는거지?

ex) 내 로컬에 있는 파일을 서버로 보낼 때
```shell
scp 
```
ex) 서버에 있는 파일을 내 로컬로 보낼 때

### grep  
**g**(global)**re**(정규식)**p**(print)

### find
ex) 현재 경로에 있는 모든 파일의 권한을 755로 바꾼다.
```shell
find . -type f -exec chmod 755 {} \;
```


### zip / unzip
zip -r 