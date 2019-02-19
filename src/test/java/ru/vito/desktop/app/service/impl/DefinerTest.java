package ru.vito.desktop.app.service.impl;

import org.junit.jupiter.api.Test;
import ru.vito.desktop.app.models.DefinedUsers;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DefinerTest {

    private Definer definer = new Definer();

    @Test
    void givenNullRequest_Ok() {
        // ACT
        final DefinedUsers response = definer.define(null);

        // ASSERT
        assertNull(response);
    }

    @Test
    void givenEmptyRequest_Ok() {
        // ACT
        final DefinedUsers response = definer.define(new HashMap<>());

        // ASSERT
        assertNotNull(response);
        assertNotNull(response.getEmailToLoginMap());
        assertEquals(0, response.getEmailToLoginMap().size());
    }

    @Test
    void givenSingleElementRequest_Ok() {
        // ARRANGE
        final Map<String, HashSet<String>> request = new HashMap<>();
        final List<String> requestEmailsList = Arrays.asList("xxx@ya.ru", "foo@gmail.com", "lol@mail.ru");
        final HashSet<String> requestEmailsSet = new HashSet<>(requestEmailsList);
        request.put("user1 ", requestEmailsSet);

        // ACT
        final DefinedUsers response = definer.define(request);

        // ASSERT
        assertNotNull(response);
        assertEquals(3, response.getEmailToLoginMap().size());
        assertEquals(1, new HashSet<>(response.getEmailToLoginMap().values()).size());
        final Set<String> emailsSet = response.getEmailToLoginMap().keySet();
        assertEquals(3, emailsSet.size());
        assertTrue(emailsSet.containsAll(requestEmailsList));
    }

    @Test
    void givenSingleMergedElementRequest_Ok() {
        // ARRANGE
        final Map<String, HashSet<String>> request = new HashMap<>();
        final List<String> requestEmailsList = Arrays.asList("xxx@ya.ru", "xxx@ya.ru", "lol@mail.ru");
        final HashSet<String> requestEmailsSet = new HashSet<>(requestEmailsList);
        request.put("user1 ", requestEmailsSet);

        // ACT
        final DefinedUsers response = definer.define(request);

        // ASSERT
        assertNotNull(response);
        assertEquals(2, response.getEmailToLoginMap().size());
        assertEquals(1, new HashSet<>(response.getEmailToLoginMap().values()).size());
        final Set<String> emailsSet = response.getEmailToLoginMap().keySet();
        assertEquals(2, emailsSet.size());
        assertTrue(emailsSet.containsAll(requestEmailsList));
    }

    @Test
    void givenFewElementsList_Ok() {
        // ARRANGE
        final Map<String, HashSet<String>> request = new HashMap<>();
        request.put("user1 ", new HashSet<>(Arrays.asList("xxx@ya.ru", "foo@gmail.com", "lol@mail.ru")));
        request.put("user2 ", new HashSet<>(Collections.singletonList("ups@pisem.net")));

        // ACT
        final DefinedUsers response = definer.define(request);

        // ASSERT
        assertNotNull(response);
        assertEquals(4, response.getEmailToLoginMap().size());
        assertEquals(2, new HashSet<>(response.getEmailToLoginMap().values()).size());
        final Set<String> emailsSet = response.getEmailToLoginMap().keySet();
        assertEquals(4, emailsSet.size());
    }

    @Test
    void givenFewElementsList_mergeKit_Ok() {
        // ARRANGE
        final Map<String, HashSet<String>> request = new HashMap<>();
        request.put("user1 ", new HashSet<>(Arrays.asList("xxx@ya.ru", "foo@gmail.com", "lol@mail.ru")));
        request.put("user2 ", new HashSet<>(Arrays.asList("foo@gmail.com", "ups@pisem.net")));
        request.put("user3 ", new HashSet<>(Arrays.asList("xyz@pisem.net", "vasya@pupkin.com")));
        request.put("user4 ", new HashSet<>(Arrays.asList("ups@pisem.net", "aaa@bbb.ru")));
        request.put("user5 ", new HashSet<>(Collections.singletonList("xyz@pisem.net")));

        // ACT
        final DefinedUsers response = definer.define(request);

        // ASSERT
        assertNotNull(response);
        assertEquals(7, response.getEmailToLoginMap().size());
        assertEquals(2, new HashSet<>(response.getEmailToLoginMap().values()).size());
        final Set<String> emailsSet = response.getEmailToLoginMap().keySet();
        assertEquals(7, emailsSet.size());
    }

    @Test
    void givenFewElementsList_hardKit_Ok() {
        // ARRANGE
        final Map<String, HashSet<String>> request = new HashMap<>();
        request.put("user1 ", new HashSet<>(Arrays.asList("e1@ya.ru", "e2@ya.ru")));
        request.put("user2 ", new HashSet<>(Arrays.asList("e3@ya.ru", "e4@ya.ru")));
        request.put("user3 ", new HashSet<>(Arrays.asList("e1@ya.ru", "e3@ya.ru")));

        // ACT
        final DefinedUsers response = definer.define(request);

        // ASSERT
        assertNotNull(response);
        assertEquals(4, response.getEmailToLoginMap().size());
        assertEquals(1, new HashSet<>(response.getEmailToLoginMap().values()).size());
        final Set<String> emailsSet = response.getEmailToLoginMap().keySet();
        assertEquals(4, emailsSet.size());
    }
}