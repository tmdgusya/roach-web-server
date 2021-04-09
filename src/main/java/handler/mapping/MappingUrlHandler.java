package handler.mapping;

import core.HttpRequest;
import core.HttpResponse;
import core.RequestMethod;
import handler.invokeMethodHandler.InvokeMethodHandler;
import handler.mapping.exception.NotFoundControllerMethodException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static util.ReflectionUtils.*;

public class MappingUrlHandler {

    private static final Logger log = LoggerFactory.getLogger(MappingUrlHandler.class);

    private final RequestMethod method;
    private final String url;
    private Class<?> targetClass;
    private Method controllerMethod;

    public MappingUrlHandler(RequestMethod method, String url) {
        this.method = method;
        this.url = url;
    }

    public Object invokeMethod(HttpRequest httpRequest, HttpResponse httpResponse) throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException, ClassNotFoundException {
        this.controllerMethod  = this.findMethod();
        final Object controllerClass = targetClass.getConstructor().newInstance();
        return InvokeMethodHandler.invoke(controllerMethod, targetClass, httpRequest, httpResponse);
    }

    public Method findMethod() throws ClassNotFoundException {
        return findController(scanForControllers());
    }

    private Method findController(ArrayList<Class<?>> controllerclasses) throws ClassNotFoundException {
        for(Class<?> c : controllerclasses) {
            this.targetClass = Class.forName(c.getName());
            final Method[] declaredMethods = c.getDeclaredMethods();
            if(method.equals(RequestMethod.GET)) {
                final Method controllerMethod = findGetController(declaredMethods);
                if(controllerMethod != null) {
                    return controllerMethod;
                }
            }
            if(method.equals(RequestMethod.POST)) {
                final Method controllerMethod = findPostController(declaredMethods);
                if(controllerMethod != null) {
                    return controllerMethod;
                }
            }
        }
        throw new NotFoundControllerMethodException();
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
        return null;
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
        return null;
    }

    public Method getControllerMethod() {
        return controllerMethod;
    }
}
