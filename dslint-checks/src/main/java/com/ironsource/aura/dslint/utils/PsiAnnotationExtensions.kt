package com.ironsource.aura.dslint.utils

import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiAnnotation

@Suppress("UNCHECKED_CAST")
fun <T> PsiAnnotation?.resolveAttributeValue(
    attribute: String
): T? {
    val attributeValue = this?.findAttributeValue(attribute)
    attributeValue ?: return null

    return JavaPsiFacade.getInstance(
        attributeValue.project
    ).constantEvaluationHelper.computeConstantExpression(attributeValue) as T
}