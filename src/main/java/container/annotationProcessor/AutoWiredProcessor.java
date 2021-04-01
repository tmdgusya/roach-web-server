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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Set;

public class AutoWiredProcessor {

    private final BeanFactory beanFactory;

    public AutoWiredProcessor(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void conductBeanInjection() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        final ArrayList<Class<?>> classes = ReflectionUtils.scanForBeansClasses();
        for(Class<?> beanClass : classes) {
            final Field[] declaredFields = beanClass.getDeclaredFields();
            for(Field field : declaredFields) {
                AutoWired annotation = field.getAnnotation(AutoWired.class);
                if(annotation != null) {
                    Object bean = beanFactory.getBean(beanClass);
                    field.setAccessible(true);
                    field.set(bean, beanFactory.getBean(field.getType()));
                }
            }
        }
    }

}
