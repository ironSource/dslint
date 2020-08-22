package com.ironsource.aura.dslint.utils

import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiLiteral
import org.jetbrains.uast.UExpression
import org.jetbrains.uast.toUElement

@Suppress("UNCHECKED_CAST")
fun <T> PsiAnnotation?.resolveAttributeValue(
    attribute: String
): T? {
    val attributeValue = this?.findAttributeValue(attribute)
    // Try to read as literal first
    if (attributeValue is PsiLiteral) {
        attributeValue.value?.let { return it as T }
    }

    return (attributeValue.toUElement() as UExpression?)?.evaluate() as T?
}

@Suppress("UNCHECKED_CAST")
fun PsiAnnotation?.resolveStringAttributeValue(
    attribute: String
) = resolveAttributeValue<Any>(attribute)?.toString()