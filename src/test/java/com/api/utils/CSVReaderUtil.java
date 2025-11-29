package com.api.utils;

import com.dataproviders.api.bean.UserBean;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class CSVReaderUtil {

    /**
     *
     * Constructor is private
     *
     * static - static methods and variables
     * purpose - Help me Read CSV files and Map it to a Bean/POJO class
     *
     */

    private CSVReaderUtil() {
        // No one can create an object of this class
        // Singleton Class Constructors are private
    }


    public static void loadCSV(String pathOfCSVFile){

        InputStream inputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(pathOfCSVFile);
        InputStreamReader fileReader = new InputStreamReader(inputStream);
        CSVReader csvReader = new CSVReader(fileReader);

        // code to Map the CSV to POJO
        CsvToBean<UserBean> csvToBean = new CsvToBeanBuilder(csvReader)
                .withType(UserBean.class)
                .withIgnoreEmptyLine(true)
                .build();

        List<UserBean> userList = csvToBean.parse();
        System.out.println(userList); // print the list of POJOs
        System.out.println(userList.get(0).getUsername()); // print username of first POJO


    }

}
