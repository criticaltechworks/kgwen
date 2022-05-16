package com.criticaltechworks.kgwen.processor

import com.criticaltechworks.kgwen.annotation.KGwenSubject
import com.criticaltechworks.kgwen.processor.CodeGen.SUBJECT_PACKAGE
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.*

/**
 * An annotation processor for KGwen test subjects.
 */
@Suppress("UNUSED")
@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_11)
internal class SubjectProcessor : SingleAnnotationProcessor<KGwenSubject>(annotationType = get()) {
    override fun process(element: Element, annotation: KGwenSubject): Boolean {
        require(element is TypeElement && element.kind.isClass) {
            "KGwen verbs must be objects but '${element.simpleName}' is not."
        }

        val roles = rolesOf(element)
        val file = fileFor(element, roles.map { subjectUsageFor(role = it, element) })
        file.writeTo(processingEnv.filer)

        return PROCESS_SUCCESS
    }

    private fun fileFor(subject: TypeElement, properties: List<PropertySpec>) = FileSpec
        .builder(
            packageName = packageOf(subject).toString(),
            fileName = "${subject.simpleName}Usage"
        )
        .apply { properties.forEach { addProperty(it) } }
        .build()

    private fun rolesOf(subject: TypeElement): Set<Role> {
        val roles = mutableSetOf<Role>()
        fun findRole(type: TypeElement) {
            type.interfaces.forEach { interfaceMirror ->
                val typeInterface = processingEnv.typeUtils.asElement(interfaceMirror)
                val role = Role.from(typeInterface)

                if (role != null) roles.add(role) else findRole(typeInterface as TypeElement)
            }
        }

        findRole(subject)
        return roles
    }

    @OptIn(DelicateKotlinPoetApi::class)
    private fun subjectUsageFor(role: Role, element: TypeElement): PropertySpec {
        val subjectName = element.simpleName.toString().replaceFirstChar { it.lowercase() }
        val returnType = ClassName(packageName = SUBJECT_PACKAGE, role.toString())
            .parameterizedBy(element.asType().asTypeName())
        val receiverType = ClassName(packageName = SUBJECT_PACKAGE, role.scope)

        return PropertySpec
            .builder(name = subjectName, returnType)
            .addKdoc("Sliced [${element.simpleName}] with [$role] only.")
            .receiver(receiverType)
            .getter(
                FunSpec.getterBuilder()
                    .addStatement("return ${element.simpleName}")
                    .build()
            )
            .build()
    }
}
