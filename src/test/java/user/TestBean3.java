package user;

import container.annotation.AutoWired;
import container.annotation.Component;
import org.junit.jupiter.api.TestInstance;

@Component
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestBean3 {

    @AutoWired(className = "user.TestBean3")
    TestBean1 testBean1;

    public String test() {
        return testBean1.test();
    }

}
