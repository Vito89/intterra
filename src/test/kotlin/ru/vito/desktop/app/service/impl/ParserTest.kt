package ru.vito.desktop.app.service.impl

import org.junit.jupiter.api.Test
import java.util.HashMap
import java.util.HashSet
import java.util.stream.Collectors

import org.junit.jupiter.api.Assertions.*

internal class ParserTest {

    private val parser = Parser()

    @Test
    fun givenNullRequest_Ok() {
        // ACT
        val response = parser.parse(null)

        // ASSERT
        assertNull(response)
    }

    @Test
    fun givenEmptyRequest_Ok() {
        // ACT
        val response = parser.parse(HashMap())

        // ASSERT
        assertNotNull(response)
        assertEquals(0, response!!.size)
    }

    @Test
    fun givenSingleElementRequest_Ok() {
        // ARRANGE
        val request = HashMap<String, String>()
        request["user1 "] = " xxx@ya.ru, foo@gmail.com, lol@mail.ru"

        // ACT
        val response = parser.parse(request)

        // ASSERT
        assertNotNull(response)
        assertEquals(1, response!!.size)
        assertEquals(1, response.keys.size)
        assertEquals(1, response.values.size)
        assertEquals(3, response.values.iterator().next().size)
    }

    @Test
    fun givenFewElementsList_easyKit_Ok() {
        // ARRANGE
        val request = HashMap<String, String>()
        request["user1 "] = " xxx@ya.ru, foo@gmail.com, lol@mail.ru "
        request["user2 "] = " foo@gmail.com, ups@pisem.net "
        request["user3 "] = " xyz@pisem.net, vasya@pupkin.com "
        request["user4 "] = " ups@pisem.net, aaa@bbb.ru "
        request["user5 "] = " xyz@pisem.net"

        // ACT
        val response = parser.parse(request)

        // ASSERT
        assertNotNull(response)
        assertEquals(5, response!!.size)
        assertEquals(5, response.keys.size)
        assertEquals(5, response.values.size)
        assertEquals(7, response.values.stream()
                .flatMap<String>(Function<HashSet<String>, Stream<out String>> { it.stream() })
                .collect<Set<String>, Any>(Collectors.toSet())
                .size)
    }

    @Test
    fun givenFewElementsList_hardKit_Ok() {
        // ARRANGE
        val request = HashMap<String, String>()
        request["user1 "] = " e1@ya.ru, e2@ya.ru "
        request["user2 "] = " e3@ya.ru, e4@ya.ru "
        request["user3 "] = " e1@ya.ru, e3@ya.ru "

        // ACT
        val response = parser.parse(request)

        // ASSERT
        assertNotNull(response)
        assertEquals(3, response!!.size)
        assertEquals(3, response.keys.size)
        assertEquals(3, response.values.size)
        assertEquals(4, response.values.stream()
                .flatMap<String>(Function<HashSet<String>, Stream<out String>> { it.stream() })
                .collect<Set<String>, Any>(Collectors.toSet())
                .size)
    }
}