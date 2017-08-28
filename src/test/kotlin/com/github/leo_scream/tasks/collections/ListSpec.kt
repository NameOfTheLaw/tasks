package com.github.leo_scream.tasks.collections

import java.lang.IndexOutOfBoundsException
import java.util.*
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.data_driven.data
import org.jetbrains.spek.data_driven.on
import org.jetbrains.spek.subject.SubjectSpek
import org.jetbrains.spek.subject.itBehavesLike
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

object ListSpec : SubjectSpek<List<String>>({

    itBehavesLike(CollectionSpec)

    describe("list") {
        val strings = arrayOf("a", "b", "c")

        beforeEachTest { strings.forEach { subject.add(it) } }
        afterEachTest { subject.clear() }

        given("list of several elements ${Arrays.toString(strings)}") {

            on("getting a sub list from %d to %d",
                    data(0, 3, expected = strings.copyOfRange(0, 3)),
                    data(1, 2, expected = strings.copyOfRange(1, 2)),
                    data(0, 0, expected = strings.copyOfRange(0, 0)),
                    data(0, 1, expected = strings.copyOfRange(0, 1))) { from, to, expected ->
                val subList = subject.subList(from, to)

                it("should return ${Arrays.toString(expected)}") {
                    expected.forEachIndexed { i, s -> assertEquals(s, subList.get(i)) }
                }
            }

            on("getting a sub list where upper bound is out of index") {
                it("should throw IndexOutOfBoundsException") {
                    assertFailsWith<IndexOutOfBoundsException> { subject.subList(0, strings.size + 1) }
                }
            }

            on("getting a sub list where lower bound is out of index") {
                it("should throw IndexOutOfBoundsException") {
                    assertFailsWith<IndexOutOfBoundsException> { subject.subList(-1, 0) }
                }
            }

            on("getting an element by index %d",
                    data(0, expected = "a"),
                    data(1, expected = "b"),
                    data(2, expected = "c")) { index, expected ->
                it("should return $expected") {
                    assertEquals(expected, subject.get(index))
                }
            }

            on("getting an element by index that is out of bounds") {
                it("should throw IndexOutOfBoundsException") {
                    assertFailsWith<IndexOutOfBoundsException> { subject.get(-1) }
                    assertFailsWith<IndexOutOfBoundsException> { subject.get(subject.size() + 1) }
                }
            }

            on("removing an element by index %d",
                    data(0, expected = arrayOf("b", "c")),
                    data(1, expected = arrayOf("a", "c")),
                    data(2, expected = arrayOf("a", "b"))) { index, expected ->
                subject.remove(index)

                it("should reduce collection size to ${expected.size}") {
                    assertEquals(expected.size, subject.size())
                }

                it("should become ${Arrays.toString(expected)}") {
                    expected.forEachIndexed { i, s -> assertEquals(s, subject.get(i)) }
                }
            }

            on("removing an element by index that is out of bounds") {
                it("should throw IndexOutOfBoundsException") {
                    assertFailsWith<IndexOutOfBoundsException> { subject.remove(-1) }
                    assertFailsWith<IndexOutOfBoundsException> { subject.remove(subject.size() + 1) }
                }
            }

            on("adding an element %s by index %d",
                    data("z", 0, expected = arrayOf("z", "a", "b", "c")),
                    data("y", 1, expected = arrayOf("a", "y", "b", "c")),
                    data("x", 2, expected = arrayOf("a", "b", "x", "c"))) { value, index, expected ->
                subject.add(index, value)

                it("should expand size") {
                    assertEquals(expected.size, subject.size())
                }

                it("should become ${Arrays.toString(expected)}") {
                    expected.forEachIndexed { i, s -> assertEquals(s, subject.get(i)) }
                }
            }

            on("adding an element by index that is out of bounds") {
                it("should throw IndexOutOfBoundsException") {
                    assertFailsWith<IndexOutOfBoundsException> { subject.add(-1, "x") }
                    assertFailsWith<IndexOutOfBoundsException> { subject.add(subject.size() + 1, "x") }
                }
            }

            on("setting an element %s by index %d",
                    data("z", 0, expected = arrayOf("z", "b", "c")),
                    data("y", 1, expected = arrayOf("a", "y", "c")),
                    data("x", 2, expected = arrayOf("a", "b", "x"))) { value, index, expected ->
                subject.set(index, value)

                it("should not expand size") {
                    assertEquals(expected.size, subject.size())
                }

                it("should become ${Arrays.toString(expected)}") {
                    expected.forEachIndexed { i, s -> assertEquals(s, subject.get(i)) }
                }
            }

            on("setting an element by index that is out of bounds") {
                it("should throw IndexOutOfBoundsException") {
                    assertFailsWith<IndexOutOfBoundsException> { subject.set(-1, "x") }
                    assertFailsWith<IndexOutOfBoundsException> { subject.set(subject.size() + 1, "x") }
                }
            }
        }
    }
})