package ru.vito.desktop.app;

import ru.vito.desktop.app.models.UsersWithEmails;
import ru.vito.desktop.app.service.impl.UserDefinitionServiceImpl;

public class Runner {

	/*
	 * @param args receiving from input string array
	 */
	public static void main(final String[] args) {

        int i = 0; // TODO remove
        for (String s : args) {
            System.out.println(s.length());
            System.out.println("i=" + i++ + ":= " + s);
        }

        final UsersWithEmails defineUsers = new UserDefinitionServiceImpl().define(args);
        System.out.println(defineUsers.toString());
	}
}