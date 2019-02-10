package ru.vito.desktop.app.service;

import org.jvnet.hk2.annotations.Contract;
import ru.vito.desktop.app.models.EmailToLoginMap;

@Contract
public interface UserDefinitionService {

    EmailToLoginMap define(String[] args);

}
