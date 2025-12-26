package com.api.utils;

import com.dataproviders.api.bean.UserBean;
import com.poiji.bind.Poiji;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

public class ExcelReaderUtil {

    private ExcelReaderUtil() {
        // private constructor to prevent instantiation
    }

    public static <T> Iterator<T> loadTestData(String sheetName, Class<T> clazz) {

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
        XSSFSheet sheet =  workbook.getSheet(sheetName);

        List<T> dataList =  Poiji.fromExcel(sheet, clazz); // UserBean not UserCredentials because Poiji needs bean class with getters and setters
        return dataList.iterator();
    }
}
