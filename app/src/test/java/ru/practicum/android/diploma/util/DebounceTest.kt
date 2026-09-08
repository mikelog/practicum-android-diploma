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

    @Test
    fun `clickDebounce runs the first call immediately`() = runTest {
        val received = mutableListOf<Int>()
        val onClick = clickDebounce<Int>(this, delayMillis = 1000L) { received.add(it) }

        onClick(1)
        runCurrent()

        assertEquals(listOf(1), received)
    }

    @Test
    fun `clickDebounce ignores calls inside the blocking window`() = runTest {
        val received = mutableListOf<Int>()
        val onClick = clickDebounce<Int>(this, delayMillis = 1000L) { received.add(it) }

        onClick(1)
        advanceTimeBy(500L)
        onClick(2)
        advanceTimeBy(499L)
        onClick(3)
        advanceUntilIdle()

        assertEquals(listOf(1), received)
    }

    @Test
    fun `clickDebounce allows the next call after the window elapses`() = runTest {
        val received = mutableListOf<Int>()
        val onClick = clickDebounce<Int>(this, delayMillis = 1000L) { received.add(it) }

        onClick(1)
        advanceTimeBy(1001L)
        onClick(2)
        advanceUntilIdle()

        assertEquals(listOf(1, 2), received)
    }
}
