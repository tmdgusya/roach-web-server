package container.annotation;

import java.lang.annotation.*;

/**
 * `@AutoWired` 는 생성자를 자동 주입받기 위해서 생성된 태그이다. FIELD 와 CONSTRUCTOR 에 할당이 가능하며,
 * AutoWiredProcessor 를 통해서 RoachCat 이 Running 되는 순간, 해당 Annotation 이 붙어 있는 클래스들에 자동으로
 * 의존성 주입을 실시 합니다. className 으로 주입받고 싶은 클래스의 이름을 적어주면 빠르게 주입이 가능합니다.
 * 이름을 선언하지 않을시 같은 타입의 Bean 이 주입되게 됩니다. 이 경우 인터페이스로 인해 두개가 Bean 에 등록되어 있을시
 * 찾아오는 과정에서 의도치 않은 Bean 이 주입될 수 있으니, className 을 적어주시는걸 선호 합니다.
 */
@Target({ElementType.FIELD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoWired {

    String className() default "";

}
