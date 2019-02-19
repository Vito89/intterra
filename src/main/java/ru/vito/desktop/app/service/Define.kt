package ru.vito.desktop.app.service

import org.jvnet.hk2.annotations.Contract
import ru.vito.desktop.app.models.DefinedUsers
import java.util.*

@Contract
interface Define {

    fun define(userToEmailsMap: Map<String, HashSet<String>>): DefinedUsers

}
