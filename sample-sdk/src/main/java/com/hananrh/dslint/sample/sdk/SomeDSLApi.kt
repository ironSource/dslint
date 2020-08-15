package com.hananrh.dslint.sample.sdk

import com.ironsource.aura.dslint.annotations.DSLMandatory
import com.ironsource.aura.dslint.annotations.DSLint

fun dslLibraryTest(block: SomeLibraryDSLApi.() -> Unit) = SomeLibraryDslApiImpl()
    .apply(block)

@DSLint
interface SomeLibraryDSLApi {

    object Group {
        const val NAME = "name"
    }

    @set:DSLMandatory(message = "ID please")
    var id: String

    @set:DSLMandatory(group = Group.NAME)
    var firstName: String

    @set:DSLMandatory(group = Group.NAME)
    var fullName: String

    var optionalProp: String

    @DSLMandatory
    fun inner(block: InnerLibraryDSL.() -> Unit)
}

class SomeLibraryDslApiImpl : SomeLibraryDSLApi {
    override lateinit var id: String
    override lateinit var firstName: String
    override lateinit var fullName: String
    override lateinit var optionalProp: String
    override fun inner(block: InnerLibraryDSL.() -> Unit) {

    }
}

@DSLint
interface InnerLibraryDSL {

    @set:DSLMandatory
    var innerProp: String

    @DSLMandatory
    fun innerProp2()
}

class InnerDSLImpl : InnerLibraryDSL {
    override lateinit var innerProp: String
    override fun innerProp2() {

    }
}