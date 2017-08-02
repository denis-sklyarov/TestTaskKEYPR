package com.test.util;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Properties;

public class Config {
    public static final String USER_NAME;
    public static final String TOKEN;
    public static final String REPOSITORY_NAME;
    public static final String BASE_URL;
    public static final String REPOS_ENDPOINT;

    static {
        Properties prop = new Properties();

        try {
            prop.load(Config.class.getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        USER_NAME = prop.getProperty("user.name");
        if (System.getProperty("token") == null){
            throw new RuntimeException("Please provide github access token to call api");
        }

        TOKEN = System.getProperty("token");
        REPOSITORY_NAME = prop.getProperty("repository.name");
        BASE_URL = prop.getProperty("base.url");
        REPOS_ENDPOINT = prop.getProperty("repos.endpoint");
    }
}
