package com.api.utils;

import com.api.request.model.CreateJobPayload;
import com.dataproviders.api.bean.CreateJobBean;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class ExcelReaderUtil1 {

    public static void main(String[] args) {
        Iterator<CreateJobBean> iterator =  ExcelReaderUtil.loadTestData("testdata/PhoenixTestData.xlsx", "CreateJobTestData", CreateJobBean.class);

        while(iterator.hasNext()) {
            CreateJobBean bean = iterator.next();
            CreateJobPayload createJobPayload = CreateJobBeanMapper.mapper(bean);
            System.out.println(createJobPayload);
        }
    }
}
