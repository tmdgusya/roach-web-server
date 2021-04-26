package container.annotationProcessor;

import java.lang.annotation.Annotation;

/**
 * Annotation 을 관리해주는 Annotation Manager 이다.
 * manage 로 관리를 시작하며 Map 구조를 이용하여 관리하여야 한다.
 */
public interface AnnotationManager {

    /**
     * 자체 구현된 자료구조 (key-value) 로 key 를 통하여 해당 Annotation 을 관리한다.
     * @param originAnnotation 원본 Annotation 을 뜻한다.
     * @param annotation merge 된 Annotation 또는 원본 Annotation 이다.
     */
    public void manage(String originAnnotation, Annotation annotation);

    public void merge(String parent, Annotation child);

    public Annotation getType(String annotationFQCN);

}
