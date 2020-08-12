package com.hananrh.dslint.sample

import com.ironsource.aura.dslint.annotations.DSLMandatory
import com.ironsource.aura.dslint.annotations.DSLint

fun dslTest(block: DslTest.() -> Unit) = DslTestImpl().apply(block)

@DSLint
interface DslTest {

    @set:DSLMandatory(group = "prop")
    var prop: String

    @set:DSLMandatory(group = "prop")
    var prop2: String
}

class DslTestImpl : DslTest {
    override lateinit var prop: String
    override lateinit var prop2: String
}