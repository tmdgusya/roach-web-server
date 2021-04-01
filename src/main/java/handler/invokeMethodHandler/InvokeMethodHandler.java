package handler.invokeMethodHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InvokeMethodHandler {

    public static Object invoke(Method method, Class<?> targetClass, Object... objects) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        final Object targetInstance = targetClass.getConstructor().newInstance();
        return method.invoke(targetInstance, objects);
    }

}
