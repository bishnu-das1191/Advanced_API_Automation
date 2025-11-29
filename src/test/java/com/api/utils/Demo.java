package com.api.utils;

import com.dataproviders.api.bean.CreateJobBean;

import java.util.Iterator;

public class Demo {
    public static void main(String[] args) {

        Iterator<CreateJobBean> iterator = CSVReaderUtil.loadCSV("testdata/CreateJobData.csv", CreateJobBean.class);
        while (iterator.hasNext()) {
            CreateJobBean jobBean = iterator.next();
            System.out.println(jobBean);
        }

    }
}
