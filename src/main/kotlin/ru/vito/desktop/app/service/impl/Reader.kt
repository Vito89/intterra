package ru.vito.desktop.app.service.impl

import ru.vito.desktop.app.service.Read
import java.util.*

class Reader : Read {

    override fun read(contentArgs: Array<String>): Map<String, String> {
        val userToEmailsMap = HashMap<String, String>()
        for (string in contentArgs) {
            val wordsSplitArrow = string.split("->".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (wordsSplitArrow.size == 2) {
                val sourceLogin = wordsSplitArrow[0]
                val emailsExpected = wordsSplitArrow[1]
                if (sourceLogin.isNotEmpty() || emailsExpected.isNotEmpty()) {
                    userToEmailsMap[sourceLogin] = emailsExpected
                } else {
                    println("Warn: Element was missed!")
                }
            } else {
                println("Warn: Element was missed!")
            }
        }

        return userToEmailsMap
    }
}
