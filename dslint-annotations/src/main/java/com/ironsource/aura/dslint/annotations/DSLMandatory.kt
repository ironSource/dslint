package com.ironsource.aura.dslint.annotations

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.PROPERTY_SETTER)
annotation class DSLMandatory(val group: String = "")