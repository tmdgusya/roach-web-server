package container.annotationProcessor;

import container.BeanFactory;
import container.annotation.AutoWired;
import container.annotation.Component;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import user.bean.Bean1;
import util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class AutoWiredProcessor {

    private final BeanFactory beanFactory;

    public AutoWiredProcessor(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void conductAutoInjection() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        conductBeanInjection();
        conductConstructorBeanInjection();
    }

    private void conductBeanInjection() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        final ArrayList<Class<?>> classes = ReflectionUtils.scanForBeansClasses();
        for(Class<?> beanClass : classes) {
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

    private void conductConstructorBeanInjection() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        final ArrayList<Class<?>> clazz = ReflectionUtils.scanForBeansClasses();
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
