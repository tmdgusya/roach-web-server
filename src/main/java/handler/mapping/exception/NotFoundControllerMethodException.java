package handler.mapping.exception;

public class NotFoundControllerMethodException extends RuntimeException {

    public NotFoundControllerMethodException() {
        super("해당하는 컨트롤러 메소드를 찾을 수 없습니다.");
    }

}
