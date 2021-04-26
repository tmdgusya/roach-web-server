package handler.mapping;

import container.annotation.AliasFor;
import container.annotation.MappingRequest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 헤딩 어노테이션이 붙은 메소드는 "POST" 요청을 처리하는 Method 임을 나타냅니다.
 *
 * ```java
 * `@PostMapping(url = "/hello")
 * public void hello() {
 *     System.out.println("Hello!!");
 * }
 * ```
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@AliasFor(alias = MappingRequest.class)
public @interface PostMapping {

    String url() default "";

}
