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
```

- 위와 같이 사용할 수 있으며 `/hello` 로 요청이 들어오면 `hello.html` 을 리턴해준다.

**리다이렉트 하는 법**
```java
@GetMapping(url = "/hello")
public String hello() {
    return "redirect:/hi";
        }
```

- hi 라는 url 로 302 Redirect 가 가능하다.

현재 버전까지는 View 만 Resolve 하는것이 가능하며, CSS 도 자동으로 가져온다.

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

