package ru.vito.desktop.app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;

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

        return "EmailToLoginMap{" + // TODO
                "emailToLoginMap=" + emailToLoginMap +
                '}';
    }
}
