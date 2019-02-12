package ru.vito.desktop.app.service.impl;

import jersey.repackaged.com.google.common.collect.Sets;
import org.junit.Test;
import ru.vito.desktop.app.models.EmailToLoginMap;
import ru.vito.desktop.app.service.UserDefinitionService;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserDefinitionServiceTest {

    private UserDefinitionService userDefinitionService = new UserDefinitionServiceImpl();

    @Test
    public void givenNull_Ok() {
        // ACT
        final EmailToLoginMap response = userDefinitionService.define(null);

        // ASSERT
        assertNotNull(response);
        assertEquals(new EmailToLoginMap(), response);
    }

    @Test
    public void givenEmptyArray_Ok() {
        // ACT
        final EmailToLoginMap response = userDefinitionService.define(new String[0]);

        // ASSERT
        assertNotNull(response);

        final EmailToLoginMap expectedEmailToLoginMap = new EmailToLoginMap();
        assertEquals(expectedEmailToLoginMap, response);
        assertEquals(expectedEmailToLoginMap.getEmailToLoginMap().size(), response.getEmailToLoginMap().size());
    }

    @Test
    public void givenSingleElementList_Ok() {
        // ARRANGE
        final String[] request = ("user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru\n").split("\\n");

        // ACT
        final EmailToLoginMap response = userDefinitionService.define(request);

        // ASSERT
        assertNotNull(response);

        final Map<String, String> emailToLoginSetMap = response.getEmailToLoginMap();
        assertNotNull(emailToLoginSetMap);
        assertEquals(1, Sets.newCopyOnWriteArraySet(emailToLoginSetMap.values()).size());
        assertEquals(3, emailToLoginSetMap.keySet().size());
    }

    @Test
    public void givenFewElementsList_easyKit_Ok() {
        // ARRANGE
        final String[] request = ("user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru\n" +
                "user2 -> foo@gmail.com, ups@pisem.net\n" +
                "user3 -> xyz@pisem.net, vasya@pupkin.com\n" +
                "user4 -> ups@pisem.net, aaa@bbb.ru\n" +
                "user5 -> xyz@pisem.net\n").split("\\n");

        // ACT
        final EmailToLoginMap response = userDefinitionService.define(request);

        // ASSERT
        assertNotNull(response);

        final Map<String, String> emailToLoginSetMap = response.getEmailToLoginMap();
        assertNotNull(emailToLoginSetMap);
        assertEquals(2, Sets.newCopyOnWriteArraySet(emailToLoginSetMap.values()).size());
        assertEquals(7, emailToLoginSetMap.keySet().size());
    }

    @Test
    public void givenFewElementsList_hardKit_Ok() {
        // ARRANGE
        final String[] request = ("user1 -> e1@ya.ru, e2@ya.ru\n" +
                "user2 -> e3@ya.ru, e4@ya.ru\n" +
                "user3 -> e1@ya.ru, e3@ya.ru\n").split("\\n");

        // ACT
        final EmailToLoginMap response = userDefinitionService.define(request);

        // ASSERT
        assertNotNull(response);

        final Map<String, String> emailToLoginSetMap = response.getEmailToLoginMap();
        assertNotNull(emailToLoginSetMap);
        assertEquals(1, Sets.newCopyOnWriteArraySet(emailToLoginSetMap.values()).size());
        assertEquals(4, emailToLoginSetMap.keySet().size());
    }

    @Test
    public void givenNotEmptyList_BadRequest_Ok() {
        // ARRANGE
        final String[] request = ("xxx@ya.ru, foo@gmail.com, lol@mail.ru\n" +
                "user2 - foo@gmail.com, ups@pisem.net\n" +
                "user3 -> xyz@pisem.net, ,  gmail.com, vasya@pupkin.com\n" +
                "ups@pisem.net, aaa@bbb.ru\n" +
                "user5 -> \n").split("\\n");

        // ACT
        final EmailToLoginMap response = userDefinitionService.define(request);

        // ASSERT
        assertNotNull(response);
        final Map<String, String> emailToLoginSetMap = response.getEmailToLoginMap();
        assertNotNull(emailToLoginSetMap);
        assertEquals(1, Sets.newCopyOnWriteArraySet(emailToLoginSetMap.values()).size());
        assertEquals(2, emailToLoginSetMap.keySet().size());
    }
}