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
    }
})