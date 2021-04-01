package handler.mapping;

import core.HttpRequest;
import core.HttpResponse;
import handler.mapping.exception.NotFoundControllerMethodException;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.zip.InflaterInputStream;

import static org.assertj.core.api.Assertions.*;

class MappingUrlHandlerTest {

    @Test
    void findGetMethodTest() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        MappingUrlHandler mappingUrlHandler = new MappingUrlHandler("GET", "/test");
        final Method method = mappingUrlHandler.findMethod();
        assertThat(method.getName()).isEqualTo("get");
    }

    @Test
   void findPostMethodTest() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        MappingUrlHandler mappingUrlHandler = new MappingUrlHandler("POST", "/test");
        final Method method = mappingUrlHandler.findMethod();
        assertThat(method.getName()).isEqualTo("post");
    }

    @Test
    void notFoundMethodException() {
        MappingUrlHandler mappingUrlHandler = new MappingUrlHandler("POST", "NOT!!");
        org.junit.jupiter.api.Assertions.assertThrows(NotFoundControllerMethodException.class, mappingUrlHandler::findMethod);
    }

}
