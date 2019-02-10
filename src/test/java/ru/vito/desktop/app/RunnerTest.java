package ru.vito.desktop.app;

import org.junit.Test;

public class RunnerTest {

    @Test
    public void givenEmptyList_Ok() {
        // ACT
        Runner.main(new String[0]);
    }

    @Test
    public void givenNotEmptyList_Ok() {
        // ARRANGE
        final String request =
                "user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru\n" +
                        "user2 -> foo@gmail.com, ups@pisem.net\n" +
                        "user3 -> xyz@pisem.net, vasya@pupkin.com\n" +
                        "user4 -> ups@pisem.net, aaa@bbb.ru\n" +
                        "user5 -> xyz@pisem.net\n";

        // ACT
        Runner.main(request.split("\n"));
    }

    @Test
    public void givenNotEmptyList_BadRequest_Fail() {
        // ARRANGE
        final String request =
                "user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru\n" +
                        "user2 -> @gmail.com, ups@pisem.net\n" +
                        "user1 -> xyz@pisem.net, vasya@pupkin\n" +
                        "user4 -> ups@pisem.net, aaa@bbb.ru\n" +
                        "user5 -> xyz@pisem.net\n";

        // ACT
        Runner.main(request.split("\n"));
    }
}
