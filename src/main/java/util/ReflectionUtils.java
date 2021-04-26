package util;

import container.annotation.Component;
import handler.mapping.Controller;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Set;

/**
 * Reflection 과 관련된 Util 함수들의 집합체 클래스 입니다.
 *
 * ROOT_PACAKAGE_NAME 는 사용자가 직접사용하는 ROOT_PACKAGE_NAME 을 의미합니다.
 */
public class ReflectionUtils {

    private static final Logger log = LoggerFactory.getLogger(ReflectionUtils.class);
    private static final String ROOT_PACKAGE_NAME = "user";

    /**
     * ROOT PACKAGE 에서 @Controller 클래스를 찾습니다.
     * @return `@Controller` 어노테이션이 붙은 클래스들.
     */
    public static ArrayList<Class<?>> scanForControllers() {
        Reflections reflections = new Reflections((new ConfigurationBuilder()).setUrls(ClasspathHelper.forPackage(ROOT_PACKAGE_NAME)).filterInputsBy((new FilterBuilder()).includePackage(ROOT_PACKAGE_NAME)));
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Controller.class);
        log.info("Controller classes : {}", classes);
        return new ArrayList<>(classes);
    }

    /**
     * ROOT PACKAGE 에서 @Component 클래스를 찾습니다.
     * @return `@Component` 가 붙어있는 클래스들.
     */
    public static ArrayList<Class<?>> scanForBeansClasses() {
        Reflections reflections = new Reflections((new ConfigurationBuilder()).setUrls(ClasspathHelper.forPackage(ROOT_PACKAGE_NAME)).filterInputsBy((new FilterBuilder()).includePackage(ROOT_PACKAGE_NAME)));
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Component.class, true);
        log.info("Bean classes : {}", classes);
        return new ArrayList<>(classes);
    }

    public static ArrayList<Class<?>> scanAnnotation() {
        Reflections reflections = new Reflections((new ConfigurationBuilder()).setUrls(ClasspathHelper.forPackage(ROOT_PACKAGE_NAME)).filterInputsBy((new FilterBuilder()).includePackage(ROOT_PACKAGE_NAME)));
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Annotation.class);
        log.info("Annotation classes : {}", classes);
        return new ArrayList<Class<?>>(classes);
    }

}
