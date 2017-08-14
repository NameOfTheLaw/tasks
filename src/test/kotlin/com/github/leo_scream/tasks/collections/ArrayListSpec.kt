import com.github.leo_scream.tasks.collections.ArrayList
import org.jetbrains.spek.subject.SubjectSpek

import org.jetbrains.spek.subject.itBehavesLike

import com.github.leo_scream.tasks.collections.ListSpec
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.data_driven.data
import org.jetbrains.spek.data_driven.on
import kotlin.test.assertEquals
import kotlin.test.assertTrue

object ArrayListSpec : SubjectSpek<ArrayList<String>>({
    subject { ArrayList.of() }

    itBehavesLike(ListSpec)

    describe("array list") {
        val strings = arrayOf("a", "b", "c")

        beforeEachTest { strings.forEach { subject.add(it) } }
        afterEachTest { subject.clear() }

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

        context("unsorted array list") {

            on("sorting %s",
                    data("6, 5, 4, 3, 2, 1", expected = "sorted array"),
                    data("1, 2, 3, 4, 5, 6", expected = "sorted array"),
                    data("4, 3, 4, 3, 3, 4", expected = "sorted array"))
            { stringArray, _ ->
                val array = stringArray.split(", ")
                array.forEach(subject::add)
                subject.sort()

                it("should be the same size") {
                    assertEquals(array.size, subject.size())
                }

                it("should be sorted") {
                    var curVal = Int.MIN_VALUE.toString()
                    subject.forEach { value ->
                        assertTrue { value >= curVal }
                        curVal = value
                    }
                }
            }
        }
    }
})