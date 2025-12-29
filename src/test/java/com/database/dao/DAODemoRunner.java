package com.database.dao;

import com.api.request.model.CreateJobPayload;
import com.api.utils.CreateJobBeanMapper;
import com.dataproviders.api.bean.CreateJobBean;

import java.util.ArrayList;
import java.util.List;

public class DAODemoRunner {
    public static void main(String[] args) {

        List<CreateJobBean> beanList = CreateJobPayloadDataDao.getCreateJobPayloadData();
        List<CreateJobPayload> payloadList = new ArrayList<>();
        for (CreateJobBean createJobBean : beanList) {
            CreateJobPayload payload = CreateJobBeanMapper.mapper(createJobBean);
            payloadList.add(payload);
        }

        for (CreateJobPayload createJobPayload : payloadList) {
            System.out.println(createJobPayload);
        }

    }
}
