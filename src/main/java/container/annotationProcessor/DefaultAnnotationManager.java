package container.annotationProcessor;

import container.annotation.AliasFor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static util.ReflectionUtils.scanAnnotation;

public class DefaultAnnotationManager implements AnnotationManager, AnnotationCollect{

    private static final Logger log = LoggerFactory.getLogger(DefaultAnnotationManager.class);

    private final Map<String, Annotation> annotationContainer = new HashMap<>();
    private ArrayList<Class<?>> annotationClazz;

    public DefaultAnnotationManager() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        this.collect();
        for(Class<?> clazz : annotationClazz) {
            this.findAliasAnnotation(clazz);
        }
    }

    @Override
    public void manage(String originAnnotation, Annotation annotation) {
        annotationContainer.put(originAnnotation, annotation);
    }

    @Override
    public void merge(String parent, Annotation child) {
        annotationContainer.put(parent, child);
    }

    @Override
    public Annotation getType(String key) {
        return annotationContainer.get(key);
    }

    @Override
    public void collect() {
        annotationClazz = scanAnnotation();
    }

    private void findAliasAnnotation(Class<?> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        final AliasFor aliasFors = clazz.getAnnotation(AliasFor.class);
        this.merge(clazz.getName(), aliasFors.alias().getConstructor().newInstance());
        log.info("Current Annotation Map : {}", annotationContainer);
    }

}
