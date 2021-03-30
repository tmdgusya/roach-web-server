package util;

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

    public static ArrayList<Class<?>> scanForControllers(String packageStr) {
        Reflections reflections = new Reflections((new ConfigurationBuilder()).setUrls(ClasspathHelper.forPackage(packageStr)).filterInputsBy((new FilterBuilder()).includePackage(packageStr)));
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Controller.class);
        log.info("Controller classes : {}", classes);
        return new ArrayList<>(classes);
    }

}
