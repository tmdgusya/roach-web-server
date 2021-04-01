package container.exception;

public class NoBeanDefinition extends RuntimeException {

    public NoBeanDefinition() {
        super("Not Found Such Bean");
    }

}
