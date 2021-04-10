package container.annotationProcessor;

import container.BeanFactory;
import container.annotation.AutoWired;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.ConstructorTestBean;
import user.TestBean2;
import org.assertj.core.api.Assertions;
import user.TestBean3;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.*;

class AutoWiredProcessorTest {

    BeanFactory beanFactory;
    AutoWiredProcessor autoWiredProcessor;
    TestBean2 testBean2;
    TestBean3 testBean3;
    ConstructorTestBean constructorTestBean;

    @BeforeEach
    public void init() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        beanFactory = new BeanFactory();
        autoWiredProcessor = new AutoWiredProcessor(beanFactory);
        autoWiredProcessor.conductAutoInjection();
    }

    @Test
    public void injectBeanByBeanNameTest() {
        testBean3 = (TestBean3) beanFactory.getBean("user.TestBean3");
        assertThat(testBean3.test()).isEqualTo("autoWired");
    }

    @Test
    public void autoWiredTest() {
        testBean2 = (TestBean2) beanFactory.getBean(TestBean2.class);
        assertThat(testBean2.testAutoWired()).isEqualTo("autoWired");
    }

    @Test
    public void constructorAutoWiredTest() {
        constructorTestBean = (ConstructorTestBean) beanFactory.getBean(ConstructorTestBean.class);
        assertThat(constructorTestBean.testContructorAutoWired()).isEqualTo("TestBean1TestBean2");
    }

}
