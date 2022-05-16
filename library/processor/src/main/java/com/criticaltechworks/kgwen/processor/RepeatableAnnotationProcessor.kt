package com.criticaltechworks.kgwen.processor

import com.criticaltechworks.kgwen.processor.AnnotationProcessor.Companion.PROCESS_FAILURE
import com.criticaltechworks.kgwen.processor.AnnotationProcessor.Companion.PROCESS_SUCCESS
import javax.annotation.processing.*
import javax.lang.model.element.*
import kotlin.reflect.KClass

/**
 * An [AbstractProcessor] that is responsible for processing (possibly repeated) annotations of type
 * [T].
 */
internal abstract class RepeatableAnnotationProcessor<T : Annotation, U : Annotation>(
    annotationType: KClass<T>,
    private val annotationContainerType: KClass<U>
) : AnnotationProcessor<T>(annotationType) {
    /**
     * Provides the processing logic for the [Repeatable] annotation container.
     * @param element An element with an annotation of type [U].
     * @param annotation The container annotation
     * @return [PROCESS_SUCCESS] if the annotation processing succeeded, [PROCESS_FAILURE]
     * otherwise.
     */
    protected abstract fun processContainer(element: Element, annotation: U): Boolean

    final override fun process(set: Set<TypeElement>, roundEnvironment: RoundEnvironment): Boolean {
        roundEnvironment.getElementsAnnotatedWith(annotationContainerType.java)
            .forEach { containerElement ->
                val containerAnnotation =
                    containerElement.getAnnotation(annotationContainerType.java)
                if (processContainer(containerElement, containerAnnotation) == PROCESS_FAILURE) {
                    return PROCESS_FAILURE
                }
            }

        roundEnvironment.getElementsAnnotatedWith(annotationType.java).forEach { element ->
            if (process(element, element.getAnnotation(annotationType.java)) == PROCESS_FAILURE) {
                return PROCESS_FAILURE
            }
        }
        return PROCESS_SUCCESS
    }

    final override fun getSupportedAnnotationTypes(): Set<String> =
        setOf(annotationType.java.canonicalName, annotationContainerType.java.canonicalName)
}
