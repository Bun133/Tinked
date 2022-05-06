package com.github.bun133.tinked.task


class TickedTaskNodeImpl<I : Any, R : Any>(
    val task: TickedTask<I, R>,
    val beforeTicked: TickedTaskNodeImpl<*, I>? = null,
    val nextTicked: TickedTaskNodeImpl<R, *>? = null
) {
    fun getAllTree(): List<TickedTaskNodeImpl<*, *>> {
        val r = getRoot()
        return (if (r === this) {
            collectTree()
        } else {
            r.getAllTree()
        }).toList()
    }

    private fun collectTree(): MutableList<TickedTaskNodeImpl<*, *>> {
        return if (nextTicked == null) {
            mutableListOf(this)
        } else {
            nextTicked.collectTree().also { it.add(this) }
        }
    }

    fun getRoot(): TickedTaskNodeImpl<*, *> {
        return beforeTicked?.getRoot() ?: this
    }

    fun runNextTicked(i: I): () -> TickedTaskResult<R> {
        val r = task.run(i)
        if (nextTicked != null) {
            return {
                nextTicked.runNextTicked(r)
                TickedTaskResult(false, null)
            }
        } else {
            return {
                TickedTaskResult(true, r)
            }
        }
    }
}

class TickedTaskResult<R : Any>(val isUp: Boolean, val returned: R?) {
}