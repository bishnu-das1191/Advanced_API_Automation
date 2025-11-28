package com.demo.csv;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ReadCVSFile_MapToPOJO {

    public static void main(String[] args) throws IOException, CsvException {

        InputStream inputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("testdata/LoginCreds.csv");
        InputStreamReader fileReader = new InputStreamReader(inputStream);
        CSVReader csvReader = new CSVReader(fileReader);

        // Write the code to Map the CSV to POJO

        CsvToBean<UserPOJO> csvToBean = new CsvToBeanBuilder(csvReader)
                .withType(UserPOJO.class)
                .withIgnoreEmptyLine(true)
                .build();

        List<UserPOJO> userList = csvToBean.parse();
        System.out.println(userList); // print the list of POJOs
        System.out.println(userList.get(0).getUsername()); // print username of first POJO


    }
}
