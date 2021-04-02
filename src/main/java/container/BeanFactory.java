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

public class BeanFactory {

    private static final Logger log = LoggerFactory.getLogger(BeanFactory.class);

    private final ConcurrentMap<String, Object> beanFactory = new ConcurrentHashMap<>();
    private final ArrayList<Class<?>> beanClasses;

    public BeanFactory() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        beanClasses = scanForBeansClasses();
        initializeBean();
    }

    private void initializeBean() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //TODO lazy initialize Bean
        eagerInitializeBean();
        log.info("Eager Initialize Beans : {}", beanClasses);
    }

    private void eagerInitializeBean() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        for(Class<?> beanClass : beanClasses) { // beanClassName is FQCN
            Object beanInstance = beanClass.getConstructor().newInstance();
            String beanClassName = beanClass.getName();
            beanFactory.putIfAbsent(beanClassName, beanInstance);
        }
    }

    public Object getBean(String beanName) {
        if(!beanFactory.containsKey(beanName)) {
            throw new NoBeanDefinition();
        }
        return beanFactory.get(beanName);
    }

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
