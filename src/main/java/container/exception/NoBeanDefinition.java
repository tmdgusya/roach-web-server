package container.exception;

/**
 * Bean Factory 에서 해당 하는 Bean 을 찾지 못할때 발생하는 Runtime Exception 입니다.
 */
public class NoBeanDefinition extends RuntimeException {

    public NoBeanDefinition() {
        super("Not Found Such Bean");
    }

}
