package handler.mapping;

import core.RequestMethod;
import handler.mapping.exception.NotFoundControllerMethodException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;

class MappingUrlHandlerTest {

    @Test
    void findGetMethodTest() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        MappingUrlHandler mappingUrlHandler = new MappingUrlHandler(RequestMethod.GET, "/test");
        final Method method = mappingUrlHandler.findMethod();
        assertThat(method.getName()).isEqualTo("get");
    }

    @Test
   void findPostMethodTest() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        MappingUrlHandler mappingUrlHandler = new MappingUrlHandler(RequestMethod.POST, "/test");
        final Method method = mappingUrlHandler.findMethod();
        assertThat(method.getName()).isEqualTo("post");
    }

    @Test
    void notFoundMethodException() {
        MappingUrlHandler mappingUrlHandler = new MappingUrlHandler(RequestMethod.POST, "NOT!!");
        org.junit.jupiter.api.Assertions.assertThrows(NotFoundControllerMethodException.class, mappingUrlHandler::findMethod);
    }

}
