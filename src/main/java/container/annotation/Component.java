package container.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * RoachCat 에서 Bean 으로 다뤄지기 위해서 사용되는 어노테이션이다.
 * 해당 Annotation 이 붙어 있는 클래스는 RoachCat 이 Bean 으로 인식하고, 서버 사이드 프로그램이 시작될 때
 * 자동으로 BeanFactory 에 추가됩니다.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {
}
