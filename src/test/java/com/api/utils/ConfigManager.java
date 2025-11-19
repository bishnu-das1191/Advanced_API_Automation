package com.api.utils;

import java.io.*;
import java.util.Properties;

public class ConfigManager {

    // why we required ConfigManagerOLD class?
    // 1. To provide a centralized way to access configuration settings
    // 2. To implement Singleton pattern to ensure only one instance handles
    // 3. Responsible for loading and providing access to properties files
    // 4. Retrieve configuration values based on keys
    // 5. It's a Utility class to manage configuration settings and has lots of static methods
    // 6. Objects creation is restricted for this class because of private constructor

    // read configuration settings from a file or environment variables
    private static String path = "config/config.properties";
    private static String env;
    private static Properties prop = new Properties();

    private ConfigManager() {
        //private constructor to prevent instantiation
    }

    // Operation of loading properties file in the memory
    //because its static block it will be executed only once when the class is loaded
    static {

        // this will help execute from terminal with different environment
        // using mvn test -Denv=qa or -Denv=dev or -Denv=uat
        env = System.getProperty("env", "qa"); //default to env qa if not provided in terminal
        env = env.toLowerCase().trim();
        System.out.println("Running... tests on environment: " + env);

        switch (env) { // used java 14+ switch expression for better readability
            case "qa" -> path = "config/config.qa.properties";
            case "dev" -> path = "config/config.dev.properties";
            case "uat" -> path = "config/config.uat.properties";
            default -> path = "config/config.qa.properties";
        }

        //Q. How do you ensure your code is platform independent and Readable? - Thread.separator
        // Using Thread.currentThread().getContextClassLoader() to load resource from classpath
        // This approach is more flexible and avoids hardcoding file paths
        // getResourceAsStream knows to look into src/test/resources folder by default
        InputStream input = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(path);
        if (input == null) {
            throw new RuntimeException("Cannot find the File at path : " + path);
        }

        try {
            prop.load(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }
}
