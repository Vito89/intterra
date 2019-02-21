package ru.vito.desktop.app.models

import java.io.Serializable
import java.util.*

data class DefinedUsers(val emailToLoginMap: Map<String, String>) : Serializable {

    override fun toString(): String {

        val loginEmailsMap = HashMap<String, HashSet<String>?>()

        emailToLoginMap.forEach { email, login ->
            val emails: HashSet<String>? =
                    if (loginEmailsMap.containsKey(login)) loginEmailsMap[login]
                    else HashSet()

            emails?.add(email)

            loginEmailsMap[login] = emails
        }

        val builder = StringBuilder()
        loginEmailsMap.forEach { login, emails ->
            builder.append(login)
                    .append(" -> ")
                    .append(emails.toString()
                            .replace("[", "")
                            .replace("]", ""))
                    .append("\n")
        }

        return builder.toString()
    }
}
