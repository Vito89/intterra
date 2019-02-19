package ru.vito.desktop.app.service.impl;

import lombok.NonNull;
import ru.vito.desktop.app.service.Parse;

import java.util.*;
import java.util.stream.Collectors;

import static ru.vito.desktop.app.common.Utils.EMAIL_PATTERN;

public class Parser implements Parse {

    public Map<String, HashSet<String>> parse(final Map<String, String> userToEmailsMap) {
        if (userToEmailsMap == null) {
            return null;
        }

        final Map<String, HashSet<String>> userToParsedEmailsMap = new HashMap<>();
        userToEmailsMap.forEach((user, emails) -> {
                final HashSet<String> parsedEmails = parseEmails(emails);
                if (parsedEmails.size() > 0) {
                    userToParsedEmailsMap.put(user, parsedEmails);
                }
            }
        );

        return userToParsedEmailsMap;
    }

    private HashSet<String> parseEmails(final @NonNull String emails) {
        final Set<String> emailsSet = Arrays.stream(emails.split(","))
            .map(String::trim)
            .collect(Collectors.toSet());

        emailsSet.removeIf(this::emailIsNotValid);

        return new HashSet<>(emailsSet);
    }

    private boolean emailIsNotValid(final String email) {
        return !EMAIL_PATTERN.matcher(email).matches();
    }
}
