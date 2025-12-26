package com.api.utils;

import com.api.request.model.UserCredentials;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class ExcelReaderUtil {

    private ExcelReaderUtil() {
        // private constructor to prevent instantiation
    }

    public static Iterator<UserCredentials> loadTestData() {

        // Read Excel file using Apache POI OOXML Library

        InputStream inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("testdata/PhoenixTestData.xlsx");

        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //focus on the specific sheet
        XSSFSheet sheet =  workbook.getSheet("LoginTestData");

        // read the excel file and store in the ArrayList<UserCredentials>

        // I need to know the indexes for the username and password columns

        XSSFRow headerRow = sheet.getRow(0); // to get the first row

        int usernameColIndex = -1; // -1 indicates not found, because index starts from 0
        int passwordColIndex = -1;
        // iterate through each cell in the header row and find the indexes of username and password columns
        // because we don't want to hardcode the index of these columns
        for(Cell cell: headerRow){
            if(cell.getStringCellValue().trim().equalsIgnoreCase("username")){
                usernameColIndex = cell.getColumnIndex();
            }
            if (cell.getStringCellValue().trim().equalsIgnoreCase("password")){
                passwordColIndex = cell.getColumnIndex();
            }
        }

        System.out.println("Username Column Index: " + usernameColIndex);
        System.out.println("Password Column Index: " + passwordColIndex);

        int lastRowIndex = sheet.getLastRowNum(); // to get the last row index
        XSSFRow row;
        UserCredentials userCredentials;
        ArrayList<UserCredentials> userCredentialsList = new ArrayList<>();
        for(int rowIndex =1; rowIndex <= lastRowIndex; rowIndex++){
            row = sheet.getRow(rowIndex);
            userCredentials = new UserCredentials(
                    row.getCell(usernameColIndex).toString(),
                    row.getCell(passwordColIndex).toString()
            );
            userCredentialsList.add(userCredentials);
        }

        return userCredentialsList.iterator();
    }
}
