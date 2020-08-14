package com.ironsource.aura.dslint.utils

import com.intellij.psi.PsiAnnotation
import org.jetbrains.kotlin.asJava.elements.KtLightElement
import org.jetbrains.kotlin.psi.KtAnnotationEntry
import org.jetbrains.kotlin.psi.KtValueArgument
import org.jetbrains.kotlin.psi.ValueArgument

@Suppress("UNCHECKED_CAST")
fun PsiAnnotation?.resolveDeclaredStringAttributeValue(
    attribute: String
): String? {
    val valueExpression =
        (((this as KtLightElement<*, *>).kotlinOrigin as KtAnnotationEntry)
            .findAttributeValue(attribute) as? KtValueArgument)?.getArgumentExpression()

    valueExpression ?: return null

    val value = valueExpression.text
    return value.substring(1 until value.length-1)
}

private fun KtAnnotationEntry.findAttributeValue(attributeName: String): ValueArgument? {
    return valueArguments.find {
        it.getArgumentName()?.asName?.identifier == attributeName
    }
}