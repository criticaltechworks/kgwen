/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.processor

import com.criticaltechworks.kgwen.annotation.*
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.*
import javax.lang.model.type.MirroredTypeException

/**
 * An annotation processor for KGwen objects.
 */
@Suppress("UNUSED")
@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_11)
internal class ObjectProcessor : RepeatableAnnotationProcessor<KGwenObject, KGwenObjects>(
    annotationType = get(),
    annotationContainerType = get()
) {
    override fun processContainer(element: Element, annotation: KGwenObjects): Boolean {
        annotation.value.forEach {
            if (process(element, it) == PROCESS_FAILURE) return PROCESS_FAILURE
        }
        return PROCESS_SUCCESS
    }

    override fun process(element: Element, annotation: KGwenObject): Boolean {
        require(element is ExecutableElement) {
            "Error in ${objectDescription(annotation.name)}: KGwen objects must be declared " +
                    "as functions."
        }
        require(element.isParameterless()) {
            "Error in ${objectDescription(annotation.name)}: KGwen objects must be declared " +
                    "as parameterless functions."
        }

        val subjectElement = subjectFrom(annotation)
        val verbElement = verbFrom(annotation)
        val objectDescription = objectDescription(annotation.name, subjectElement, verbElement)

        require(subjectElement is TypeElement) { "Error in ${objectDescription}: invalid subject" }
        require(verbElement is TypeElement) { "Error in ${objectDescription}: invalid verb" }
        require(verbElement.hasVerbAnnotation()) {
            "KGwen object with annotation $objectDescription requires that " +
                    "verb '$verbElement' is annotated with '@${KGwenVerb::class.simpleName}'"
        }

        val verbRole = rolesOf(verbElement).first()
        val subjectRoles = rolesOf(subjectElement)

        require(subjectRoles.contains(verbRole)) {
            "Error in KGwen object with annotation $objectDescription: " +
                    "Verb '${verbElement.simpleName}' requires role $verbRole but " +
                    "subject '${subjectElement.simpleName}' only has role(s) $subjectRoles "
        }

        val objectUsage = objectUsageFor(
            element = element,
            annotation = annotation,
            subjectElement = subjectElement,
            verbElement = verbElement
        )

        val file = createFileFor(
            subject = subjectElement,
            verb = verbElement,
            objectName = annotation.name,
            packageName = packageOf(element).toString(),
            propertySpec = objectUsage
        )
        file.writeTo(processingEnv.filer)

        return PROCESS_SUCCESS
    }

    /** @return The verb [Element] in the [annotation]. */
    private fun verbFrom(annotation: KGwenObject): Element {
        val verb: Element
        try {
            annotation.verb
            error("Unknown error in ${objectDescription(annotation.name)}")
        } catch (e: MirroredTypeException) {
            val extractedVerbType = processingEnv.typeUtils.asElement(e.typeMirror)
            checkNotNull(extractedVerbType) {
                "Error in ${objectDescription(annotation.name)}: invalid verb"
            }
            verb = extractedVerbType
        }
        return verb
    }

    /** @return The subject [Element] in the [annotation]. */
    private fun subjectFrom(annotation: KGwenObject): Element {
        val subject: Element
        try {
            annotation.subject
            error("Unknown error in ${objectDescription(annotation.name)}")
        } catch (e: MirroredTypeException) {
            val extractedSubjectType = processingEnv.typeUtils.asElement(e.typeMirror)
            checkNotNull(extractedSubjectType) {
                "Error in ${objectDescription(annotation.name)}: invalid subject"
            }
            subject = extractedSubjectType
        }
        return subject
    }

    /** @return The [Role](s) defined for the verb or subject [element]. */
    private fun rolesOf(element: TypeElement): Set<Role> {
        val roles = mutableSetOf<Role>()
        fun findRole(type: TypeElement) {
            type.interfaces.forEach { interfaceMirror ->
                val typeInterface = processingEnv.typeUtils.asElement(interfaceMirror)
                val role = Role.from(typeInterface)
                if (role != null) roles.add(role) else findRole(typeInterface as TypeElement)
            }
        }

        findRole(element)
        return roles
    }

    /** @return true if this [TypeElement] conforms to `NodeOperation` interface. */
    private fun TypeElement.supportsNodeOperations(): Boolean {
        var result = false
        fun checkSupportsNodeOperations(type: TypeElement) {
            type.interfaces.forEach { interfaceMirror ->
                val typeInterface = processingEnv.typeUtils.asElement(interfaceMirror)
                if (typeInterface.simpleName.toString() == NODE_OPERATION) {
                    result = true
                }
                checkSupportsNodeOperations(typeInterface as TypeElement)
            }
        }

        checkSupportsNodeOperations(this)
        return result
    }

    /** A readable description for a `@KGwenObject` annotated element. */
    private fun objectDescription(name: String, subject: Element? = null, verb: Element? = null) =
        "${KGwenObject::class.simpleName}(name=\"$name\"" +
                (subject?.let { ", subject=" + it.simpleName.toString() + "::class" } ?: "") +
                (verb?.let { ", verb=" + it.simpleName.toString() + "::class" } ?: "") + ")"

    /**
     * Create the object usage as an extension property on the [verbElement] parameterized by
     * [subjectElement].
     */
    @OptIn(ExperimentalKotlinPoetApi::class)
    private fun objectUsageFor(
        element: Element,
        annotation: KGwenObject,
        subjectElement: TypeElement,
        verbElement: TypeElement
    ): PropertySpec {
        val verbType =
            ClassName(packageOf(verbElement).toString(), verbElement.simpleName.toString())
                .parameterizedBy(
                    ClassName(
                        packageOf(subjectElement).toString(),
                        subjectElement.simpleName.toString()
                    )
                )
        val contextReceivers = element.toString()
            .extractParenthesizedArguments()
            .toMutableList()
        val objectReceiver = contextReceivers
            .lastOrNull()
            .takeIf { element.modifiers.contains(Modifier.STATIC) }
        objectReceiver?.let { contextReceivers.remove(it) }
        val contextReceiverTypes = contextReceivers.map { ClassName("", it) }

        val functionCall = (if (objectReceiver != null) "$objectReceiver." else "") +
                element.simpleName.toString() + "()"
        val isNode = verbElement.supportsNodeOperations() && annotation.allowsNodeOperations
        val block = "return " + if (isNode) "node { $functionCall } " else functionCall
        val returns = if (isNode) ClassName(NODE_PACKAGE, KGWEN_NODE) else Unit::class.asTypeName()

        return PropertySpec
            .builder(name = annotation.name, returns)
            .receiver(verbType)
            .contextReceivers(contextReceiverTypes)
            .getter(
                FunSpec.getterBuilder()
                    .addStatement(block)
                    .build()
            )
            .build()
    }

    /**
     * Create a file with codegen for the usage of the `@KGwenObject` annotated element using the
     * provided [objectName].
     * */
    private fun createFileFor(
        subject: Element,
        verb: Element,
        objectName: String,
        packageName: String,
        propertySpec: PropertySpec
    ): FileSpec {
        val fileName = subject.simpleName.toString() +
                verb.simpleName.toString() +
                objectName.replaceFirstChar { it.uppercase() }

        return FileSpec
            .builder(packageName, fileName)
            .addProperty(propertySpec)
            .build()
    }

    // TODO: improve check to allow non-annotated custom verbs
    /**
     * Check whether the this [Element] as a verb annotation (for custom verbs) or is a library
     * implemented verb available in [VERB_PACKAGE].
     */
    private fun Element.hasVerbAnnotation() = getAnnotation(KGwenVerb::class.java) != null ||
            packageOf(this).qualifiedName.toString() == VERB_PACKAGE

    private companion object {
        const val VERB_PACKAGE = "com.criticaltechworks.kgwen.verbs"
        const val NODE_PACKAGE = "com.criticaltechworks.kgwen.nodes"
        const val NODE_OPERATION = "NodeOperation"
        const val KGWEN_NODE = "KGwenNode"

        /** @return true if this function or method has zero parameters or false otherwise. */
        fun ExecutableElement.isParameterless() =
            parameters.filterNot { it.simpleName.contains("this") }.isEmpty()

        /**
         * Split and extract substrings from an enumeration of type:
         * ```
         * "prefix(subStr1, subStr2, ...)suffix"
         * ```
         */
        fun String.extractParenthesizedArguments() = substringAfter("(")
            .substringBefore(")")
            .split(",")
    }
}
