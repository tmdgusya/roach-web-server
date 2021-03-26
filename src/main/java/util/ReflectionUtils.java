package util;

import handler.mapping.Controller;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.util.Set;

public class ReflectionUtils {

    public static Class<?>[] scanForControllers(String packageStr) {
        Reflections reflections = new Reflections((new ConfigurationBuilder()).setUrls(ClasspathHelper.forPackage(packageStr)).filterInputsBy((new FilterBuilder()).includePackage(packageStr)));
        Set classes = reflections.getTypesAnnotatedWith(Controller.class);
        return (Class[])classes.toArray(new Class[classes.size()]);
    }

}
