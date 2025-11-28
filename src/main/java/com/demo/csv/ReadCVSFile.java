package com.demo.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadCVSFile {

    public static void main(String[] args) throws IOException, CsvException {

        // Code to read CSV file will go here

        //File csvFile = new File("/Users/bishnu/workspace/api-automation/Advanced_API_Automation/src/main/resources/testdata/LoginCreds.csv");
        //FileReader fileReader = new FileReader(csvFile);

        InputStream inputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("testdata/LoginCreds.csv");
        InputStreamReader fileReader = new InputStreamReader(inputStream);
        CSVReader csvReader = new CSVReader(fileReader);

        List<String[]> dataList = csvReader.readAll();

        // print all the data from CSV file
       /* for(String[] dataArray : dataList){
            for(String data : dataArray){
                System.out.print(data+" ");
            }
            System.out.println(" ");
        }*/

        for(String[] dataArray : dataList){
            System.out.println(dataArray[0]); // print first column value
            System.out.println(dataArray[1]); // print second column value

        }
    }
}
