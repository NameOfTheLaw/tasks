package com.github.leo_scream.tasks.collections

import org.jetbrains.spek.api.dsl.*
import org.jetbrains.spek.subject.SubjectSpek
import java.lang.NullPointerException
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

object CollectionSpec : SubjectSpek<Collection<String>>({
    describe("collection") {
        val strings = arrayOf("a", "b", "c")

        given("empty collection") {
            on("checking its size") {
                it("should return 0") {
                    assertEquals(0, subject.size())
                }
            }

            on("checking its emptiness") {
                it("should be empty") {
                    assertTrue(subject.isEmpty())
                }
            }

            on("contains") {
                it("should return false") {
                    assertFalse(subject.contains("whatever"))
                }
            }

            on("addition") {
                val value = "value"
                subject.add(value)

                it("should contains added value") {
                    assertTrue(subject.contains(value))
                }

                it("should have the size of 1") {
                    assertEquals(1, subject.size())
                }

                it("should not be empty") {
                    assertTrue(subject.isEmpty)
                }
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


            val removedElement = strings[0]
            on("removing element that is presented in the collection: '$removedElement'") {
                subject.remove(removedElement)

                it("should reduce collection size by 1") {
                    assertEquals(strings.size-1, subject.size())
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
        }

        on("addition null value") {
            it("should throw NullPointerException") {
                assertFailsWith<NullPointerException> { subject.add(null) }
            }
        }
    }
})