package com.dataproviders;

import com.api.request.model.CreateJobPayload;
import com.api.utils.CSVReaderUtil;
import com.api.utils.CreateJobBeanMapper;
import com.api.utils.FakerDataGenerator;
import com.dataproviders.api.bean.CreateJobBean;
import com.dataproviders.api.bean.UserBean;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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
        Iterator<CreateJobPayload> payloadIterator = FakerDataGenerator
                .generateFakeCreateJobData(10);
        return payloadIterator;
    }



}
