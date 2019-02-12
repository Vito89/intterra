package ru.vito.desktop.app.service.impl;

import lombok.NonNull;
import ru.vito.desktop.app.common.Utils;
import ru.vito.desktop.app.models.EmailToLoginMap;
import ru.vito.desktop.app.service.UserDefinitionService;

import javax.inject.Singleton;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class UserDefinitionServiceImpl implements UserDefinitionService {

    @Override
    public EmailToLoginMap define(final String[] args) {
        if (args == null) {
            return new EmailToLoginMap();
        }

        try {
            return doDefine(args);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private EmailToLoginMap doDefine(final @NonNull String[] args) {
        final Map<String, String> emailToSetOfLoginMap = new HashMap<>();

        for (final String arg : args) {
            putNew(emailToSetOfLoginMap, arg.split("->"));
        }

        return new EmailToLoginMap(emailToSetOfLoginMap);
    }

    private void putNew(final Map<String, String> emailToSetOfLoginMap,
                        final String[] wordsSplitArrow) {
        if (wordsSplitArrow.length != 2) {
            System.out.println("Warn: Element was missed!");
            return;
        }

        final String sourceLogin = wordsSplitArrow[0];
        final String emailsExpected = wordsSplitArrow[1];
        if (sourceLogin.length() <= 0 || emailsExpected.length() <= 0) {
            System.out.println("Warn: Element was missed!");
            return;
        }

        final HashSet<String> parsedEmails = parseEmailList(emailsExpected);
        doPutNew(emailToSetOfLoginMap, sourceLogin, parsedEmails);
    }

    private HashSet<String> parseEmailList(final @NonNull String emailsExpected) {
        final Set<String> emails = Arrays.stream(emailsExpected.split(","))
                .map(String::trim)
                .collect(Collectors.toSet());

        emails.removeIf(this::isNotValid);

        return new HashSet<>(emails);
    }

    private void doPutNew(final Map<String, String> emailToSetOfLoginMap,
                          final String sourceLogin,
                          final HashSet<String> parsedEmails) {
        if (emailToSetOfLoginMap.isEmpty()) {
            putAllEmails(emailToSetOfLoginMap, sourceLogin, parsedEmails);
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
            putAllEmails(emailToSetOfLoginMap, sourceLogin, parsedEmails);
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

        putAllEmails(emailToSetOfLoginMap, mainUser, parsedEmails);
    }

    private void putAllEmails(final Map<String, String> map, final String login, final HashSet<String> emails) {
        for (String email : emails) {
            map.put(email, login);
        }
    }

    private boolean isNotValid(final String email) {
        return !Utils.emailValidatePattern().matcher(email).matches();
    }
}