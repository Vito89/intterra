package ru.vito.desktop.app.service

import org.jvnet.hk2.annotations.Contract

@Contract
interface Parse {

    fun parse(userToEmailsMap: Map<String, String>): Any

}
