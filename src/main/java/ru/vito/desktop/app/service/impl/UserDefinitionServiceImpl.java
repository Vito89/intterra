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
        final EmailToLoginMap emailToLoginMap = new EmailToLoginMap();

        for (final String arg : args) {
            putNew(emailToLoginMap, arg.split("->"));
        }

        return emailToLoginMap;
    }

    private void putNew(final EmailToLoginMap usersWithEmails,
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

        final HashMap<String, String> emailToLoginMap = usersWithEmails.getEmailToLoginMap();
        final HashSet<String> parsedEmails = parseEmailList(emailsExpected);
        final String userToRecord = defineUser(emailToLoginMap, parsedEmails).orElse(sourceLogin);

        doPutNew(emailToLoginMap, userToRecord, parsedEmails);
    }

    private Optional<String> defineUser(final HashMap<String, String> emailToLoginMap,
                                        final HashSet<String> parsedEmails) {
        for (String email : parsedEmails) {
            if (emailToLoginMap.containsKey(email)) {
                return Optional.of(emailToLoginMap.get(email));
            }
        }

        return Optional.empty();
    }

    private void doPutNew(final Map<String, String> emailToLoginMap,
                          final String sourceLogin,
                          final HashSet<String> parsedEmails) {

        parsedEmails.forEach(sourceEmail -> {
            if (!emailToLoginMap.containsKey(sourceEmail)) {
                emailToLoginMap.put(sourceEmail, sourceLogin);
            }
        });
    }

    private HashSet<String> parseEmailList(final @NonNull String emailsExpected) {
        final Set<String> emails = Arrays.stream(emailsExpected.split(","))
                .map(String::trim)
                .collect(Collectors.toSet());

        emails.removeIf(this::isNotValid);

        return new HashSet<>(emails);
    }

    private boolean isNotValid(final String email) {
        return !Utils.emailValidatePattern().matcher(email).matches();
    }
}