package com.ironsource.aura.dslint.utils

fun String?.nullIfEmpty() = if (this.isNullOrEmpty()) null else this