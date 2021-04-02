package user;

import container.annotation.AutoWired;
import container.annotation.Component;
import user.TestBean1;
import user.TestBean2;

@Component
public class ConstructorTestBean {

    TestBean1 testBean1;
    TestBean2 testBean2;

    public ConstructorTestBean() {
    }

    @AutoWired
    public ConstructorTestBean(TestBean1 testBean1, TestBean2 testBean2) {
        this.testBean1 = testBean1;
        this.testBean2 = testBean2;
    }

    public String testContructorAutoWired() {
        return testBean1.testBeanString()+testBean2.testBeanString();
    }

}
