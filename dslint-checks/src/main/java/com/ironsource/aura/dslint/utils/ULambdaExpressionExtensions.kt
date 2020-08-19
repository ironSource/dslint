package com.ironsource.aura.dslint.utils

import com.intellij.psi.PsiClass
import com.intellij.psi.PsiClassType
import com.intellij.psi.PsiWildcardType
import com.intellij.psi.impl.source.PsiClassReferenceType
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.ULambdaExpression
import org.jetbrains.uast.getParameterForArgument

fun ULambdaExpression.getReceiverType(
    callExpression: UCallExpression
): PsiClass? {
    // TODO - for some reason this is sometimes null
    val parameter = callExpression.getParameterForArgument(this)?.type
    parameter ?: return null

    val receiverType = (parameter as PsiClassReferenceType).parameters.getOrNull(0)

    if (receiverType !is PsiWildcardType) {
        return null
    }

    return (receiverType.bound as PsiClassType).resolve()
}