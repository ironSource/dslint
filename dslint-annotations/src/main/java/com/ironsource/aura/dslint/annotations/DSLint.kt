package com.ironsource.aura.dslint.annotations

/**
 * DSLint library annotation.
 * Use on a DSL class/interface that you want DSLint to verify.
 */
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS)
@DslMarker
annotation class DSLint