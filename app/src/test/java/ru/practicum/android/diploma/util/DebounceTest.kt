package ru.practicum.android.diploma.util

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DebounceTest {

    @Test
    fun `action is not called before the delay elapses`() = runTest {
        var calls = 0
        val debounced = debounce<Int>(this, delayMillis = 1000L) { calls++ }

        debounced(1)
        advanceTimeBy(999L)
        runCurrent()
        assertEquals(0, calls)

        advanceUntilIdle()
        assertEquals(1, calls)
    }

    @Test
    fun `useLastParam true runs only the last call`() = runTest {
        val received = mutableListOf<Int>()
        val debounced = debounce<Int>(this, delayMillis = 1000L, useLastParam = true) { received.add(it) }

        debounced(1)
        advanceTimeBy(500L)
        debounced(2)
        advanceTimeBy(500L)
        debounced(3)
        advanceUntilIdle()

        assertEquals(listOf(3), received)
    }

    @Test
    fun `useLastParam false runs the first call and ignores the rest`() = runTest {
        val received = mutableListOf<Int>()
        val debounced = debounce<Int>(this, delayMillis = 1000L, useLastParam = false) { received.add(it) }

        debounced(1)
        debounced(2)
        debounced(3)
        advanceUntilIdle()

        assertEquals(listOf(1), received)
    }

    @Test
    fun `searchDebounce emits the settled value and skips duplicates`() = runTest {
        val result = flow {
            emit("a")
            emit("ab")
            delay(3000L)
            emit("abc")
            emit("abc")
            delay(3000L)
        }.searchDebounce(2000L).toList()

        assertEquals(listOf("ab", "abc"), result)
    }
}
