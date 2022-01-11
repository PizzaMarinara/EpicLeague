package dev.efantini.epicleague

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun `sanity test`() {
        for (k in 0..20) {
            println(k.and(4)) // .and = Python's & operator
        }
        for (k in 0..20) {
            println(k.xor(1)) // .xor = Python's ^ operator
        }
        for ((i, j) in (10..15 step 15 - 10).zip(7..8 step 8 - 7)) {
            println("values are: $i, $j")
        }

        val foo = mutableListOf(
            mutableListOf("A", "B", "C"),
            mutableListOf("D", "E", "F")
        )

        println(foo[1].slice(1 until foo[1].size))
        assertEquals(4, 2 + 2)
    }
}
