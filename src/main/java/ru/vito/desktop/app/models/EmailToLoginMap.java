package ru.vito.desktop.app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class EmailToLoginMap implements Serializable {

    private HashMap<String, String> emailToLoginMap;

    public EmailToLoginMap() {
        this.emailToLoginMap = new HashMap<>();
    }

    @Override
    public String toString() {

        final HashMap<String, Set<String>> loginEmailsMap = new HashMap<>();
        emailToLoginMap.forEach((email, login) -> {

            final Set<String> emails = loginEmailsMap.containsKey(login)
                    ? loginEmailsMap.get(login) : new HashSet<>();
            emails.add(email);

            loginEmailsMap.put(login, emails);
        });


        final StringBuilder builder = new StringBuilder();
        loginEmailsMap.forEach((login, emails) ->
                builder.append(login)
                        .append(" -> ")
                        .append(emails.toString()
                                .replace("[", "")
                                .replace("]", ""))
                        .append("\n"));

        return builder.toString();
    }
}
