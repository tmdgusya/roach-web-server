package handler.mapping;

import core.HttpRequest;
import core.HttpResponse;
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
    public void findGetMethodTest() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        MappingUrlHandler mappingUrlHandler = new MappingUrlHandler("GET", "/test");
        final Method method = mappingUrlHandler.findMethod();
        assertThat(method.getName()).isEqualTo("get");
    }

    @Test
    public void findPostMethodTest() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        MappingUrlHandler mappingUrlHandler = new MappingUrlHandler("POST", "/test");
        final Method method = mappingUrlHandler.findMethod();
        assertThat(method.getName()).isEqualTo("post");
    }

}
