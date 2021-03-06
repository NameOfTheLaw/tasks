package com.github.leo_scream.tasks.collections

import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek
import org.jetbrains.spek.subject.itBehavesLike
import java.util.*
import kotlin.test.assertTrue

object HashSetSpec : SubjectSpek<HashSet<String>>({
    subject { HashSet.of() }

    itBehavesLike(SetSpec)

    describe("hash set") {
        val strings = arrayOf("a", "b", "c")
        val defaultLoadFactor = 0.75

        beforeEachTest { strings.forEach(subject::add) }

        on("getting the set ${Arrays.toString(strings)} load factor") {
            it("it should be between 0 and $defaultLoadFactor") {
                assertTrue(subject.loadFactor > 0)
                assertTrue(subject.loadFactor < defaultLoadFactor)
            }
        }

    }
})