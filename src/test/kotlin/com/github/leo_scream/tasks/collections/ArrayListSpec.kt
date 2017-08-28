import com.github.leo_scream.tasks.collections.ArrayList
import org.jetbrains.spek.subject.SubjectSpek

import org.jetbrains.spek.subject.itBehavesLike

import com.github.leo_scream.tasks.collections.ListSpec
import org.jetbrains.spek.api.dsl.*
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.data_driven.Data3
import org.jetbrains.spek.data_driven.data
import org.jetbrains.spek.data_driven.on
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

object ArrayListSpec : SubjectSpek<ArrayList<String>>({
    subject { ArrayList.of() }

    itBehavesLike(ListSpec)

    describe("array list") {
        val strings = arrayOf("a", "b", "c")
        val dataForSorting: Array<Data3<String, Array<Int>, (String, String) -> Int, Array<Int>>> = arrayOf(
                data("natural", arrayOf(6, 5, 4, 3, 2, 1), { a, b -> a.compareTo(b) }, expected = arrayOf(1, 2, 3, 4, 5, 6)),
                data("natural", arrayOf(1, 2, 3, 4, 5, 6), { a, b -> a.compareTo(b) }, expected = arrayOf(1, 2, 3, 4, 5, 6)),
                data("natural", arrayOf(4, 3, 4, 3, 3, 4), { a, b -> a.compareTo(b) }, expected = arrayOf(3, 3, 3, 4, 4, 4)),
                data("reverse", arrayOf(6, 5, 4, 3, 2, 1), { a, b -> b.compareTo(a) }, expected = arrayOf(6, 5, 4, 3, 2, 1)),
                data("reverse", arrayOf(1, 2, 3, 4, 5, 6), { a, b -> b.compareTo(a) }, expected = arrayOf(6, 5, 4, 3, 2, 1)),
                data("reverse", arrayOf(4, 3, 4, 3, 3, 4), { a, b -> b.compareTo(a) }, expected = arrayOf(4, 4, 4, 3, 3, 3))
        )
        val dataForBinarySearch = arrayOf(
                data("a", expected = 0),
                data("b", expected = 1),
                data("c", expected = 2),
                data("d", expected = 3),
                data("e", expected = 4)
        )

        beforeEachTest { strings.forEach(subject::add) }
        afterEachTest(subject::clear)

        on("getting the capacity") {
            val capacity = subject.capacity

            it("it should be more or equals to size") {
                assertTrue(capacity >= strings.size)
            }
        }

        on("trimming to size") {
            subject.trimToSize()

            it("should reduce capacity to size") {
                assertEquals(strings.size, subject.size())
            }
        }

        on("sorting in %s order",
                *dataForSorting)
        { ordering, array, comparator, expected ->
            subject.clear()

            array.map(Int::toString)
                    .forEach(subject::add)

            subject.sort(comparator)

            it("should transform ${Arrays.toString(array)} to ${Arrays.toString(expected)}") {

                it("should be the same size as before") {
                    assertEquals(array.size, subject.size())
                }

                expected.map(Int::toString)
                        .forEachIndexed { i, expectedValue ->
                            assertTrue { expectedValue.equals(subject.get(i)) }
                        }
            }
        }

        val sortedLetters = arrayOf("a", "b", "c", "d", "e")

        given("sorted array list ${Arrays.toString(sortedLetters)}") {
            sortedLetters.forEach(subject::add)

            on("binary search %s",
                    *dataForBinarySearch) { letter, expected ->
                it("should return $expected") {
                    assertEquals(expected, subject.binarySearch(letter))
                }
            }
        }

    }
})