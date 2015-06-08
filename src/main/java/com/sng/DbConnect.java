package com.sng;

import java.sql.*;

public class DbConnect {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hm2test", "postgres", "postgrespass");
        Statement st = db.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM pg_am");
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
        rs.close();
        st.close();
        db.close();
    }

}