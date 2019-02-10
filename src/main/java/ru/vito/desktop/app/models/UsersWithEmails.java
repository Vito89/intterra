package ru.vito.desktop.app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersWithEmails implements Serializable {

    private HashMap<String, Emails> usersWithEmails;

    @Override
    public String toString() {

        return "UsersWithEmails{" + // TODO
                "usersWithEmails=" + usersWithEmails +
                '}';
    }
}
