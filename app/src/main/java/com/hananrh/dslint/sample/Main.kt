package com.hananrh.dslint.sample

import com.hananrh.dslint.sample.sdk.dslLibraryTest

fun create() {
    dslTest {
        id = ""
        firstName = "Hanan"
    }

    dslLibraryTest {
    }
}