package ru.vito.desktop.app.service.impl

import lombok.NonNull
import ru.vito.desktop.app.service.Parse

import java.util.*
import java.util.stream.Collectors

import ru.vito.desktop.app.common.Utils.EMAIL_PATTERN

class Parser : Parse {

    override fun parse(userToEmailsMap: Map<String, String>?): Map<String, HashSet<String>>? {
        if (userToEmailsMap == null) {
            return null
        }

        val userToParsedEmailsMap = HashMap<String, HashSet<String>>()
        userToEmailsMap.forEach { user, emails ->
            val parsedEmails = parseEmails(emails)
            if (parsedEmails.size > 0) {
                userToParsedEmailsMap[user] = parsedEmails
            }
        }

        return userToParsedEmailsMap
    }

    private fun parseEmails(@NonNull emails: String): HashSet<String> {
        val emailsSet = Arrays.stream<String>(emails.split(",".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray())
                .map<String>(Function<String, String> { it.trim({ it <= ' ' }) })
                .collect<Set<String>, Any>(Collectors.toSet())

        emailsSet.removeIf(Predicate<String> { this.emailIsNotValid(it) })

        return HashSet(emailsSet)
    }

    private fun emailIsNotValid(email: String): Boolean {
        return !EMAIL_PATTERN.matcher(email).matches()
    }
}
