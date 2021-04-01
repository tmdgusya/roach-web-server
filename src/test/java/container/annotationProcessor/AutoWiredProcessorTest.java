package container.annotationProcessor;

import container.BeanFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.TestBean2;
import org.assertj.core.api.Assertions;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.*;

class AutoWiredProcessorTest {

    BeanFactory beanFactory;
    AutoWiredProcessor autoWiredProcessor;
    TestBean2 testBean2;

    @BeforeEach
    public void init() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        beanFactory = new BeanFactory();
        autoWiredProcessor = new AutoWiredProcessor(beanFactory);
        autoWiredProcessor.conductBeanInjection();
    }

    @Test
    public void autoWiredTest() {
        testBean2 = (TestBean2) beanFactory.getBean(TestBean2.class);
        assertThat(testBean2.testAutoWired()).isEqualTo("autoWired");
    }

}
