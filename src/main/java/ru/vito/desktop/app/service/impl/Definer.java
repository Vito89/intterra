package ru.vito.desktop.app.service.impl;

import ru.vito.desktop.app.models.DefinedUsers;
import ru.vito.desktop.app.service.Define;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Singleton
public class Definer implements Define {

    @Override
    public DefinedUsers define(Map<String, HashSet<String>> userToEmailsMap) {
        if (userToEmailsMap == null) {
            return null;
        }

        final Map<String, String> emailToLoginsMap = new HashMap<>();
        userToEmailsMap.forEach((user, emails) -> doPutNew(emailToLoginsMap, user, emails));

        return new DefinedUsers(emailToLoginsMap);
    }

    private void doPutNew(final Map<String, String> emailToSetOfLoginMap,
                          final String sourceLogin,
                          final HashSet<String> parsedEmails) {
        if (emailToSetOfLoginMap.isEmpty()) {
            for (String email : parsedEmails) {
                emailToSetOfLoginMap.put(email, sourceLogin);
            }
            return;
        }

        final Set<String> usersInParsedEmails = new HashSet<>();
        for (String email : parsedEmails) {
            if (emailToSetOfLoginMap.containsKey(email)) {
                usersInParsedEmails.add(emailToSetOfLoginMap.get(email));
            }
        }

        if (usersInParsedEmails.size() != 0) {
            usersInParsedEmails.add(sourceLogin);
            mergeMapAndPutNewEmails(usersInParsedEmails, parsedEmails, emailToSetOfLoginMap);
        } else {
            for (String email : parsedEmails) {
                emailToSetOfLoginMap.put(email, sourceLogin);
            }
        }
    }

    private void mergeMapAndPutNewEmails(final Set<String> usersInParsedEmails,
                                         final HashSet<String> parsedEmails,
                                         final Map<String, String> emailToSetOfLoginMap) {
        if (usersInParsedEmails.size() < 2) {
            throw new RuntimeException("Non conditional state.");
        }

        final String mainUser = usersInParsedEmails.iterator().next();
        emailToSetOfLoginMap.forEach((email, user) -> {
            if (usersInParsedEmails.contains(user) && !mainUser.equals(user)) {
                emailToSetOfLoginMap.put(email, mainUser);
            }
        });

        for (String email : parsedEmails) {
            emailToSetOfLoginMap.put(email, mainUser);
        }
    }
}