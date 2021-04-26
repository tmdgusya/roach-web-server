package container.annotation;

import java.lang.annotation.*;

/**
 * Service 는 Service 계층 로직임을 알려주기 위해 있는 어노테이션으로
 * AliasFor 를 이용해서 Component 를 넓힌 Annotation 중 하나입니다.
 * 따라서 서버 사이드 프로그램이 Warm up 될때, 자동으로 BeanFactory 에 추가됩니다.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@AliasFor(alias = Component.class)
public @interface Service {

}
