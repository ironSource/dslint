package com.ironsource.aura.dslint.utils

import org.jetbrains.kotlin.psi.KtExpression
import org.jetbrains.uast.UExpression
import org.jetbrains.uast.toUElement

@Suppress("UNCHECKED_CAST")
fun <T> KtExpression?.evaluate() = (this.toUElement() as UExpression?)?.evaluate() as T?