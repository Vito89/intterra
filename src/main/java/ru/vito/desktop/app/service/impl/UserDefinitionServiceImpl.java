package ru.vito.desktop.app.service.impl;

import lombok.NonNull;
import ru.vito.desktop.app.models.Emails;
import ru.vito.desktop.app.models.UsersWithEmails;
import ru.vito.desktop.app.service.UserDefinitionService;

import javax.inject.Singleton;
import java.util.HashMap;

@Singleton
public class UserDefinitionServiceImpl implements UserDefinitionService {

    @Override
    public UsersWithEmails define(final String[] args) {
        if (args == null) {
            return new UsersWithEmails();
        }

        try {
            return doDefine(args);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private UsersWithEmails doDefine(final @NonNull String[] args) {
        final UsersWithEmails usersWithEmails = new UsersWithEmails(new HashMap<>());

        for (final String arg : args) {
            putNew(usersWithEmails, arg.split("->"));
        }

        return compact(usersWithEmails);
    }

    private void putNew(final UsersWithEmails usersWithEmails, final String[] wordsInRow) {
        if (wordsInRow.length != 2) {
            System.out.println("Warn: Element was missed!");
            return;
        }

        final String userExpectedWord = wordsInRow[0];
        final String emailExpectedWords = wordsInRow[1];
        if (userExpectedWord.length() <= 0 || emailExpectedWords.length() <= 0) {
            System.out.println("Warn: Element was missed!");
            return;
        }

        final HashMap<String, Emails> usersEmails = usersWithEmails.getUsersWithEmails();
        if (usersEmails.containsKey(userExpectedWord)) {
            throw new RuntimeException("Error: User already exists.");
        }

        usersEmails.put(userExpectedWord, parseEmailList(emailExpectedWords));
    }

    private UsersWithEmails compact(final UsersWithEmails usersWithEmails) {
        // TODO
        return null;
    }

    private Emails parseEmailList(final String emailExpectedWords) {
        // TODO
        return null;
    }
}
