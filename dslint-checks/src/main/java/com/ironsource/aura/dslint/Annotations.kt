package com.ironsource.aura.dslint

sealed class DSLintAnnotation(name: String) {
    val name = "$PACKAGE_NAME.$ANNOTATIONS_PACKAGE_NAME.$name"

    object DSLint : DSLintAnnotation("DSLint")

    object DslMandatory : DSLintAnnotation("DSLMandatory") {
        object Attributes {
            const val group = "group"
            const val message = "message"
        }
    }
}