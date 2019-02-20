package ru.vito.desktop.app.service

import ru.vito.desktop.app.models.DefinedUsers
import java.util.*

interface Define {

    fun define(userToEmailsMap: Map<String, HashSet<String>>): DefinedUsers

}
