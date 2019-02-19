package ru.vito.desktop.app.service.impl;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    private Parser parser = new Parser();

    @Test
    void givenNullRequest_Ok() {
        // ACT
        final Map<String, HashSet<String>> response = parser.parse(null);

        // ASSERT
        assertNull(response);
    }

    @Test
    void givenEmptyRequest_Ok() {
        // ACT
        final Map<String, HashSet<String>> response = parser.parse(new HashMap<>());

        // ASSERT
        assertNotNull(response);
        assertEquals(0, response.size());
    }

    @Test
    void givenSingleElementRequest_Ok() {
        // ARRANGE
        final Map<String, String> request = new HashMap<>();
        request.put("user1 ", " xxx@ya.ru, foo@gmail.com, lol@mail.ru");

        // ACT
        final Map<String, HashSet<String>> response = parser.parse(request);

        // ASSERT
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(1, response.keySet().size());
        assertEquals(1, response.values().size());
        assertEquals(3, response.values().iterator().next().size());
    }

    @Test
    void givenFewElementsList_easyKit_Ok() {
        // ARRANGE
        final Map<String, String> request = new HashMap<>();
        request.put("user1 ", " xxx@ya.ru, foo@gmail.com, lol@mail.ru ");
        request.put("user2 ", " foo@gmail.com, ups@pisem.net ");
        request.put("user3 ", " xyz@pisem.net, vasya@pupkin.com ");
        request.put("user4 ", " ups@pisem.net, aaa@bbb.ru ");
        request.put("user5 ", " xyz@pisem.net");

        // ACT
        final Map<String, HashSet<String>> response = parser.parse(request);

        // ASSERT
        assertNotNull(response);
        assertEquals(5, response.size());
        assertEquals(5, response.keySet().size());
        assertEquals(5, response.values().size());
        assertEquals(7, response.values().stream()
            .flatMap(Collection::stream)
            .collect(Collectors.toSet())
            .size());
    }

    @Test
    void givenFewElementsList_hardKit_Ok() {
        // ARRANGE
        final Map<String, String> request = new HashMap<>();
        request.put("user1 ", " e1@ya.ru, e2@ya.ru ");
        request.put("user2 ", " e3@ya.ru, e4@ya.ru ");
        request.put("user3 ", " e1@ya.ru, e3@ya.ru ");

        // ACT
        final Map<String, HashSet<String>> response = parser.parse(request);

        // ASSERT
        assertNotNull(response);
        assertEquals(3, response.size());
        assertEquals(3, response.keySet().size());
        assertEquals(3, response.values().size());
        assertEquals(4, response.values().stream()
            .flatMap(Collection::stream)
            .collect(Collectors.toSet())
            .size());
    }
}