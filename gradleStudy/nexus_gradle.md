# 그래이들로 넥서스에 업로드하기

## 상황
새로운 버전의 pgsql로 업데이트를 하기 위해서, nexus에 있는 pgsql을 교체해야 했다.   
원래는 nexus의 UI를 통해서 직접 업로드를 했지만, 여러번 업로드 해야했기 때문에 gradle로 업로드하면 편할 것 같았다.

## 조사
#### maven publish plugin
**설명**   
빌드 artifact를 메이븐 저장소에 업로드하는 기능을 제공하는 플러그인   
메이븐, 그레이들 둘다 해당 플러그인을 사용할 수 있다.   

**사용법**
```shell
plugins {
    id 'maven-publish'
}
```
**Tasks**
- generatePomFileForPubNamePublication
- publishPubNamePublicationToRepoNameRepository
- publishPubNamePublicationToMavenLocal
- publish
- publishToMavenLocal

**Publications**
저장소에 올리려고 하는 파일에 대한 설명   
publications는 이름을 가지며, 하나 또는 그 이상의 artifacts 정보로 구성된다.

해당 플러그인은 MavenPublication 타입의 publications를 제공한다.

링크 : https://docs.gradle.org/current/userguide/publishing_maven.html

**Repositories**
해당 플러그인은 MavenArtifactRepository타입의 repositories를 제공한다.   

## 적용
### build.gradle
```shell
plugins {
    id 'maven-publish'
}

publishing {
    publications {
        maven(MavenPublication) {
            groupId = 'sastPgsql'
            artifactId = 'pgsql-14.5-linux-x64'
            version = '1.0.0'

            artifact '배포하려는파일.zip'
        }
    }
    repositories {
        maven {
            name "nexus"
            url project.nexusUrl
            credentials {
                username project.nexusUsername
                password project.nexusPassword
            }
        }
    }
}
```
### gradle.properties
build.gradle이 위치한 경로에 gradle.properties를 만든다.   
다음의 내용을 적어준다.   
```shell
nexusUsername=넥서스 아이디
nexusPassword=넥서스 비밀번호
nexusUrl=넥서스 주소
```

### 결과물
```shell
groupId(d)   
  + artifactId(d)   
      + version(d)   
          + {artifactId}-{version}.{확장자}
```

## 느낀점
다음의 이유로 한 10번 이상 업로드 했던 것 같은데 gradle로 구성안해놨으면 정말 귀찮아질뻔했다.   
앞으로도 자동화할 수 있는 방법을 잘 활용해야겠다..   

- 권한을 잘못줌
- 리눅스 pgsql을 윈도우에서 작업해서 파일이 깨짐
