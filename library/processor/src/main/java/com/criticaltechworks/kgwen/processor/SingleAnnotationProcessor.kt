/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.processor

import javax.annotation.processing.*
import javax.lang.model.element.TypeElement
import kotlin.reflect.KClass

/**
 * An [AbstractProcessor] that is responsible for processing a single annotation of type [T].
 */
internal abstract class SingleAnnotationProcessor<T : Annotation>(
    annotationType: KClass<T>
) : AnnotationProcessor<T>(annotationType) {
    final override fun process(set: Set<TypeElement>, roundEnvironment: RoundEnvironment): Boolean {
        roundEnvironment.getElementsAnnotatedWith(annotationType.java).forEach { element ->
            if (process(element, element.getAnnotation(annotationType.java)) == PROCESS_FAILURE) {
                return PROCESS_FAILURE
            }
        }
        return PROCESS_SUCCESS
    }

    final override fun getSupportedAnnotationTypes(): Set<String> =
        setOf(annotationType.java.canonicalName)
}
