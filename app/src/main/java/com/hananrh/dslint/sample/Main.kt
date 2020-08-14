package com.hananrh.dslint.sample

fun test() {
    dslTest {
        innerProp {
            innerProp = ""
            innerProp2()
        }
    }
}