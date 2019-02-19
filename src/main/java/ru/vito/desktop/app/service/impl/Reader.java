package ru.vito.desktop.app.service.impl;

import ru.vito.desktop.app.service.Read;

import java.util.HashMap;
import java.util.Map;

public class Reader implements Read {

    @Override
    public Map<String, String> read(final String[] strings) {
        if (strings == null) {
            return null;
        }

        final Map<String, String> userToEmailsMap = new HashMap<>();
        for (final String string : strings) {
            final String[] wordsSplitArrow = string.split("->");
            if (wordsSplitArrow.length == 2) {
                final String sourceLogin = wordsSplitArrow[0];
                final String emailsExpected = wordsSplitArrow[1];
                if (sourceLogin.length() > 0 || emailsExpected.length() > 0) {
                    userToEmailsMap.put(sourceLogin, emailsExpected);
                } else {
                    System.out.println("Warn: Element was missed!");
                }
            } else {
                System.out.println("Warn: Element was missed!");
            }
        }

        return userToEmailsMap;
    }
}
