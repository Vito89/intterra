package ru.vito.desktop.app.service.impl

import org.junit.jupiter.api.Test
import ru.vito.desktop.app.models.DefinedUsers

import java.util.*

import org.junit.jupiter.api.Assertions.*

internal class DefinerTest {

    private val definer = Definer()

    @Test
    fun givenNullRequest_Ok() {
        // ACT
        val response = definer.define(null)

        // ASSERT
        assertNull(response)
    }

    @Test
    fun givenEmptyRequest_Ok() {
        // ACT
        val response = definer.define(HashMap())

        // ASSERT
        assertNotNull(response)
        assertNotNull(response.getEmailToLoginMap())
        assertEquals(0, response.getEmailToLoginMap().size())
    }

    @Test
    fun givenSingleElementRequest_Ok() {
        // ARRANGE
        val request = HashMap<String, HashSet<String>>()
        val requestEmailsList = Arrays.asList("xxx@ya.ru", "foo@gmail.com", "lol@mail.ru")
        val requestEmailsSet = HashSet(requestEmailsList)
        request["user1 "] = requestEmailsSet

        // ACT
        val response = definer.define(request)

        // ASSERT
        assertNotNull(response)
        assertEquals(3, response.getEmailToLoginMap().size())
        assertEquals(1, HashSet(response.getEmailToLoginMap().values()).size)
        val emailsSet = response.getEmailToLoginMap().keySet()
        assertEquals(3, emailsSet.size)
        assertTrue(emailsSet.containsAll(requestEmailsList))
    }

    @Test
    fun givenSingleMergedElementRequest_Ok() {
        // ARRANGE
        val request = HashMap<String, HashSet<String>>()
        val requestEmailsList = Arrays.asList("xxx@ya.ru", "xxx@ya.ru", "lol@mail.ru")
        val requestEmailsSet = HashSet(requestEmailsList)
        request["user1 "] = requestEmailsSet

        // ACT
        val response = definer.define(request)

        // ASSERT
        assertNotNull(response)
        assertEquals(2, response.getEmailToLoginMap().size())
        assertEquals(1, HashSet(response.getEmailToLoginMap().values()).size)
        val emailsSet = response.getEmailToLoginMap().keySet()
        assertEquals(2, emailsSet.size)
        assertTrue(emailsSet.containsAll(requestEmailsList))
    }

    @Test
    fun givenFewElementsList_Ok() {
        // ARRANGE
        val request = HashMap<String, HashSet<String>>()
        request["user1 "] = HashSet(Arrays.asList("xxx@ya.ru", "foo@gmail.com", "lol@mail.ru"))
        request["user2 "] = HashSet<String>(listOf<String>("ups@pisem.net"))

        // ACT
        val response = definer.define(request)

        // ASSERT
        assertNotNull(response)
        assertEquals(4, response.getEmailToLoginMap().size())
        assertEquals(2, HashSet(response.getEmailToLoginMap().values()).size)
        val emailsSet = response.getEmailToLoginMap().keySet()
        assertEquals(4, emailsSet.size)
    }

    @Test
    fun givenFewElementsList_mergeKit_Ok() {
        // ARRANGE
        val request = HashMap<String, HashSet<String>>()
        request["user1 "] = HashSet(Arrays.asList("xxx@ya.ru", "foo@gmail.com", "lol@mail.ru"))
        request["user2 "] = HashSet(Arrays.asList("foo@gmail.com", "ups@pisem.net"))
        request["user3 "] = HashSet(Arrays.asList("xyz@pisem.net", "vasya@pupkin.com"))
        request["user4 "] = HashSet(Arrays.asList("ups@pisem.net", "aaa@bbb.ru"))
        request["user5 "] = HashSet<String>(listOf<String>("xyz@pisem.net"))

        // ACT
        val response = definer.define(request)

        // ASSERT
        assertNotNull(response)
        assertEquals(7, response.getEmailToLoginMap().size())
        assertEquals(2, HashSet(response.getEmailToLoginMap().values()).size)
        val emailsSet = response.getEmailToLoginMap().keySet()
        assertEquals(7, emailsSet.size)
    }

    @Test
    fun givenFewElementsList_hardKit_Ok() {
        // ARRANGE
        val request = HashMap<String, HashSet<String>>()
        request["user1 "] = HashSet(Arrays.asList("e1@ya.ru", "e2@ya.ru"))
        request["user2 "] = HashSet(Arrays.asList("e3@ya.ru", "e4@ya.ru"))
        request["user3 "] = HashSet(Arrays.asList("e1@ya.ru", "e3@ya.ru"))

        // ACT
        val response = definer.define(request)

        // ASSERT
        assertNotNull(response)
        assertEquals(4, response.getEmailToLoginMap().size())
        assertEquals(1, HashSet(response.getEmailToLoginMap().values()).size)
        val emailsSet = response.getEmailToLoginMap().keySet()
        assertEquals(4, emailsSet.size)
    }
}