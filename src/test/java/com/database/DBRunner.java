package com.database;

import java.sql.SQLException;

public class DBRunner {

    public static void main(String[] args) throws SQLException {

        DatabaseManager.createConnection();
    }
}
