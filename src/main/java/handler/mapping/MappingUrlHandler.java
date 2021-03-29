package handler.mapping;

import core.HttpRequest;
import core.HttpResponse;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import static util.ReflectionUtils.*;

public class MappingUrlHandler {

    private static final Logger log = LoggerFactory.getLogger(MappingUrlHandler.class);

    private final String method;
    private final String url;
    private Class<?> targetClass;
    private Method controllerMethod;

    public MappingUrlHandler(String method, String url) {
        this.method = method;
        this.url = url;
    }


    public Object invokeMethod(HttpRequest httpRequest, HttpResponse httpResponse) throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException, ClassNotFoundException {
        final Method method = this.findMethod();
        final Object controllerClass = targetClass.getConstructor().newInstance();
        final Class<?>[] parameterTypes = method.getParameterTypes();
        return method.invoke(controllerClass, httpRequest, httpResponse);
    }

    private Method findMethod() throws ClassNotFoundException {
        return findController(scanForControllers("controller"));
    }

    private Method findController(Class<?>[] controllerclasses) throws ClassNotFoundException {
        for(Class c : controllerclasses) {
            this.targetClass = Class.forName(c.getName());
            final Method[] declaredMethods = c.getDeclaredMethods();
            if(method.equals("GET")) {
                return findGetController(declaredMethods);
            }
            if(method.equals("POST")) {
                return findPostController(declaredMethods);
            }
        }
        throw new IllegalArgumentException("500 을 리턴해야한다.");
    }

    private Method findGetController(Method[] methods) {
        for(Method method : methods) {
            if(method.isAnnotationPresent(GetMapping.class)) {
                final GetMapping annotation = method.getAnnotation(GetMapping.class);
                if(annotation.url().equals(this.url)) {
                    return method;
                }
            }
        }
        throw new IllegalArgumentException("500 을 리턴해야한다.");
    }

    private Method findPostController(Method[] methods) {
        for(Method method : methods) {
            if(method.isAnnotationPresent(PostMapping.class)){
                final PostMapping annotation = method.getAnnotation(PostMapping.class);
                if(annotation.url().equals(this.url)) {
                    return method;
                }
            }
        }
        throw new IllegalArgumentException("500 을 리턴해야한다.");
    }

}
