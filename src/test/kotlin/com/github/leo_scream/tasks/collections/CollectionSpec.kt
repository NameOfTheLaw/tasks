package com.github.leo_scream.tasks.collections

import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek
import java.lang.NullPointerException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

object CollectionSpec : SubjectSpek<Collection<String>>({
    describe("collection") {

        context("empty collection") {
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
        }

        on("contains with the value that wasn't been added before") {
            it("should return false") {
                assertFalse(subject.contains("value"))
            }
        }

        on("addition 1 element") {
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

        on("addition null value") {
            it("should throw NullPointerException") {
                assertFailsWith<NullPointerException> { subject.add(null) }
            }
        }

        on("removing element which is not presented in the collection") {
            subject.remove("nonExistingValue")

            it("should not change the collection size") {
                assertEquals(1, subject.size())
            }

        }

        on("removing element which is presented in the collection") {
            subject.remove("value")

            it("should reduce the collection size by 1") {
                assertEquals(0, subject.size())
            }

        }
    }
})