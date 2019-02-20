package ru.vito.desktop.app.service

import java.util.*

interface Parse {

    fun parse(userToEmailsMap: Map<String, String>): Map<String, HashSet<String>>

}
