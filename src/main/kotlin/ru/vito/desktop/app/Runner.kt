package ru.vito.desktop.app

import ru.vito.desktop.app.service.impl.Definer
import ru.vito.desktop.app.service.impl.Parser
import ru.vito.desktop.app.service.impl.Reader
import java.util.*

/**
 * @param strings receiving from input string array
 */
fun main(strings: Array<String>) {

    println(Runner.run(strings))
}

class Runner {
    companion object {
        fun run(strings: Array<String>): String {

            val userToEmailsMap: Map<String, HashSet<String>> = Parser().parse(
                    Reader().read(strings)
            )

            val definedUsers = Definer()
                    .define(userToEmailsMap)

            return definedUsers.toString()
        }
    }
}