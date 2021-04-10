package container.annotationProcessor;

import container.BeanFactory;
import container.annotation.AutoWired;
import util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * 자동으로 의존성을 주입해주기 위해 생성한 클래스 입니다.
 * `@AutoWired` 를 이용해서 자동 주입을 실시하며, FieldInjection, Constructor Injection 이 가능합니다.
 * `@Component 가 붙어 있는 클래스에서만 현재 사용이 가능합니다.
 * 주입 알고리즘은 아래와 같습니다.
 *
 * 1. 해당 @AutoWired 에 ClassName 이 선언되어 있는지 확인한다. 선언되어 있다면 바로 주입한다. O(1)
 *
 * 2. 해당 @AutoWired 에 ClassName 이 선언되어 있지 않다면, Type 을 조회해서 주입힌다. O(N)
 *
 * Field Injection 사용 예시
 *
 * ```java
 * `@Component
 * public class Roach {
 *      `@Autowired(className = "com.roach.Roach:")
 *      Roach roach;
 * }
 * ```
 *
 * Constructor Injection 사용 예시
 *
 * ```java
 * `@Component
 * public class ConstructorTestBean {
 *
 *  TestBean1 testBean1;
 *  TestBean2 testBean2;
 *
 *  public ConstructorTestBean() {
 *  }
 *
 * `@AutoWired
 *  public ConstructorTestBean(TestBean1 testBean1, TestBean2 testBean2) {
 *      this.testBean1 = testBean1;
 *      this.testBean2 = testBean2;
 *  }
 * }
 * ```
 */
public class AutoWiredProcessor {

    private final BeanFactory beanFactory;

    public AutoWiredProcessor(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    private final ArrayList<Class<?>> clazz = ReflectionUtils.scanForBeansClasses();

    public void conductAutoInjection() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        conductBeanInjection();
        conductConstructorBeanInjection();
    }

    /**
     * Bean Class 들을 몯 스캔하여 가져옵니다. 그 후 해당 Field 에 선언된, @AutoWired 를 찾아서
     * 해당 Type 에 일치 혹은 ClassName 에 일치하는 Bean 이 존재할시 자동으로 주입해줍니다.
     *
     * @throws NoSuchMethodException 해당 하는 메소드를 찾지 못할때 발생합니다.
     * @throws IllegalAccessException Field 에 대한 Access 가 충분하지 않을때 일어 납니다.
     * @throws InvocationTargetException 해당 Target 에 대한 Exception 발생시 일어 납니다.
     * @throws InstantiationException ₩₩
     */
    private void conductBeanInjection() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        for(Class<?> beanClass : clazz) {
            final Field[] declaredFields = beanClass.getDeclaredFields();
            for(Field field : declaredFields) {
                AutoWired annotation = field.getAnnotation(AutoWired.class);
                if(annotation != null) {
                    Object bean;
                    if(!annotation.className().equals("")) {
                        bean = beanFactory.getBean(annotation.className());
                    } else {
                        bean = beanFactory.getBean(beanClass);
                    }
                    field.setAccessible(true);
                    field.set(bean, beanFactory.getBean(field.getType()));
                }
            }
        }
    }

    /**
     * 위와 같이 Bean 클래스 가져 온뒤에, 해당 Class 들에서 Constructor 에 @AutoWired 가 붙어있는지를 찾아온 후
     * 자동으로 Bean 을 주입해줍니다.
     *
     * @throws IllegalAccessException Field 에 대한 Access 가 충분하지 않을때 일어 납니다.
     * @throws InvocationTargetException 해당 Target 에 대한 Exception 발생시 일어 납니다.
     * @throws InstantiationException ₩₩
     */
    private void conductConstructorBeanInjection() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Object[] injectionBeans;
        for(Class<?> beanClass : clazz) {
            Constructor<?>[] constructors = beanClass.getConstructors();
            for(Constructor<?> constructor : constructors) {
                Annotation annotation = constructor.getAnnotation(AutoWired.class);
                if(annotation != null) {
                    Object bean = beanFactory.getBean(beanClass);
                    Class<?>[] parameterTypes = constructor.getParameterTypes();
                    injectionBeans = new Object[parameterTypes.length];
                    for(int i = 0; i < parameterTypes.length; i++) {
                        injectionBeans[i] = beanFactory.getBean(parameterTypes[i]);
                    }
                    beanFactory.changeBean(constructor.newInstance(injectionBeans));
                }
            }
        }
    }

}
