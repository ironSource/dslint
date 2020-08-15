package com.hananrh.dslint.sample

import com.ironsource.aura.dslint.annotations.DSLMandatory
import com.ironsource.aura.dslint.annotations.DSLint

fun dslTest(block: SomeDSLApi.() -> Unit) = SomeDslApiImpl().apply(block)

@DSLint
interface SomeDSLApi {

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
    fun inner(block: InnerDSL.() -> Unit)
}

class SomeDslApiImpl : SomeDSLApi {
    override lateinit var id: String
    override lateinit var firstName: String
    override lateinit var fullName: String
    override lateinit var optionalProp: String
    override fun inner(block: InnerDSL.() -> Unit) {

    }
}

@DSLint
interface InnerDSL {

    @set:DSLMandatory
    var innerProp: String

    @DSLMandatory
    fun innerProp2()
}

class InnerDSLImpl : InnerDSL {
    override lateinit var innerProp: String
    override fun innerProp2() {

    }
}