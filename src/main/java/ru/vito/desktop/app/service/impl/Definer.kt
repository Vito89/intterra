package ru.vito.desktop.app.service.impl

import ru.vito.desktop.app.models.DefinedUsers
import ru.vito.desktop.app.service.Define

import javax.inject.Singleton
import java.util.HashMap
import java.util.HashSet

@Singleton
class Definer : Define {

    override fun define(userToEmailsMap: Map<String, HashSet<String>>?): DefinedUsers? {
        if (userToEmailsMap == null) {
            return null
        }

        val emailToLoginsMap = HashMap<String, String>()
        userToEmailsMap.forEach { user, emails -> doPutNew(emailToLoginsMap, user, emails) }

        return DefinedUsers(emailToLoginsMap)
    }

    private fun doPutNew(emailToSetOfLoginMap: MutableMap<String, String>,
                         sourceLogin: String,
                         parsedEmails: HashSet<String>) {
        if (emailToSetOfLoginMap.isEmpty()) {
            for (email in parsedEmails) {
                emailToSetOfLoginMap[email] = sourceLogin
            }
            return
        }

        val usersInParsedEmails = HashSet<String>()
        for (email in parsedEmails) {
            if (emailToSetOfLoginMap.containsKey(email)) {
                usersInParsedEmails.add(emailToSetOfLoginMap[email])
            }
        }

        if (usersInParsedEmails.size != 0) {
            usersInParsedEmails.add(sourceLogin)
            mergeMapAndPutNewEmails(usersInParsedEmails, parsedEmails, emailToSetOfLoginMap)
        } else {
            for (email in parsedEmails) {
                emailToSetOfLoginMap[email] = sourceLogin
            }
        }
    }

    private fun mergeMapAndPutNewEmails(usersInParsedEmails: Set<String>,
                                        parsedEmails: HashSet<String>,
                                        emailToSetOfLoginMap: MutableMap<String, String>) {
        if (usersInParsedEmails.size < 2) {
            throw RuntimeException("Non conditional state.")
        }

        val mainUser = usersInParsedEmails.iterator().next()
        emailToSetOfLoginMap.forEach { email, user ->
            if (usersInParsedEmails.contains(user) && mainUser != user) {
                emailToSetOfLoginMap[email] = mainUser
            }
        }

        for (email in parsedEmails) {
            emailToSetOfLoginMap[email] = mainUser
        }
    }
}