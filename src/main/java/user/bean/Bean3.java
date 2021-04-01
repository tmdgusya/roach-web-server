package user.bean;

import container.annotation.AutoWired;
import container.annotation.Component;
import handler.mapping.Controller;

@Component
public class Bean3 {

    @AutoWired
    Bean1 bean1;

    public Bean3() {
    }

    public void run() {
        bean1.test();
    }

}
