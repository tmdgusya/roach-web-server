package handler.mapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 해당 클래스가 Controller 임을 나타내는 어노테이션이다
 * 후에 @AliasFor 를 통해서 @Component 로 확장예정이다.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
}
