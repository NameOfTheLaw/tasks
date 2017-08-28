package com.github.leo_scream.tasks.collections

import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek
import org.jetbrains.spek.subject.itBehavesLike
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

object HashSetSpec : SubjectSpek<HashSet<String>>({
    subject { HashSet.of() }

    itBehavesLike(SetSpec)

    describe("hash set") {
        val strings = arrayOf("a", "b", "c")

        beforeEachTest { strings.forEach(subject::add) }

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
    }
})