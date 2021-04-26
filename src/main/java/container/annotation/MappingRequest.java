package container.annotation;

public @interface MappingRequest {

    String url() default "";

    String method() default "GET";

}
