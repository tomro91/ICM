package server;

import entities.Requirement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DBConnection {

    private Connection sqlConnection;

    public DBConnection(String url, String username, String password) {
        // Driver definition
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println("Driver definition succeed");
        } catch (Exception ex) {
            /* handle the error*/
            System.out.println("Driver definition failed");
            System.exit(1);
        }

        // SQL connection to server
        try {
            sqlConnection = DriverManager.getConnection("jdbc:mysql://" + url + "?serverTimezone=IST", username, password);
            System.out.println("SQL connection succeed");

        } catch (SQLException ex) {/* handle any errors*/
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            System.exit(1);
        }

    }

    public List<Requirement> getAllRequestsFromRequirement() {

        List<Requirement> resultList = new ArrayList<>();
        try {
            // create and execute the query
            PreparedStatement ps = sqlConnection.prepareStatement("SELECT * FROM Requirement");
            ResultSet resultSet = ps.executeQuery();

            // go throw the results and add it to arrayList
            resultSet.beforeFirst();
            while (resultSet.next()) {
                Requirement row = new Requirement();
                row.setId(Integer.parseInt(resultSet.getString(1)));
                row.setInitiatorName(resultSet.getString(2));
                row.setInfoSystem(resultSet.getString(3));
                row.setCurrState(resultSet.getString(4));
                row.setRequestedChange(resultSet.getString(5));
                row.setPhaseName(resultSet.getString(6));
                row.setIeName(resultSet.getString(7));
                resultList.add(row);
                System.out.println(row);
            }
            ps.close();
            System.out.println("getRequestFromRequirement succeed");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public void updateRequestDetails(List<String> requirementList) {
        try {
            // create and execute the query
            PreparedStatement ps = sqlConnection.prepareStatement("UPDATE Requirement SET rStatus=? WHERE id=?");
            ps.setString(1, requirementList.get(0));
            ps.setInt(2, Integer.parseInt(requirementList.get(1)));
            ps.executeUpdate();
            System.out.println("status updated");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


