package ru.vito.desktop.app.service.impl

import ru.vito.desktop.app.service.Read

import java.util.HashMap

class Reader : Read {

    override fun read(strings: Array<String>?): Map<String, String>? {
        if (strings == null) {
            return null
        }

        val userToEmailsMap = HashMap<String, String>()
        for (string in strings) {
            val wordsSplitArrow = string.split("->".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            if (wordsSplitArrow.size == 2) {
                val sourceLogin = wordsSplitArrow[0]
                val emailsExpected = wordsSplitArrow[1]
                if (sourceLogin.length > 0 || emailsExpected.length > 0) {
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
