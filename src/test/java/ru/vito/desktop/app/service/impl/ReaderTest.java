package ru.vito.desktop.app.service.impl;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ReaderTest {

    private Reader reader = new Reader();

    @Test
    void givenNullRequest_Ok() {
        // ACT
        final Map<String, String> response = reader.read(null);

        // ASSERT
        assertNull(response);
    }

    @Test
    void givenEmptyRequest_Ok() {
        // ACT
        final Map<String, String> response = reader.read(new String[0]);

        // ASSERT
        assertNotNull(response);
        assertEquals(0, response.size());
    }

    @Test
    void givenSingleElementRequest_Ok() {
        // ARRANGE
        final String[] request = {"user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru\n"};

        // ACT
        final Map<String, String> response = reader.read(request);

        // ASSERT
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(response.keySet().iterator().next(), request[0].split("->")[0]);
        assertEquals(response.values().iterator().next(), request[0].split("->")[1]);
    }

    @Test
    void givenFewElementsList_easyKit_Ok() {
        // ARRANGE
        final String[] request = ("user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru\n" +
                "user2 -> foo@gmail.com, ups@pisem.net\n" +
                "user3 -> xyz@pisem.net, vasya@pupkin.com\n" +
                "user4 -> ups@pisem.net, aaa@bbb.ru\n" +
                "user5 -> xyz@pisem.net\n").split("\\n");

        // ACT
        final Map<String, String> response = reader.read(request);

        // ASSERT
        assertNotNull(response);
        assertEquals(5, response.size());
    }

    @Test
    void givenFewElementsList_hardKit_Ok() {
        // ARRANGE
        final String[] request = ("user1 -> e1@ya.ru, e2@ya.ru\n" +
                "user2 -> e3@ya.ru, e4@ya.ru\n" +
                "user3 -> e1@ya.ru, e3@ya.ru\n").split("\\n");

        // ACT
        final Map<String, String> response = reader.read(request);

        // ASSERT
        assertNotNull(response);
        assertEquals(3, response.size());
    }

    @Test
    void givenNotEmptyList_BadRequest_Ok() {
        // ARRANGE
        final String[] request = ("xxx@ya.ru, foo@gmail.com, lol@mail.ru\n" +
                "user2 - foo@gmail.com, ups@pisem.net\n" +
                "user3 -> xyz@pisem.net, ,  gmail.com, vasya@pupkin.com\n" +
                "ups@pisem.net, aaa@bbb.ru\n" +
                "user5 -> \n").split("\\n");

        // ACT
        final Map<String, String> response = reader.read(request);

        // ASSERT
        assertNotNull(response);
        assertEquals(2, response.size());
    }
}