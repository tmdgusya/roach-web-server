package user.bean;

import container.annotation.Component;

@Component
public class Bean1 {

    public Bean1() {
    }

    public void test() {
        System.out.println("AUTO WIRED SUCCESS");
    }

}
