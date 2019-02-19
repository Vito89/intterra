package ru.vito.desktop.app.service;

import org.jvnet.hk2.annotations.Contract;
import ru.vito.desktop.app.models.DefinedUsers;

import java.util.HashSet;
import java.util.Map;

@Contract
public interface Define {

    DefinedUsers define(Map<String, HashSet<String>> userToEmailsMap);

}
