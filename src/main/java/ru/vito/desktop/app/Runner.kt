package ru.vito.desktop.app

import ru.vito.desktop.app.models.DefinedUsers
import ru.vito.desktop.app.service.impl.Definer
import ru.vito.desktop.app.service.impl.Parser
import ru.vito.desktop.app.service.impl.Reader

import java.util.HashSet

object Runner {

    /**
     * @param strings receiving from input string array
     */
    fun main(strings: Array<String>) {

        print(run(strings))
    }

    private fun run(strings: Array<String>): String {

        val userToEmailsMap = Parser().parse(
                Reader().read(strings)
        )

        val definedUsers = Definer()
                .define(userToEmailsMap)

        return definedUsers!!.toString()
    }
}