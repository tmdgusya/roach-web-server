package handler.mapping.exception;

/**
 * 요청과 일치하는 Controller Method 를 찾지 못할시 발생하는 Exception 입니다.
 */
public class NotFoundControllerMethodException extends RuntimeException {

    public NotFoundControllerMethodException() {
        super("해당하는 컨트롤러 메소드를 찾을 수 없습니다.");
    }

}
