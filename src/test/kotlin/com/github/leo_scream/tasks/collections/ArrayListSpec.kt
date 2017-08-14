import com.github.leo_scream.tasks.collections.ArrayList
import org.jetbrains.spek.subject.SubjectSpek

import org.jetbrains.spek.subject.itBehavesLike

import com.github.leo_scream.tasks.collections.ListSpec
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
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
        val dataForSorting: Array<Data3<String, String, (String, String) -> Int, String>> = arrayOf(
                data("6, 5, 4, 3, 2, 1", "natural", { a, b -> a.compareTo(b) }, expected = "sorted array"),
                data("1, 2, 3, 4, 5, 6", "natural", { a, b -> a.compareTo(b) }, expected = "sorted array"),
                data("4, 3, 4, 3, 3, 4", "natural", { a, b -> a.compareTo(b) }, expected = "sorted array"),
                data("6, 5, 4, 3, 2, 1", "reverse", { a, b -> b.compareTo(a) }, expected = "sorted array"),
                data("1, 2, 3, 4, 5, 6", "reverse", { a, b -> b.compareTo(a) }, expected = "sorted array"),
                data("4, 3, 4, 3, 3, 4", "reverse", { a, b -> b.compareTo(a) }, expected = "sorted array"),
                data("6, 5, 4, 3, 2, 1", "custom", { a, b -> a.toInt().compareTo(b.toInt() - 3) }, expected = "sorted array"),
                data("1, 2, 3, 4, 5, 6", "custom", { a, b -> a.toInt().compareTo(b.toInt() - 3) }, expected = "sorted array"),
                data("4, 3, 4, 3, 3, 4", "custom", { a, b -> a.toInt().compareTo(b.toInt() - 3) }, expected = "sorted array")
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

        on("sorting %s in %s order",
                *dataForSorting)
        { stringArray, orderingName, comparator, _ ->
            subject.clear()

            val array = stringArray.split(", ")
            array.forEach(subject::add)
            subject.sort(comparator)

            it("should be the same size") {
                assertEquals(array.size, subject.size())
            }

            it("should be sorted in $orderingName") {
                var curVal = subject.get(0)
                subject.forEach { value ->
                    assertTrue { comparator(value, curVal) >= 0 }
                    curVal = value
                }
            }
        }

        context("sorted array list") {
            val sortedLetters = arrayOf("a", "b", "c", "d", "e")
            sortedLetters.forEach(subject::add)

            on("binary search letter %s in ${Arrays.toString(sortedLetters)}",
                    *dataForBinarySearch) { letter, expected ->
                it("should return $expected") {
                    assertEquals(expected, subject.binarySearch(letter))
                }
            }
        }

    }
})