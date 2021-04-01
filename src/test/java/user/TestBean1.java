package user;

import container.annotation.Component;
import org.junit.jupiter.api.TestInstance;

@Component
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestBean1 {


    public String test() {
        return "autoWired";
    }

}
