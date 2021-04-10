package handler.invokeMethodHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 원하는 Object 로 Invoke 하기 위해 따로 만들어진 클래스 입니다.
 */
public class InvokeMethodHandler {

    public static Object invoke(Method method, Class<?> targetClass, Object... objects) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        final Object targetInstance = targetClass.getConstructor().newInstance();
        return method.invoke(targetInstance, objects);
    }

}
