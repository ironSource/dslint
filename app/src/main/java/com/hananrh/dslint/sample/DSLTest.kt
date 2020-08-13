package com.hananrh.dslint.sample

import com.ironsource.aura.dslint.annotations.DSLMandatory
import com.ironsource.aura.dslint.annotations.DSLint

fun dslTest(block: DSLTest.() -> Unit) = DslTestImpl().apply(block)

@DSLint
interface DSLTest {

    @set:DSLMandatory(group = "prop")
    var prop: String

    @set:DSLMandatory(group = "prop")
    var prop2: String

    var optionalProp: String

    @DSLMandatory
    fun innerProp(block: InnerDSL.() -> Unit)
}

class DslTestImpl : DSLTest {
    override lateinit var prop: String
    override lateinit var prop2: String
    override lateinit var optionalProp: String
    override fun innerProp(block: InnerDSL.() -> Unit) {

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