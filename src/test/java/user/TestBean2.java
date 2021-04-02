package user;

import container.annotation.AutoWired;
import container.annotation.Component;
import org.junit.jupiter.api.TestInstance;

@Component
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestBean2 {

    @AutoWired
    TestBean1 testBean1;

    public String testAutoWired() {
        return testBean1.test();
    }

    public String testBeanString() {
        return "TestBean2";
    }

}
