package com.github.bun133.tinked.utils

class Future<R> {
    private var isSubmitted = false
    private var result: R? = null

    fun isSubmitted(): Boolean {
        return isSubmitted
    }

    fun setResult(r: R?) {
        result = r
        isSubmitted = true
    }

    fun getResult(): R? {
        return result
    }
}