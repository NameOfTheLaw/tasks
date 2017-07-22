package com.github.leo_scream.tasks

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import java.util.*
import kotlin.test.*

/**
 * @author Denis Verkhoturov, mod.satyr@gmail.com
 */
object ReachIterableSpec : Spek({
    describe("a reach iterable") {
        on("initialization") {
            it("should throw NullPointerException if instantiated from null instead of list") {
                assertFailsWith<NullPointerException> { ReachIterable.from<Nothing>(null) }
            }

            it("should work fine with empty list") {
                ReachIterable.from(emptyList<String>())
            }
        }

        given("initialized reach iterable") {
            val elements = listOf("cat", "close", "camp", "airplane")

            on("consuming next element") {
                it("should return true if there is next element after processed") {
                    assertTrue { ReachIterable.from(elements).forNext {} }
                }

                it("should iterate over every single element before return false") {
                    var hasNext = true
                    val iterable = ReachIterable.from(elements)
                    while (hasNext) {
                        hasNext = iterable.forNext {
                            assertTrue { elements.contains(it) }
                        }
                    }
                }

                it("should return false if no element left") {
                    val iterable = ReachIterable.from(elements)
                    (0..elements.size - 1).forEach { iterable.forNext {} }
                    assertFalse { iterable.forNext {} }
                }

                it("should not call consumer if has not next element") {
                    val iterable = ReachIterable.from(elements)
                    (0..elements.size - 1).forEach { iterable.forNext {} }
                    iterable.forNext { fail() }
                }
            }

            on("filtering") {
                it("should fails with NullPointerException when predicate is null") {
                    assertFailsWith<NullPointerException> { ReachIterable.from(elements).filter(null) }
                }

                it("should return empty iterable if no elements satisfying predicate") {
                    assertFalse { ReachIterable.from(elements).filter { false }.forNext {} }
                }

                it("should pass only elements satisfying predicate") {
                    ReachIterable.from(elements).filter { word -> word.startsWith("a") }
                            .forNext { assertEquals("airplane", it) }
                }
            }

            on("mapping") {
                it("should fails with NullPointerException when mapper function is null") {
                    assertFailsWith<NullPointerException> { ReachIterable.from(elements).map<Nothing>(null) }
                }

                it("should map every single element") {
                    val expected = elements.map { it.length }
                    val mapped = ReachIterable.from(elements).map { it.length }
                    val actual = mutableListOf<Int>()
                    while (mapped.forNext { actual.add(it) });
                    assertEquals(expected, actual)
                }

                it("should works fine with function returning null as result") {
                    val expected = Collections.nCopies(elements.size, null)
                    val mapped = ReachIterable.from(elements).map { null }
                    val actual = mutableListOf<Nothing?>()
                    while (mapped.forNext { actual.add(it) });
                    assertEquals(expected, actual)
                }
            }

            on("flatten mapping") {
                it("should throw NullPointerException if mapper function is null") {
                    assertFailsWith<NullPointerException> {
                        ReachIterable.from(elements).flatMap<Nothing>(null)
                    }
                }

                it("should return reach iterable containing all elements mapped with function") {
                    val mapped = ReachIterable.from(elements)
                                              .flatMap({ word -> (0..word.length - 1).map { word[it] } })
                    val actual = mutableListOf<Char>()
                    while (mapped.forNext { actual.add(it) });
                    assertEquals(
                            listOf(
                                    'c', 'a', 't',
                                    'c', 'l', 'o', 's', 'e',
                                    'c', 'a', 'm', 'p',
                                    'a', 'i', 'r', 'p', 'l', 'a', 'n', 'e'
                            ),
                            actual
                    )
                }

                it("should works fine with mapper function returns less elements than was in origin") {
                    val mapped = ReachIterable.from(elements)
                                              .flatMap {
                                                  word -> (0..word.length - 1).filter { it > 5 }
                                                                              .map { word[it] }
                                              }
                    val actual = mutableListOf<Char>()
                    while (mapped.forNext { actual.add(it) });
                    assertEquals(listOf('n', 'e'), actual)
                }

                it("should process one element per ") {
                    val mapped = ReachIterable.from(elements)
                                              .flatMap {  word -> (0..word.length - 1).map { word[it] } }
                    val actual = mutableListOf<Char>()
                    var counter = 0
                    var hasNext = true
                    while (hasNext) {
                        counter += 1
                        hasNext = mapped.forNext { actual.add(it) }
                    }
                    assertEquals(
                            listOf(
                                    'c', 'a', 't',
                                    'c', 'l', 'o', 's', 'e',
                                    'c', 'a', 'm', 'p',
                                    'a', 'i', 'r', 'p', 'l', 'a', 'n', 'e'
                            ).size,
                            counter
                    )
                }
            }

            on("finding first matched") {
                it("should throw NullPointerException if predicate is null") {
                    assertFailsWith<NullPointerException> { ReachIterable.from(elements).firstMatch(null) }
                }

                it("should return empty optional if no element was matched") {
                    val needle = ReachIterable.from(elements).firstMatch { false }
                    assertFalse { needle.isPresent }
                }

                it("should return optional containing needle if element is present in origin list") {
                    val needle = ReachIterable.from(elements).firstMatch { it == "camp" }
                    assertEquals("camp", needle.get())
                }

                it("should be short-circuited") {
                    val iterable = ReachIterable.from(elements)
                    iterable.firstMatch { it == "close" }
                    assertTrue { iterable.forNext { assertEquals("camp", it) } }
                }
            }

            on("checking is any match") {
                it ("should throw NullPointerException if predicate is null") {
                    assertFailsWith<NullPointerException> { ReachIterable.from(elements).anyMatch(null) }
                }

                it ("should be true if given elements contains any element which satisfies predicate") {
                    val iterable = ReachIterable.from(elements)
                    assertTrue { iterable.anyMatch { it.startsWith("a") } }
                }

                it ("should be false if given elements contains no element which satisfies predicate") {
                    val iterable = ReachIterable.from(elements)
                    assertFalse { iterable.anyMatch { it.startsWith("x") } }
                }
            }

            on("checking is all match") {
                it ("should throw NullPointerException if predicate is null") {
                    assertFailsWith<NullPointerException> { ReachIterable.from(elements).allMatch(null) }
                }

                it ("should be true if all the elements satisfies predicate") {
                    val iterable = ReachIterable.from(elements)
                    assertTrue { iterable.allMatch { it.length < 42 } }
                }

                it ("should be false if any element do not satisfy predicate") {
                    val iterable = ReachIterable.from(elements)
                    assertFalse { iterable.allMatch { it.startsWith("x") } }
                }
            }

            on("checking is none match") {
                it ("should throw NullPointerException if predicate is null") {
                    assertFailsWith<NullPointerException> { ReachIterable.from(elements).noneMatch(null) }
                }

                it ("should be true if no one element satisfy predicate") {
                    val iterable = ReachIterable.from(elements)
                    assertTrue { iterable.noneMatch { it.startsWith("x") } }
                }

                it ("should be false if any element satisfy predicate") {
                    val iterable = ReachIterable.from(elements)
                    assertFalse { iterable.noneMatch { it.startsWith("a") } }
                }
            }
        }
    }
})
