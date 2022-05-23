package com.criticaltechworks.kgwen.processor

import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.*
import javax.tools.Diagnostic
import kotlin.reflect.KClass

/**
 * An [AbstractProcessor] for annotations of type [T].
 */
internal abstract class AnnotationProcessor<T : Annotation>(
    protected val annotationType: KClass<T>
) : AbstractProcessor() {
    /**
     * Provides the annotation processing logic and returns the final processing status.
     * @param element An element with an annotation of type [T].
     * @param annotation The [element] annotation.
     * @return [PROCESS_SUCCESS] if the annotation processing succeeded, [PROCESS_FAILURE]
     * otherwise.
     */
    protected abstract fun process(element: Element, annotation: T): Boolean

    @Synchronized
    final override fun init(processingEnv: ProcessingEnvironment) {
        this.processingEnv = processingEnv
    }

    final override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latestSupported()

    /** Convenience method to retrieve a [PackageElement] for the provided [element]. */
    protected fun packageOf(element: Element): PackageElement =
        processingEnv.elementUtils.getPackageOf(element)

    /** Use [Messager] to print a [message] with [Diagnostic.Kind.NOTE] */
    protected fun printNote(message: String) {
        processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, message)
    }

    /** Use [Messager] to print a [message] with [Diagnostic.Kind.ERROR] */
    protected fun printError(message: String) {
        processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, message)
    }

    companion object {
        const val PROCESS_FAILURE = true
        const val PROCESS_SUCCESS = false

        /** Convenience function that creates a [KClass] for type [T]. */
        inline fun <reified T : Annotation> get() = T::class
    }
}
