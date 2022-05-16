/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.processor

import com.criticaltechworks.kgwen.annotation.KGwenVerb
import com.criticaltechworks.kgwen.processor.CodeGen.ARRANGER_ROLE
import com.criticaltechworks.kgwen.processor.CodeGen.SUBJECT_PACKAGE
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.plusParameter
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.*
import javax.lang.model.type.TypeMirror

/**
 * An annotation processor for KGwen verbs.
 */
@Suppress("UNUSED")
@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_11)
internal class VerbProcessor : SingleAnnotationProcessor<KGwenVerb>(annotationType = get()) {
    override fun process(element: Element, annotation: KGwenVerb): Boolean {
        check(element is TypeElement && element.kind.isInterface) {
            "KGwen verbs must be interfaces but '${element.simpleName}' is not."
        }

        val name = verbNameFor(element)
        val type = verbTypeFor(element)
        val blockType = objectBlockTypeFor(type)
        val role = roleFor(verb = element)
        checkNotNull(role) {
            "'${element.simpleName}' must conform to either 'ActorRole' or 'AsserterRole'"
        }

        val interactions = if (role.toString().contains("Actor")) "actions" else "assertions"
        val verbUsage = verbUsageFor(element, type, name, blockType, role, interactions)
        val arrangerUsage = arrangerUsageFor(element, type, name, blockType, interactions)
            .takeIf { annotation.inArrangerScope }

        val file = fileFor(verb = element, functions = listOfNotNull(verbUsage, arrangerUsage))
        file.writeTo(processingEnv.filer)

        return PROCESS_SUCCESS
    }

    private fun verbNameFor(verb: TypeElement) =
        verb.simpleName.toString().replaceFirstChar { it.lowercase() }

    private fun verbTypeFor(verb: TypeElement) =
        ClassName(packageName = packageOf(verb).toString(), verb.simpleName.toString())
            .plusParameter(T_TYPE)

    private fun objectBlockTypeFor(verbType: TypeName) =
        LambdaTypeName.get(receiver = verbType, returnType = Unit::class.asTypeName())

    private fun TypeMirror.hasActorOrAsserterRole() =
        with(toString()) { contains("ActorRole<T>") || contains("AsserterRole<T>") }

    private fun roleFor(verb: TypeElement) = verb.interfaces
        .filter { it.hasActorOrAsserterRole() }
        .run { first().takeIf { count() == 1 } }

    private fun fileFor(verb: TypeElement, functions: List<FunSpec>) = FileSpec
        .builder(packageName = packageOf(verb).toString(), fileName = verb.simpleName.toString())
        .apply { functions.forEach { addFunction(it) } }
        .build()

    @OptIn(DelicateKotlinPoetApi::class)
    private fun verbUsageFor(
        verb: TypeElement,
        type: TypeName,
        name: String,
        blockType: LambdaTypeName,
        role: TypeMirror,
        interactions: String
    ) = FunSpec
        .builder(name = name)
        .addKdoc("Opens a block for stating [${verb.simpleName}] $interactions.")
        .addModifiers(KModifier.INFIX)
        .addTypeVariable(T_TYPE.copy(bounds = listOf(type)))
        .receiver(role.asTypeName())
        .addParameter("block", blockType)
        .returns(role.asTypeName())
        .addStatement("block(this as %T)", type)
        .addStatement("return this")
        .build()

    private fun arrangerUsageFor(
        verb: TypeElement,
        type: TypeName,
        name: String,
        blockType: LambdaTypeName,
        interactions: String
    ): FunSpec {
        val arrangerType = ClassName(packageName = SUBJECT_PACKAGE, ARRANGER_ROLE)
            .plusParameter(T_TYPE)

        return FunSpec
            .builder(name = name)
            .addKdoc("Opens a block for stating [${verb.simpleName}] $interactions.")
            .addModifiers(KModifier.INFIX)
            .addTypeVariable(T_TYPE.copy(bounds = listOf(type, arrangerType)))
            .receiver(arrangerType)
            .addParameter("block", blockType)
            .returns(arrangerType)
            .addStatement("@Suppress(\"UNCHECKED_CAST\") block(this as %T)", type)
            .addStatement("return this")
            .build()
    }

    private companion object {
        val T_TYPE = TypeVariableName("T")
    }
}
