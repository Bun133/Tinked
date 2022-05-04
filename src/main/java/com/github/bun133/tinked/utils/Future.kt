package com.github.bun133.tinked.utils

/**
 * Future class.
 *
 * This instance should be used only once.
 */
class Future<R> {
    private var isSubmitted = false
    private var result: R? = null
    private var listeners = mutableListOf<(R?) -> Unit>()

    fun isSubmitted(): Boolean {
        return isSubmitted
    }

    fun setResult(r: R?) {
        result = r
        isSubmitted = true
        callListeners(r)
    }

    fun getResult(): R? {
        return result
    }

    fun onValue(f: (R?) -> Unit) {
        if (isSubmitted) {
            f(result)
        } else {
            addListener(f)
        }
    }

    private fun addListener(f: (R?) -> Unit) {
        listeners.add(f)
    }

    private fun callListeners(r: R?) {
        listeners.forEach {
            it(r)
        }
    }
}