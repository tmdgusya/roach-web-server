# RoachCat

## Commit Convention

```
[# 이슈 번호] type :emoji: title

contents..
```

## Using

### Mapping

- 반드시 `@Controller` 어노테이션이 붙어있는 클래스 밑에서만 사용가능하다.

```java
@GetMapping(url = "/hello")
public String hello() {
    return "hello.html";
        }
        
@PostMapping(url = "/hello")
public String hello() {
        return "hiPost.html";
        }
```

- 위와 같이 사용할 수 있으며 `/hello` 로 요청이 들어오면 `hello.html` 을 리턴해준다.

**리다이렉트 하는 법**
```java
@GetMapping(url = "/hello")
public String hello() {
    return "redirect:/hi";
        }

@PostMapping(url = "/hello")
public String hello() {
        return "redirect:hiPost";
}
```

- hi 라는 url 로 302 Redirect 가 가능하다.

현재 버전까지는 View 만 Resolve 하는것이 가능하며, CSS 도 자동으로 가져온다.

## Bean 추가 방법

- 아래와 같이 @Component Annotation 을 붙여주면, 빈으로 인식하고 RoachCat 이 자동으로 BeanFactory 에 추가해줍니다.

이후 @Service 와 @Bean 어노테이션으로도 가능하도록 제작 예정입니다.

또한 @AutoWired 를 통한 자동주입도 후에 개발될 예정입니다.

```java
@Component
public class TestBean {
    
}
```

## 현재 부족한 부분

- 예외처리가 상당히 대충되어 있음.. 이부분들에 대한 개선이 필요함

- TestCode 작성

- 리팩토링

## Commit Message Icon

|Commit Type|Emoji|Type|
|---:|:---:|---|
|BugFix|:bug: `:bug:`| bug: |
|Review Fix|:wrench: `:wrench:` | fix: |
|Documentation|:books:`:books:`| doc: |
|Documenting Source Code|:bulb:`:bulb`| doc: |
|Performance|:racehors:`:racehors:`| perf: |
|Adding a Test|:white_check_mark:`:white_check_mark:`| test: |
|Refactoring Code|:recycle:`:recycle:`| refactor: |
|Docker|:whale:`:whale:`| infra: |
|Update README File|:pencil:`:pencil:`| text: |
|Need Refactoring|:hankey:`:hankey:`|
|Add CI Build System|:construction_worker:`:construction_worker:`| infra: |
|Deploying Stuff|:rocket:`:rocket:`| infra: |
|Add Dependency|:package:`:package:`| setup: |
|Featuring New Function|:smiley_cat:`:smiley_cat:`| feat: |
|test |:checkered_flag:`:checkered_flag:`| test: |

