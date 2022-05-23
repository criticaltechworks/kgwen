package com.criticaltechworks.kgwen.processor

import com.criticaltechworks.kgwen.annotation.KGwenKeyword
import com.criticaltechworks.kgwen.processor.CodeGen.KGWEN_NODE
import com.criticaltechworks.kgwen.processor.CodeGen.NODE_OPERATION
import com.criticaltechworks.kgwen.processor.CodeGen.NODE_PACKAGE
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.*

/**
 * An annotation processor for KGwen keywords.
 */
@Suppress("UNUSED")
@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_11)
internal class KeywordProcessor : SingleAnnotationProcessor<KGwenKeyword>(annotationType = get()) {
    override fun process(element: Element, annotation: KGwenKeyword): Boolean {
        require(element is TypeElement && element.kind.isInterface) {
            "KGwen keywords must be interfaces but '${element.simpleName}' is not."
        }

        val isNodeOperation = element.interfaces.any { it.toString().contains(NODE_OPERATION) }
        require(isNodeOperation) {
            "KGwen keywords must conform to 'NodeOperation' but '${element.simpleName}' does not."
        }

        val file = createFileFor(keywordElement = element)
        file.writeTo(processingEnv.filer)

        return PROCESS_SUCCESS
    }

    /** Creates a file with codegen for the usage of [keywordElement]. */
    private fun createFileFor(keywordElement: TypeElement) = FileSpec
        .builder(
            packageName = packageOf(keywordElement).toString(),
            fileName = "${keywordElement.simpleName}Usage"
        )
        .addFunction(keywordUsageFor(keywordElement))
        .build()

    /** Create the keyword usage of [keywordElement] as an infix extension on `KGwenNode`. */
    @OptIn(ExperimentalKotlinPoetApi::class, DelicateKotlinPoetApi::class)
    private fun keywordUsageFor(keywordElement: TypeElement): FunSpec {
        val keywordName = keywordElement.simpleName.toString().replaceFirstChar { it.lowercase() }
        val kgwenNodeType = ClassName(packageName = NODE_PACKAGE, KGWEN_NODE)

        return FunSpec.builder(name = keywordName)
            .addKdoc("Chain two nodes using `$keywordName` keyword")
            .addModifiers(KModifier.INFIX)
            .receiver(kgwenNodeType)
            .contextReceivers(keywordElement.asClassName())
            .addParameter(
                ParameterSpec.builder(name = "other", type = kgwenNodeType)
                    .build()
            )
            .returns(kgwenNodeType)
            .addStatement("return other")
            .build()
    }
}
