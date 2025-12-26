package com.dataproviders;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.UserCredentials;
import com.api.utils.*;
import com.dataproviders.api.bean.CreateJobBean;
import com.dataproviders.api.bean.UserBean;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataProviderUtils {

    // DataProvider needs to return either of these :
    // Object[][]
    // Object[]
    // Iterator<>

    @DataProvider(name = "loginAPIDataProvider", parallel = true)
    public static Iterator<UserBean> loginAPIDataProvider() {
        return CSVReaderUtil.loadCSV("testdata/LoginCreds.csv", UserBean.class);
    }


    @DataProvider(name = "CreateJobAPIDataProvider", parallel = true)
    public static Iterator<CreateJobPayload> createJobAPIDataProvider() {
        Iterator<CreateJobBean> createJobBeanIterator =  CSVReaderUtil.loadCSV("testdata/CreateJobData.csv", CreateJobBean.class);

        List<CreateJobPayload> payloadList = new ArrayList<>();
        CreateJobBean tempBean;
        CreateJobPayload tempPayload;
        while (createJobBeanIterator.hasNext()) {
            tempBean = createJobBeanIterator.next();
            tempPayload = CreateJobBeanMapper.mapper(tempBean);
            payloadList.add(tempPayload);
        }

        return payloadList.iterator();

    }



    @DataProvider(name = "CreateJobAPIFakerDataProvider", parallel = true)
    public static Iterator<CreateJobPayload> createJobFakerDataProvider() {
        // Read faker count from system property during runtime
        // mvn test -Denv=qa -DsuiteXmlFile=testng-datadriven.xml -Dgroups=faker -DfakerCount=100
        String fakerCount = System.getProperty("fakerCount", "5"); // default to 5 if not provided during runtime
        int fakerCountInt = Integer.parseInt(fakerCount);

        // Generate faker data based on fakerCountInt
        Iterator<CreateJobPayload> payloadIterator = FakerDataGenerator
                .generateFakeCreateJobData(fakerCountInt);
        return payloadIterator;
    }


    @DataProvider(name = "loginAPIJsonDataProvider", parallel = true)
    public static Iterator<UserCredentials> loginAPIJsonDataProvider() {
        return JsonReaderUtil.loadJSON("testdata/loginAPITestData.json", UserCredentials[].class);
    }


    @DataProvider(name = "CreateJobAPIJsonDataProvider", parallel = true)
    public static Iterator<CreateJobPayload> createJobAPIJsonDataProvider() {
        return JsonReaderUtil.loadJSON("testdata/CreateJobAPIData.json", CreateJobPayload[].class);
    }


    @DataProvider(name = "LoginAPIExcelDataProvider", parallel = true)
    public static Iterator<UserBean> loginAPIExcelDataProvider() {
        return ExcelReaderUtil.loadTestData("LoginTestData", UserBean.class);
    }



}
