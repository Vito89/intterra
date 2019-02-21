package ru.vito.desktop.app.service.impl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

internal class ReaderTest {

    private val reader = Reader()

    @Test
    fun givenEmptyArrayRequest_Ok() {
        // ACT
        val response = reader.read(emptyArray())

        // ASSERT
        assertNotNull(response) // TODO to valid test
        assertEquals(0, response.size)
    }

    @Test
    fun givenEmptyRequest_Ok() {
        // ACT
        val response = reader.read(arrayOf(""))

        // ASSERT
        assertNotNull(response)
        assertEquals(0, response.size)
    }

    @Test
    fun givenSingleElementRequest_Ok() {
        // ARRANGE
        val request = arrayOf("user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru\n")

        // ACT
        val response = reader.read(request)

        // ASSERT
        assertNotNull(response)
        assertEquals(1, response.size)
        assertEquals(response.keys.iterator().next(), request[0]
                .split("->".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0])
        assertEquals(response.values.iterator().next(), request[0]
                .split("->".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1])
    }

    @Test
    fun givenFewElementsList_easyKit_Ok() {
        // ARRANGE
        val request = ("user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru\n" +
                "user2 -> foo@gmail.com, ups@pisem.net\n" +
                "user3 -> xyz@pisem.net, vasya@pupkin.com\n" +
                "user4 -> ups@pisem.net, aaa@bbb.ru\n" +
                "user5 -> xyz@pisem.net\n").split("\\n"
                .toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        // ACT
        val response = reader.read(request)

        // ASSERT
        assertNotNull(response)
        assertEquals(5, response.size)
    }

    @Test
    fun givenFewElementsList_hardKit_Ok() {
        // ARRANGE
        val request = ("user1 -> e1@ya.ru, e2@ya.ru\n" +
                "user2 -> e3@ya.ru, e4@ya.ru\n" +
                "user3 -> e1@ya.ru, e3@ya.ru\n").split("\\n"
                .toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        // ACT
        val response = reader.read(request)

        // ASSERT
        assertNotNull(response)
        assertEquals(3, response.size)
    }

    @Test
    fun givenNotEmptyList_BadRequest_Ok() {
        // ARRANGE
        val request = ("xxx@ya.ru, foo@gmail.com, lol@mail.ru\n" +
                "user2 - foo@gmail.com, ups@pisem.net\n" +
                "user3 -> xyz@pisem.net, ,  gmail.com, vasya@pupkin.com\n" +
                "ups@pisem.net, aaa@bbb.ru\n" +
                "user5 -> \n").split("\\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        // ACT
        val response = reader.read(request)

        // ASSERT
        assertNotNull(response)
        assertEquals(2, response.size)
    }
}