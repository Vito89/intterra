package ru.vito.desktop.app;

import ru.vito.desktop.app.models.DefinedUsers;
import ru.vito.desktop.app.service.impl.Definer;
import ru.vito.desktop.app.service.impl.Parser;
import ru.vito.desktop.app.service.impl.Reader;

import java.util.HashSet;
import java.util.Map;

public class Runner {

    /**
     * @param strings receiving from input string array
     */
    public static void main(final String[] strings) {

        System.out.println(run(strings));
    }

    private static String run(final String[] strings) {

        final Map<String, HashSet<String>> userToEmailsMap =
            new Parser().parse(
                new Reader().read(strings)
            );

        final DefinedUsers definedUsers =
            new Definer()
                .define(userToEmailsMap);

        return definedUsers.toString();
    }
}