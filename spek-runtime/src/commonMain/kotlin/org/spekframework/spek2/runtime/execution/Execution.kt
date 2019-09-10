package org.spekframework.spek2.runtime.execution

import org.spekframework.spek2.lifecycle.ExecutionResult
import org.spekframework.spek2.runtime.scope.GroupScopeImpl
import org.spekframework.spek2.runtime.scope.ScopeImpl
import org.spekframework.spek2.runtime.scope.TestScopeImpl

data class ExecutionRequest(val roots: List<ScopeImpl>, val executionListener: ExecutionListener)

interface ExecutionListener {
    fun executionStart()
    fun executionFinish()

    fun testExecutionStart(test: TestScopeImpl)
    fun testExecutionFinish(test: TestScopeImpl, result: ExecutionResult)
    fun testIgnored(test: TestScopeImpl, reason: String?)

    fun groupExecutionStart(group: GroupScopeImpl)
    fun groupExecutionFinish(group: GroupScopeImpl, result: ExecutionResult)
    fun groupIgnored(group: GroupScopeImpl, reason: String?)
}
