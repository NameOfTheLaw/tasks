package com.github.leo_scream.tasks.collections

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.mock
import org.jetbrains.spek.api.dsl.*
import org.jetbrains.spek.subject.SubjectSpek
import org.jetbrains.spek.subject.itBehavesLike
import java.util.*
import java.util.function.Consumer
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

object SetSpec : SubjectSpek<Set<String>>({

    itBehavesLike(CollectionSpec)

    describe("set") {

        fun getSetMock(array: Array<String>): Set<String> =
                mock { set ->
                    onGeneric { set.forEach(any()) } doAnswer { invocationOnMock ->
                        val consumer = invocationOnMock.arguments.get(0) as Consumer<String>
                        array.forEach { consumer.accept(it) }
                    }

                    onGeneric { set.contains(any()) } doAnswer { invocationOnMock ->
                        array.contains(invocationOnMock.arguments.get(0))
                    }
                }
        val strings = arrayOf("a", "b", "c")
        val stringsForOtherSet = arrayOf("b", "c", "d")
        val otherSet = getSetMock(stringsForOtherSet)

        given("set of ${Arrays.toString(strings)}") {

            beforeEachTest { strings.forEach(subject::add) }
            afterEachTest { subject.clear() }

            on("union with set of ${Arrays.toString(stringsForOtherSet)}") {
                val unionSet = subject.union(otherSet)
                it("should return set with size of 4") {
                    assertEquals(4, unionSet.size())
                }

                it("should contains a, b, c, d") {
                    assertTrue(unionSet.contains("a"))
                    assertTrue(unionSet.contains("b"))
                    assertTrue(unionSet.contains("c"))
                    assertTrue(unionSet.contains("d"))
                }
            }

            on("intersection with set of ${Arrays.toString(stringsForOtherSet)}") {
                val unionSet = subject.intersection(otherSet)
                it("should return set with size of 2") {
                    assertEquals(2, unionSet.size())
                }

                it("should contains b and c") {
                    assertTrue(unionSet.contains("b"))
                    assertTrue(unionSet.contains("c"))
                }

                it("should not contains a and d") {
                    assertFalse(unionSet.contains("a"))
                    assertFalse(unionSet.contains("d"))
                }
            }

            on("difference with set of ${Arrays.toString(stringsForOtherSet)}") {
                val unionSet = subject.difference(otherSet)
                it("should return set with size of 2") {
                    assertEquals(2, unionSet.size())
                }

                it("should contains a and d") {
                    assertTrue(unionSet.contains("a"))
                    assertTrue(unionSet.contains("d"))
                }

                it("should not contains b and c") {
                    assertFalse(unionSet.contains("b"))
                    assertFalse(unionSet.contains("c"))
                }
            }
        }

    }
})