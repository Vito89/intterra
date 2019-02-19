package ru.vito.desktop.app.service

import org.jvnet.hk2.annotations.Contract

@Contract
interface Read {

    fun read(contentArgs: Array<String>): Map<String, String>

}
