package com.github.leo_scream.tasks

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

object RangeSpec : Spek({
    val smallestRangeLowerBound = 42
    val smallestRangeUpperBound = 47
    val greatestRangeLowerBound = 48
    val greatestRangeUpperBound = 53

    describe("a range") {
        on("initialization") {
            it("should throw IllegalArgumentException if lower bound greater than upper one") {
                assertFailsWith<IllegalArgumentException> { Range.within(smallestRangeUpperBound, smallestRangeLowerBound) }
            }

            it("should be able to instantiate range within 1 element if lower bound equals to upper one") {
                assertTrue { Range.within(smallestRangeLowerBound, smallestRangeLowerBound).contains(smallestRangeLowerBound) }
            }

            it("should be able to has Integer.MIN_VALUE as lower bound and Integer.MAX_VALUE as upper bound") {
                val range = Range.within(Integer.MIN_VALUE, Integer.MAX_VALUE)
                assertEquals(range.lowerBound(), Integer.MIN_VALUE)
                assertEquals(range.upperBound(), Integer.MAX_VALUE)
            }

        }

        given("initialized ranges") {
            val smallest = Range.within(smallestRangeLowerBound, smallestRangeUpperBound)
            val greatest = Range.within(greatestRangeLowerBound, greatestRangeUpperBound)
            val overlapping = Range.within(smallestRangeUpperBound, greatestRangeLowerBound)

            it("should contains lower and upper bounds as an element") {
                assertTrue { smallest.contains(smallestRangeLowerBound) }
                assertTrue { smallest.contains(smallestRangeUpperBound) }
            }

            it("should not contains element if it is out within bounds") {
                assertFalse { overlapping.contains(smallestRangeLowerBound) }
                assertFalse { overlapping.contains(greatestRangeUpperBound) }
            }

            on("sequencing") {
                it("should be before another if it's upper bound is less-than lower bound within another range") {
                    assertTrue { smallest.isBefore(greatest) }
                }

                it("should not be before another if it's upper bound is greater-than or equals to lower bound within another range") {
                    assertFalse { greatest.isBefore(smallest) }
                }

                it("should not be before another if ranges has overlapping elements") {
                    assertFalse { overlapping.isBefore(greatest) }
                }

                it("should be after another if it's lower bound is greater-than upper bound within another range") {
                    assertTrue { greatest.isAfter(smallest) }
                }

                it("should not be after another if it's lower bound is less-than or equals to upper bound within another range") {
                    assertFalse { smallest.isAfter(greatest) }
                }

                it("should not be after another if ranges has overlapping elements") {
                    assertFalse { greatest.isAfter(overlapping) }
                }

                it("should be concurrent if ranges has common elements") {
                    assertTrue { smallest.isConcurrent(overlapping) }
                    assertTrue { greatest.isConcurrent(overlapping) }
                }

                it("should not be concurrent if ranges has no common elements") {
                    assertFalse { smallest.isConcurrent(greatest) }
                    assertFalse { greatest.isConcurrent(smallest) }
                }

                it("should be concurrent to itself") {
                    assertTrue { smallest.isConcurrent(smallest) }
                    assertTrue { greatest.isConcurrent(greatest) }
                    assertTrue { overlapping.isConcurrent(overlapping) }
                }
            }

            on("viewing as a list") {
                it("should contains every single value") {
                    assertEquals(smallest.asList(), (smallestRangeLowerBound..smallestRangeUpperBound).toList())
                }
            }

            on("taking an iterator") {
                val iterator = smallest.asIterator()

                it("should has next") {
                    assertTrue { smallest.asIterator().hasNext() }
                }

                it("should throw NoSuchElementException if has not next value") {
                    val elements = (smallestRangeLowerBound..smallestRangeUpperBound).toList()
                    while (iterator.hasNext()) {
                        assertTrue { elements.contains(iterator.next()) }
                    }
                    assertFailsWith<NoSuchElementException> { iterator.next() }
                }
            }
        }
    }
})
