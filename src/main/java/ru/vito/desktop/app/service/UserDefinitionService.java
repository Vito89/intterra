package ru.vito.desktop.app.service;

import org.jvnet.hk2.annotations.Contract;
import ru.vito.desktop.app.models.UsersWithEmails;

@Contract
public interface UserDefinitionService {

    UsersWithEmails define (String[] args);

}
