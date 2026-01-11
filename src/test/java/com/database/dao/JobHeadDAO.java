package com.database.dao;

import com.database.DatabaseManager;
import com.database.model.JobHeadModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JobHeadDAO {
    private static final String JOB_HEAD_QUERY =
            """
            SELECT * from tr_job_head where tr_customer_id = ?;                                                   
            """;

    private JobHeadDAO() {
        // private constructor to prevent instantiation
    }

    public static JobHeadModel getJobHeadData(int tr_customer_id) {
        // Implementation goes here
        JobHeadModel jobHeadModel = null;
        try{
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(JOB_HEAD_QUERY);
            ps.setInt(1, tr_customer_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                jobHeadModel = new JobHeadModel(
                        rs.getInt("id"),
                        rs.getString("job_number"),
                        rs.getInt("tr_customer_id"),
                        rs.getInt("tr_customer_product_id"),
                        rs.getInt("mst_service_location_id"),
                        rs.getInt("mst_platform_id"),
                        rs.getInt("mst_warrenty_status_id"),
                        rs.getInt("mst_oem_id")
                );
                System.out.println("JobHeadModel Data form DB : " + jobHeadModel);
            }
        }catch (SQLException e) {
            System.err.println("SQL Exception in JobHeadDAO: " + e.getMessage());
        }

        return jobHeadModel;
    }
}
