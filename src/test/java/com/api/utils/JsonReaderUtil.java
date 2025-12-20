package com.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

public class JsonReaderUtil {

    public static <T> Iterator<T> loadJSON(String fileName, Class<T[]> clazz) {

        //loginAPITestData.json ----> src/test/resources/demodata/loginAPITestData.json
        // covert JSON Object into POJO or Java Object using ObjectMapper ----> Deserialization
        // using library Jackson Databind  ---> ObjectMapper class

        InputStream inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(fileName);

        ObjectMapper objMapper = new ObjectMapper();
        T[] classArray;
        List<T> list = null;
        try {
            classArray = objMapper
                    .readValue(inputStream, clazz);
            list = List.of(classArray);
        } catch (IOException e) {
            e.printStackTrace();
        }

       return list.iterator();
    }
}
