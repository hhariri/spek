package org.spekframework.spek2.dsl

import org.spekframework.spek2.lifecycle.CachingMode
import org.spekframework.spek2.lifecycle.LifecycleListener
import org.spekframework.spek2.lifecycle.MemoizedValue
import org.spekframework.spek2.meta.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.lifecycle.LetValue

sealed class Skip {
    class Yes(val reason: String? = null) : Skip()
    object No : Skip()
}

@SpekDsl
interface Root : GroupBody {
    fun registerListener(listener: LifecycleListener)
    fun include(spek: Spek) = spek.root(this)
}

@SpekDsl
interface GroupBody : LifecycleAware, TestContainer {
    @Synonym(type = SynonymType.GROUP)
    @Descriptions(Description(DescriptionLocation.VALUE_PARAMETER, 0))
    fun group(description: String, skip: Skip = Skip.No, defaultCachingMode: CachingMode = CachingMode.INHERIT, preserveExecutionOrder: Boolean = false, failFast: Boolean = false, body: GroupBody.() -> Unit)
}

typealias Fixture = () -> Unit

@SpekDsl
interface LifecycleAware : ScopeBody {
    val defaultCachingMode: CachingMode

    fun <T> memoized(mode: CachingMode = defaultCachingMode, factory: () -> T): MemoizedValue<T>
    fun <T> memoized(mode: CachingMode = defaultCachingMode, factory: () -> T, destructor: (T) -> Unit): MemoizedValue<T>

    fun <T> value(factory: () -> T): LetValue.PropertyCreator<T>
    fun <T> value(letValue: LetValue<T>, factory: () -> T)

    fun beforeEachTest(fixture: Fixture)
    fun afterEachTest(fixture: Fixture)

    fun beforeEachGroup(fixture: Fixture)
    fun afterEachGroup(fixture: Fixture)

    fun beforeGroup(fixture: Fixture)
    fun afterGroup(fixture: Fixture)
}

interface ScopeBody {
    fun <T> memoized(): MemoizedValue<T>
}

@SpekDsl
interface TestContainer {
    var defaultTimeout: Long

    @Synonym(type = SynonymType.TEST)
    @Descriptions(Description(DescriptionLocation.VALUE_PARAMETER, 0))
    fun test(description: String, skip: Skip = Skip.No, timeout: Long = defaultTimeout, body: TestBody.() -> Unit)
}

@SpekDsl
interface TestBody : ScopeBody
