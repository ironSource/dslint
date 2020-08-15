package com.ironsource.aura.dslint.utils

import com.intellij.psi.PsiAnnotation
import org.jetbrains.uast.UExpression
import org.jetbrains.uast.toUElement

@Suppress("UNCHECKED_CAST")
fun <T> PsiAnnotation?.resolveAttributeValue(
    attribute: String
) = (this?.findAttributeValue(attribute).toUElement() as UExpression?)?.evaluate() as T?

@Suppress("UNCHECKED_CAST")
fun PsiAnnotation?.resolveStringAttributeValue(
    attribute: String
) = resolveAttributeValue<Any>(attribute)?.toString()