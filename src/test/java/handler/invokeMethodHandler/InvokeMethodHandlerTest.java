package handler.invokeMethodHandler;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.*;

class InvokeMethodHandlerTest {

    @Test
    public void invokeTest() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        final Class<?> targetClass = Class.forName("user.controller.TestController");
        assertThat(InvokeMethodHandler.invoke(targetClass.getMethod("get"), targetClass)).isEqualTo("index");
    }

}
