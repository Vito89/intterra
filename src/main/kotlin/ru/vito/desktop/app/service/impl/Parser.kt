package ru.vito.desktop.app.service.impl

import ru.vito.desktop.app.common.Utils.EMAIL_PATTERN
import ru.vito.desktop.app.service.Parse
import java.util.*
import java.util.stream.Collectors

class Parser : Parse {

    override fun parse(userToEmailsMap: Map<String, String>): Map<String, HashSet<String>> {
        val userToParsedEmailsMap = HashMap<String, HashSet<String>>()
        userToEmailsMap.forEach { user, emails ->
            val parsedEmails = parseEmails(emails)
            if (parsedEmails.size > 0) {
                userToParsedEmailsMap[user] = parsedEmails
            }
        }

        return userToParsedEmailsMap
    }

    private fun parseEmails(emails: String): HashSet<String> {
        val emailsSet = Arrays.stream(
                emails.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        ).map<String> { it.trim { it <= ' ' } }
                .collect(Collectors.toSet())

        emailsSet.forEach { this.emailIsNotValid(it) }

        return HashSet(emailsSet)
    }

    private fun emailIsNotValid(email: String): Boolean {
        return !EMAIL_PATTERN.matcher(email).matches()
    }
}
