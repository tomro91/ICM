package server;

import entities.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;


public class DBConnection {

    private Connection sqlConnection;
    private PreparedStatement ps;

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

    public List<ChangeInitiator> login(List<String> params) {
        System.out.println("database received login request for: " + params);
        List<ChangeInitiator> userDetails = new ArrayList<>();

        try {
            PreparedStatement ps = sqlConnection.prepareStatement("SELECT * FROM users WHERE IDuser = (?) AND password = (?)");
            ps.setString(1, params.get(0));
            ps.setString(2, params.get(1));
            ResultSet rs = ps.executeQuery();

            // wrong user name or password
            if(rs.next() == false) {
                System.out.println("user not found");
                return null;
            }
            ChangeInitiator user = new ChangeInitiator();

            user.setId(rs.getInt(1));
            user.setFirstName(rs.getString(2));
            user.setLastName(rs.getString(3));
            user.setEmail(rs.getString(4));
            user.setPassword(rs.getString(5));
            user.setTitle(ChangeInitiator.Title.valueOf(rs.getString(6)));
            user.setPhoneNumber(rs.getString(7));
            user.setDepartment(CiDepartment.valueOf(rs.getString(8)));
            user.setPosition(Position.valueOf(rs.getString(9)));

            userDetails.add(user);

            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("user details returned");
        return userDetails;
    }

    public List<List<ChangeRequest>> getAllRequests(List<ChangeInitiator> userList) {

        List<ChangeRequest> myRequests = new ArrayList<>();
        List<ChangeRequest> inMyTreatmentRequests = new ArrayList<>();
        List<List<ChangeRequest>> allRequests = new ArrayList<>();

        ChangeInitiator currUser = userList.get(0);
        try {
            // create and execute the query
            // the user is the change initiator
            ps = sqlConnection.prepareStatement(
                    "SELECT CR.crID, CR.crInfoSystem, CR.crDate, CR.crCurrPhaseName, PH.phStatus, U.firstName, U.lastName " +
                    "FROM changeRequest CR, phase PH, users U " +
                    "WHERE CR.crIDuser = ? AND CR.crID = PH.phIDChangeRequest AND CR.crCurrPhaseName = PH.phPhaseName AND PH.phIDPhaseLeader = U.IDuser"
            );

            // go throw the results and add it to arrayList
            Set<ChangeRequest> tempSet = insertRequestsIntoList(currUser.getId());
            myRequests.addAll(tempSet);
            System.out.println("1 succeed");

            // get requests where the user is the phase leader
            ps = sqlConnection.prepareStatement(
                    "SELECT CR.crID, CR.crInfoSystem, CR.crDate, CR.crCurrPhaseName, PH.phStatus, U.firstName, U.lastName " +
                    "FROM changeRequest CR, phase PH, users U " +
                    "WHERE CR.crIDuser = ? AND CR.crID = PH.phIDChangeRequest AND CR.crCurrPhaseName = PH.phPhaseName AND PH.phIDPhaseLeader = U.IDuser"
            );

            tempSet = new HashSet<>();
            tempSet.addAll(insertRequestsIntoList(currUser.getId()));

            // get requests where the user has any position
            ps = sqlConnection.prepareStatement(
                    "SELECT CR.crID, CR.crInfoSystem, CR.crDate, CR.crCurrPhaseName, PH.phStatus, U.firstName, U.lastName " +
                    "FROM changeRequest CR, phase PH, users U, ieInPhase IE " +
                    "WHERE CR.crID = PH.phIDChangeRequest AND PH.phIDChangeRequest = IE.crID AND CR.crCurrPhaseName = PH.phPhaseName AND PH.phPhaseName = IE.iePhaseName AND IE.IDieInPhase = U.IDuser = ?"
            );
            tempSet.addAll(insertRequestsIntoList(currUser.getId()));
            inMyTreatmentRequests.addAll(tempSet);

            allRequests.add(myRequests);
            allRequests.add(inMyTreatmentRequests);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allRequests;
    }


    // helper functions

    private Set<ChangeRequest> insertRequestsIntoList(int userId) throws SQLException {
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();

        Set<ChangeRequest> requestSet = new HashSet<>();
        rs.beforeFirst();
        while (rs.next()) {
            ChangeRequest row = new ChangeRequest();
            row.setId(rs.getInt(1));
            row.setInfoSystem(InfoSystem.valueOf(rs.getString(2)));
            row.setDate(rs.getDate(3).toLocalDate());
            row.setCurrPhase(Phase.PhaseName.valueOf(rs.getString(4)));
            row.setCurrPhaseStatus(Phase.PhaseStatus.valueOf(rs.getString(5)));
            row.setCurrPhasePhaseLeaderName(rs.getString(6) + " " + rs.getString(7));
            requestSet.add(row);
            System.out.println(row);
        }
        ps.close();
        return requestSet;
    }
}


