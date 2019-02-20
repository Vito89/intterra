package ru.vito.desktop.app

import org.junit.jupiter.api.Test

internal class RunnerTest {

    @Test
    fun givenEmptyList_Ok() {
        // ACT
        Runner.run()
    }

    @Test
    fun givenNotEmptyList_easyKit_Ok() {
        // ARRANGE
        val request = "user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru\n" +
                "user2 -> foo@gmail.com, ups@pisem.net\n" +
                "user3 -> xyz@pisem.net, vasya@pupkin.com\n" +
                "user4 -> ups@pisem.net, aaa@bbb.ru\n" +
                "user5 -> xyz@pisem.net\n"

        // ACT
        Runner.run(request.split("\n".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray())
    }

    @Test
    fun givenNotEmptyList_hardKit_Ok() {
        // ARRANGE
        val request = "user1 -> e1@ya.ru, e2@ya.ru\n" +
                "user2 -> e3@ya.ru, e4@ya.ru\n" +
                "user3 -> e1@ya.ru, e3@ya.ru\n"

        // ACT
        Runner.run(request.split("\n".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray())
    }

    @Test
    fun givenNotEmptyList_BadRequest_Ok() {
        // ARRANGE
        val request = "user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru\n" +
                "user2 -> @gmail.com, ups@pisem.net\n" +
                "user1 -> xyz@pisem.net, vasya@pupkin\n" +
                "user4 -> ups@pisem.net, aaa@bbb.ru\n" +
                "user5 -> xyz@pisem.net\n"

        // ACT
        Runner.run(request.split("\n".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray())
    }
}
