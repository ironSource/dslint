package com.ironsource.aura.dslint.annotations

/**
 * DSLint library annotation.
 * Use on mandatory DSL properties setters or functions. Properties/functions annotated by this will generate a lint error if not defined in the DSL lambda.
 * @param group Optional group for property. DSLint will verify that at least one of the properties in the group is defined.
 * @param message Optional custom error message. If not supplied a default message will be shown.
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FUNCTION)
annotation class DSLMandatory(val group: String = "", val message: String = "")