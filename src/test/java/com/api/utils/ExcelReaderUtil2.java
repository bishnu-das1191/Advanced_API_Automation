package com.api.utils;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;

public class ExcelReaderUtil2 {

    public static void main(String[] args) throws IOException {

        // Read Excel file using Apache POI OOXML Library

        InputStream inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("testdata/PhoenixTestData.xlsx");

        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet =  workbook.getSheet("LoginTestData");
        XSSFRow row = sheet.getRow(1);
        System.out.println(row.getCell(0));
        System.out.println(row.getCell(1));

        // Get total number of rows
        int lastRowIndex = sheet.getLastRowNum();
        System.out.println("Last Index of Row: " + lastRowIndex);

        // Get total number of columns
        XSSFRow rowHeader = sheet.getRow(0);
        int lastIndexOfCol = rowHeader.getLastCellNum()-1; //-1 to get last cell index
        System.out.println("Last Index of Col: " + lastIndexOfCol);

        // Print all data from excel sheet
        for(int r=0; r<=lastRowIndex; r++){
            XSSFRow currentRow = sheet.getRow(r);
            for(int c=0; c<=lastIndexOfCol; c++){
                System.out.print(currentRow.getCell(c) + "\t");
            }
            System.out.println();
        }

    }
}
