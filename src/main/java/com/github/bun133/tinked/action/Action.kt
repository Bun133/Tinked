package com.github.bun133.tinked.action

/**
 * Action Class express an action that takes tick(s) to execute/complete.
 * And all actions are able to convert into a [com.github.bun133.tinked.task.TickedTask]
 *
 * @param R the return type of the action
 */
interface Action<I,R : Any> {
    /**
     * returns ticked task that executes this action.
     */
    fun toTickedTask(): TickedTask<I,R>

    /**
     * This method is not always needed to be implemented.
     * If the action can't be done in one tick, it should return null and terminate the action.
     */
    fun executeAtOnce(i:I): R?
}