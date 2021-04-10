package container.annotation;

import java.lang.annotation.*;

/**
 * AliasFor 는 어노테이션을 Merge 하는 방식으로 이용되는 어노테이션이다. 예를 들면, @Component 기 븥아 있는 Annotation 을
 * 현재 Bean 으로 찾게 되는데, 만약 다른 클래스에서 AliasFor 를 이용해서 해당 어노테이션을 사용중이라면, Bean 에서 해당 어노테이션
 * 클래스를 @Component 로 간주하게 됩니다. 확장성을 넓혀주기 위해 사용되는 어노테이션입니다.
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AliasFor {

    public Class<? extends Annotation> alias();

}
