package container;

import container.exception.NoBeanDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static util.ReflectionUtils.*;

/**
 * BeanFactory 는 Core Class 입니다.
 * RoachCat Application 이 Warmup 되는 시점에 해당 Package Directory 에서 Bean Class 들을 찾아서
 * BeanFactory 안에 관리합니다. 해당 Class 는 SingleTon 처럼 Application 전역에서 사용됩니다.
 * 현재는 Eager-Initialization 만 가능하며, 후에 지연 초기화도 제작할 예쩡입니다.
 */
public class BeanFactory {

    private static final Logger log = LoggerFactory.getLogger(BeanFactory.class);

    private final ConcurrentMap<String, Object> beanFactory = new ConcurrentHashMap<>();
    private final ArrayList<Class<?>> beanClasses;

    public BeanFactory() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        beanClasses = scanForBeansClasses();
        initializeBean();
    }

    /**
     * Warmup 시 Bean 을 생성합니다.
     *
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private void initializeBean() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //TODO lazy initialize Bean
        eagerInitializeBean();
        log.info("Eager Initialize Beans : {}", beanClasses);
    }

    /**
     * 즉시 Bean 을 생성합니다.
     *
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    private void eagerInitializeBean() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        for(Class<?> beanClass : beanClasses) { // beanClassName is FQCN
            Object beanInstance = beanClass.getConstructor().newInstance();
            String beanClassName = beanClass.getName();
            beanFactory.putIfAbsent(beanClassName, beanInstance);
        }
    }

    /**
     * Bean Name 을 기반으로 Bean 을 조회합니다.
     * @param beanName 조회하고자 하는 Bean Name 을 입력합니다.
     * @return 찾아온 Bean 을 리턴합니다. 찾지 못한다면 NoBeanDefinition 예외를 발생합니다.
     */
    public Object getBean(String beanName) {
        if(!beanFactory.containsKey(beanName)) {
            throw new NoBeanDefinition();
        }
        return beanFactory.get(beanName);
    }

    /**
     * Type 기반으로 Bean 을 조회합니다.
     * @param type 조회하고자 하는 Bean Type 을 입력합니다.
     * @return 찾아온 Bean 을 리턴합니다. 찾지 못한다면 NoBeanDefinition 예외를 발생합니다.
     */
    public Object getBean(Class<?> type) {
        for(Object bean : beanFactory.values()) {
            if(type.getName().equals(bean.getClass().getName())){
                return bean;
            }
        }
        throw new NoBeanDefinition();
    }

    public void changeBean(Object bean) {
        beanFactory.put(bean.getClass().getName(), bean);
    }

}
