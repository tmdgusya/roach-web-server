package handler.mapping;

import container.annotation.AliasFor;
import container.annotation.MappingRequest;

import java.lang.annotation.*;

/**
 * 헤딩 어노테이션이 붙은 메소드는 "GET" 요청을 처리하는 Method 임을 나타냅니다.
 *
 * ```java
 * `@GetMapping(url = "/hello")
 * public void hello() {
 *     System.out.println("Hello!!");
 * }
 * ```
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@AliasFor(alias = MappingRequest.class)
public @interface GetMapping {

    String url() default "";

}
