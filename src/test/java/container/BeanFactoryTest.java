package container;

import container.BeanFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.TestBean1;
import user.TestBean2;
import org.assertj.core.api.Assertions;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.*;

class BeanFactoryTest {

    BeanFactory beanFactory;

    @BeforeEach
    public void init() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        beanFactory = new BeanFactory();
    }

    @Test
    void beanFactoryIsNormallyCreatedBean() {
        assertThat(beanFactory.getBean(TestBean1.class)).isNotNull();
        assertThat(beanFactory.getBean(TestBean2.class)).isNotNull();
    }

    @Test
    void isNormallyOperateGetBeanByNameMethod() {
        assertThat(beanFactory.getBean("user.TestBean1")).isNotNull();
    }

    @Test
    void isNormallyOperateGetBeanByClassMethod() {
        assertThat(beanFactory.getBean(TestBean1.class)).isNotNull();
    }

}
