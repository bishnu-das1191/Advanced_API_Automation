package com.database.dao;

import com.database.DatabaseManager;
import com.database.model.MapJobProblemModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MapJobProblemDAO {

    private static final String PROBLEM_QUERY =
            """
            SELECT * from map_job_problem where tr_job_head_id = ?;        
            """;

    private MapJobProblemDAO() {
        // private constructor to prevent instantiation
    }
    public static MapJobProblemModel getProblemDetails(int tr_job_head_id) {
        MapJobProblemModel mapJobProblemModel = null;
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(PROBLEM_QUERY);
            ps.setInt(1, tr_job_head_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                mapJobProblemModel = new MapJobProblemModel(
                        rs.getInt("id"),
                        rs.getInt("tr_job_head_id"),
                        rs.getInt("mst_problem_id"),
                        rs.getString("remark")
                );
                System.out.println("MapJobProblemModel: " + mapJobProblemModel);
            }

        }catch (SQLException e) {
            System.err.println("SQL Exception in MapJobProblem Model: " + e.getMessage());
        }
        return mapJobProblemModel;
    }
}
