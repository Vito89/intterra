package ru.vito.desktop.app;

import ru.vito.desktop.app.models.EmailToLoginMap;
import ru.vito.desktop.app.service.impl.UserDefinitionServiceImpl;

public class Runner {

    /*
     * @param args receiving from input string array
     */
    public static void main(final String[] args) {

        final EmailToLoginMap defineUsers = new UserDefinitionServiceImpl().define(args);
        System.out.println(defineUsers.toString());
    }
}