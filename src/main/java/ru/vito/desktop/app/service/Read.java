package ru.vito.desktop.app.service;

import org.jvnet.hk2.annotations.Contract;

import java.util.Map;

@Contract
public interface Read {

    Map<String, String> read(String[] contentArgs);

}
