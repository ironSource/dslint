package com.hananrh.dslint.sample

fun test() {
    dslTest {
        prop = ""
        innerProp {
            innerProp = ""
            innerProp2()
        }
    }
}