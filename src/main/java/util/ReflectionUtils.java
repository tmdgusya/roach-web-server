package util;

import container.annotation.Component;
import handler.mapping.Controller;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Set;

public class ReflectionUtils {

    private static final Logger log = LoggerFactory.getLogger(ReflectionUtils.class);
    private static final String ROOT_PACAKAGE_NAME = "user";

    public static ArrayList<Class<?>> scanForControllers() {
        Reflections reflections = new Reflections((new ConfigurationBuilder()).setUrls(ClasspathHelper.forPackage(ROOT_PACAKAGE_NAME)).filterInputsBy((new FilterBuilder()).includePackage(ROOT_PACAKAGE_NAME)));
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Controller.class);
        log.info("Controller classes : {}", classes);
        return new ArrayList<>(classes);
    }

    public static ArrayList<Class<?>> scanForBeansClasses() {
        Reflections reflections = new Reflections((new ConfigurationBuilder()).setUrls(ClasspathHelper.forPackage(ROOT_PACAKAGE_NAME)).filterInputsBy((new FilterBuilder()).includePackage(ROOT_PACAKAGE_NAME)));
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Component.class, true);
        log.info("Bean classes : {}", classes);
        return new ArrayList<>(classes);
    }

}
