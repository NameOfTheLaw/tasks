package com.github.leo_scream.tasks.collections

import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.subject.SubjectSpek
import org.jetbrains.spek.subject.itBehavesLike
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

object LinkedListSpec : SubjectSpek<LinkedList<String>>({
    subject { LinkedList.of() }

    itBehavesLike(ListSpec)

    describe("linked list") {
        val strings = arrayOf("a", "b", "c")

        given("linked list of ${Arrays.toString(strings)}") {

            beforeEachTest { strings.forEach(subject::add) }
            afterEachTest(subject::clear)

            on("getting first") {
                it("should return ${strings[0]}") {
                    assertEquals(strings[0], subject.first())
                }
            }

            on("getting last") {
                it("should return ${strings[strings.size - 1]}") {
                    assertEquals(strings[strings.size - 1], subject.last())
                }
            }

            on("removing first") {
                subject.removeFirst()

                it("should reduce list size by 1") {
                    assertEquals(strings.size - 1, subject.size())
                }

                it("should change first element to ${strings[1]}") {
                    assertEquals(strings[1], subject.first())
                }
            }

            on("removing last") {
                subject.removeLast()

                it("should reduce list size by 1") {
                    assertEquals(strings.size - 1, subject.size())
                }

                it("should change last element to ${strings[strings.size - 2]}") {
                    assertEquals(strings[strings.size - 2], subject.last())
                }
            }

            val pushingValue = "9"

            on("pushing '$pushingValue'") {
                subject.push(pushingValue)

                it("should extend list size by 1") {
                    assertEquals(strings.size + 1, subject.size())
                }

                it("should add $pushingValue to the end of the list") {
                    assertEquals(pushingValue, subject.last())
                }
            }

            on("poping") {
                val popedValue = subject.pop()

                it("should reduce list size by 1") {
                    assertEquals(strings.size - 1, subject.size())
                }

                it("should return last element: ${strings[strings.size - 1]}") {
                    assertEquals(strings[strings.size - 1], popedValue)
                }
            }

            on("reverse") {
                val reversedLinkedList = subject.reverse()

                it("should return new linked list") {
                    assertNotEquals(subject, reversedLinkedList)
                }

                it("should not change the first list") {
                    strings.forEachIndexed { i, element ->
                        assertEquals(element, subject.get(i))
                    }
                }

                it("should return reversed linked list") {
                    strings.forEachIndexed { i, element ->
                        assertEquals(element, reversedLinkedList.get(reversedLinkedList.size() - 1 - i))
                    }
                }
            }
        }

        given("empty linked list") {
            on("getting first") {
                it("should fail with IndexOutOfBoundException") {
                    assertFailsWith<IndexOutOfBoundsException> { subject.first() }
                }
            }

            on("getting last") {
                it("should fail with IndexOutOfBoundException") {
                    assertFailsWith<IndexOutOfBoundsException> { subject.last() }
                }
            }

            on("removing first") {
                it("should fail with IndexOutOfBoundException") {
                    assertFailsWith<IndexOutOfBoundsException> { subject.removeFirst() }
                }
            }

            on("removing last") {
                it("should fail with IndexOutOfBoundException") {
                    assertFailsWith<IndexOutOfBoundsException> { subject.removeLast() }
                }
            }

            on("popping") {
                it("should fail with IndexOutOfBoundException") {
                    assertFailsWith<IndexOutOfBoundsException> { subject.pop() }
                }
            }

            on("reverse") {
                val reversedLinkedList = subject.reverse()

                it("should return new linked list") {
                    assertNotEquals(subject, reversedLinkedList)
                }

                it("should return empty linked list") {
                    assertTrue(reversedLinkedList.isEmpty)
                }
            }
        }
    }
})