package com.github.leo_scream.tasks.collections

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.mock
import org.jetbrains.spek.api.dsl.*
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.data_driven.data
import org.jetbrains.spek.data_driven.on
import org.jetbrains.spek.subject.SubjectSpek
import java.lang.NullPointerException
import java.util.*
import java.util.function.Consumer
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

object CollectionSpec : SubjectSpek<Collection<String>>({

    describe("collection") {

        fun <V> getCollectionMock(array: Array<V>): Collection<V> =
                mock { collection ->
                    onGeneric { collection.forEach(any()) } doAnswer { invocationOnMock ->
                        val consumer = invocationOnMock.arguments.get(0) as Consumer<V>
                        array.forEach { consumer.accept(it) }
                    }
                }
        val strings = arrayOf("a", "b", "c")
        val stringsForOtherCollection = arrayOf("b", "c", "d")
        val otherCollection = getCollectionMock(stringsForOtherCollection)
        val dataForContainingAll = arrayOf(
                data(arrayOf("a", "b", "c"), expected = true),
                data(arrayOf("a", "b"), expected = true),
                data(arrayOf("a", "b", "c", "d"), expected = false),
                data(arrayOf("d", "g", "e"), expected = false),
                data(arrayOf("b", "c", "d"), expected = false))
        val dataForRemovingAll = arrayOf(
                data(arrayOf("a", "b", "c"), expected = emptyArray()),
                data(arrayOf("b", "c"), expected = arrayOf("a")),
                data(arrayOf("c", "d", "e"), expected = arrayOf("a", "b", "c"))
        )

        given("empty collection") {

            on("checking its size") {
                it("should return 0") { assertEquals(0, subject.size()) }
            }

            on("checking its emptiness") {
                it("should be empty") { assertTrue(subject.isEmpty()) }
            }

            on("contains") {
                it("should return false") { assertFalse(subject.contains("whatever")) }
            }

            on("addition") {
                val value = "value"
                subject.add(value)

                it("should contains added value") { assertTrue(subject.contains(value)) }

                it("should have the size of 1") { assertEquals(1, subject.size()) }

                it("should not be empty") { assertFalse(subject.isEmpty) }
            }
        }

        given("collection of ${Arrays.toString(strings)}") {

            beforeEachTest { strings.forEach(subject::add) }
            afterEachTest(subject::clear)

            on("contains its values") {
                it("should return true") {
                    strings.forEach { assertTrue(subject.contains(it)) }
                }
            }

            on("removing element that is not presented in the collection") {
                subject.remove("whatever")

                it("should not change the collection size") {
                    assertEquals(strings.size, subject.size())
                }
            }

            on("removing element that is presented in the collection") {
                val removedElement = strings[0]
                subject.remove(removedElement)

                it("should reduce collection size by 1") {
                    assertEquals(strings.size - 1, subject.size())
                }

                it("should remove '$removedElement' from collection") {
                    assertFalse(subject.contains(removedElement))
                }
            }

            on("removing by predicate") {
                val predicate: (String) -> Boolean = { it.equals(strings[0]) || it.equals(strings[1]) }
                subject.removeIf(predicate)

                it("should remove elements that satisfy the predicate") {
                    strings.forEach {
                        if (predicate(it)) assertFalse { subject.contains(it) }
                        else assertTrue { subject.contains(it) }
                    }
                }
            }

            on("retaining by predicate") {
                val predicate: (String) -> Boolean = { it.equals(strings[0]) || it.equals(strings[1]) }
                subject.retainIf(predicate)

                it("should pass only elements that satisfy the predicate") {
                    strings.forEach {
                        if (predicate(it)) assertTrue { subject.contains(it) }
                        else assertFalse { subject.contains(it) }
                    }
                }
            }

            on("adding collection of ${Arrays.toString(stringsForOtherCollection)}") {
                subject.addAll(otherCollection)

                it("should contains elements from the second collection") {
                    stringsForOtherCollection.forEach { assertTrue { subject.contains(it) } }
                }
            }

            on("adding empty collection") {
                subject.addAll(getCollectionMock(emptyArray()))

                it("should do nothing to collection") {
                    assertEquals(strings.size, subject.size())
                }
            }

            on("containing collection", *dataForContainingAll)
            { array, expected ->
                it("should return $expected on ${Arrays.toString(array)}") {
                    assertEquals(expected, subject.containsAll(getCollectionMock(array)))
                }
            }

            on("containing empty collection") {
                it("should return true") {
                    assertTrue(subject.containsAll(getCollectionMock(emptyArray())))
                }
            }

            on("removing collection", *dataForRemovingAll)
            { array, expected ->
                subject.removeAll(getCollectionMock(array))

                it("should retain only ${Arrays.toString(expected)} " +
                        "on removing all elements from ${Arrays.toString(array)}") {
                    strings.forEach { element ->
                        if (expected.contains(element)) assertTrue { subject.contains(element) }
                        else assertFalse { subject.contains(element) }
                    }
                }
            }

            on("removing empty collection") {
                subject.removeAll(getCollectionMock(emptyArray()))

                it("should do nothing to collection") {
                    assertEquals(strings.size, subject.size())
                }
            }
        }

        on("addition null value") {
            it("should throw NullPointerException") {
                assertFailsWith<NullPointerException> { subject.add(null) }
            }
        }

        on("removing null value") {
            it("should throw NullPointerException") {
                assertFailsWith<NullPointerException> { subject.remove(null) }
            }
        }

        on("contains null value") {
            it("should throw NullPointerException") {
                assertFailsWith<NullPointerException> { subject.contains(null) }
            }
        }

        on("removing by predicate") {
            it("should throw NullPointerException") {
                assertFailsWith<NullPointerException> { subject.removeIf(null) }
            }
        }
    }
})