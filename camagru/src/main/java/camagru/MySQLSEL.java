package camagru;

import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;
//import org.springframework.jdbc.core.JdbcTemplate;

public class MySQLSEL {
    private String url;
    private String user;
    private String pass;

    MySQLSEL() {
        this.url = "jdbc:mysql://localhost:3306/camagru?autoReconnect=true&useSSL=false";
        this.user = "root";
        this.pass = "root";
    }

    public void select(String query) {
        JdbcTemplate jdbc;
        PreparedStatement statement;
        try {
            Connection myConn = DriverManager.getConnection(this.url, this.user, this.pass);
            Statement myStmt = myConn.createStatement();
            ResultSet result = myStmt.executeQuery(query);

            //
             while(result.next())
                 System.out.println(result.getString("email"));
            //
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
