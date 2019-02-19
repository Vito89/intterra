package ru.vito.desktop.app.models

import lombok.Builder
import lombok.Data

import java.io.Serializable
import java.util.HashMap
import java.util.HashSet

@Data
@Builder
class DefinedUsers : Serializable {

    private var emailToLoginMap: Map<String, String>? = null

    constructor() {
        this.emailToLoginMap = HashMap()
    }

    constructor(emailToLoginMap: Map<String, String>) {
        this.emailToLoginMap = emailToLoginMap
    }

    override fun toString(): String {

        val loginEmailsMap = HashMap<String, Set<String>>()
        emailToLoginMap!!.forEach { email, login ->

            val emails = if (loginEmailsMap.containsKey(login))
                loginEmailsMap[login]
            else
                HashSet()
            emails.add(email)

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
