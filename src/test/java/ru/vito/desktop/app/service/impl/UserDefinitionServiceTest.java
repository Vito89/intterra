package ru.vito.desktop.app.service.impl;

import jersey.repackaged.com.google.common.collect.Sets;
import org.junit.Test;
import ru.vito.desktop.app.models.EmailToLoginMap;
import ru.vito.desktop.app.service.UserDefinitionService;

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
        assertEquals(new EmailToLoginMap(), response);
    }

    @Test
    public void givenNotEmptyList_Ok() {
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
        assertNotNull(response.getEmailToLoginMap());
        assertEquals(2, Sets.newCopyOnWriteArraySet(response.getEmailToLoginMap().values()).size());
    }

    @Test
    public void givenNotEmptyList_BadRequest_Ok() {
        // ARRANGE
        final String[] request = ("xxx@ya.ru, foo@gmail.com, lol@mail.ru\n" +
                "user2 - foo@gmail.com, ups@pisem.net\n" +
                "user3 -> xyz@pisem.net, vasya@pupkin.com\n" +
                "ups@pisem.net, aaa@bbb.ru\n" +
                "user5 -> \n").split("\\n");

        // ACT
        final EmailToLoginMap response = userDefinitionService.define(request);

        // ASSERT
        assertNotNull(response);
        assertNotNull(response.getEmailToLoginMap());
        assertEquals(2, response.getEmailToLoginMap().size());
    }
}