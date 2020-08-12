package com.ironsource.aura.dslint.detectors

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.isJava
import com.intellij.psi.PsiClass
import com.ironsource.aura.dslint.DSLintAnnotation
import com.ironsource.aura.dslint.utils.getReceiverType
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UElement
import org.jetbrains.uast.ULambdaExpression
import javax.annotation.Nonnull
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@ExperimentalContracts
abstract class DSLintDetector : Detector(),
    Detector.UastScanner {

    override fun getApplicableUastTypes(): List<Class<out UElement?>> {
        return listOf<Class<out UElement?>>(ULambdaExpression::class.java)
    }

    final override fun createUastHandler(@Nonnull context: JavaContext): UElementHandler? {
        val psi = context.uastFile?.sourcePsi
        psi ?: return null
        if (isJava(psi)) {
            return null
        }

        return object : UElementHandler() {

            override fun visitLambdaExpression(node: ULambdaExpression) {
                try {
                    // Looking for lambdas passed as arguments
                    val callExpression = node.uastParent
                    if (callExpression !is UCallExpression) {
                        return
                    }

                    val lambdaReceiverType = node.getReceiverType(callExpression)
                    if (!lambdaReceiverType.isDSLintClass()) {
                        return
                    }

                    visitDslLintLambda(context, node, lambdaReceiverType!!)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    abstract fun visitDslLintLambda(
        context: JavaContext,
        node: ULambdaExpression,
        dslLintClass: PsiClass
    )
}

@ExperimentalContracts
private fun PsiClass?.isDSLintClass(): Boolean {
    contract {
        returns(true) implies (this@isDSLintClass != null)
    }
    return this != null && this.hasAnnotation(DSLintAnnotation.DSLint.name)
}