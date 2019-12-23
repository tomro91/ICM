package server;

import entities.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.Date;


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
            PreparedStatement ps = sqlConnection.prepareStatement("SELECT * FROM users WHERE IDuser = ? AND password = ?");
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
            Set<ChangeRequest> tempSet = (Set<ChangeRequest>) insertRequestsIntoList(currUser.getId());
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

    // helper function for getAllRequests()
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
            row.setCurrPhaseName(Phase.PhaseName.valueOf(rs.getString(4)));
            row.setCurrPhaseStatus(Phase.PhaseStatus.valueOf(rs.getString(5)));
            row.setCurrPhasePhaseLeaderName(rs.getString(6) + " " + rs.getString(7));
            requestSet.add(row);
            System.out.println(row);
        }
        ps.close();
        return requestSet;
    }


    /**
     * This method handles any messages received from the client.
     *
     * @param params [0]    current user ID.
     * @param params [1]    change request ID.
     */
    public List<ChangeRequest> getRequestDetails(List<Integer> params) {
        ChangeRequest cr = new ChangeRequest();
        List<Phase> crPhases = new ArrayList<>();
        List<ChangeRequest> crList = new ArrayList<>();

        try {
            // get request basic info
            ps = sqlConnection.prepareStatement("SELECT * FROM changeRequest WHERE crID = ?");
            ps.setInt(1 ,params.get(1));

            ResultSet rs = ps.executeQuery();
            rs.beforeFirst();
            rs.next();

            ChangeInitiator initiator = new ChangeInitiator();
            cr.setId(rs.getInt("crID"));
            cr.setInfoSystem(InfoSystem.valueOf(rs.getString("crInfoSystem")));
            cr.setInitiator(initiator);
            initiator.setId(rs.getInt("crIDuser"));
            cr.setDate(rs.getDate("crDate").toLocalDate());
            cr.setCurrState(rs.getString("crCurrState"));
            cr.setRequestedChange(rs.getString("crRequestedChange"));
            cr.setReasonForChange(rs.getString("crReasonForChange"));
            cr.setComment(rs.getString("crComments"));
            cr.setCurrPhaseName(Phase.PhaseName.valueOf(rs.getString("crCurrPhaseName")));
            cr.setSuspended(rs.getBoolean("crSuspended"));

            ps.close();

            // get request phases
            ps = sqlConnection.prepareStatement("SELECT * FROM phase WHERE phIDChangeRequest = ? AND phPhaseName = ?");
            ps.setInt(1, cr.getId());
            ps.setString(2, cr.getCurrPhaseName().toString());

            rs = ps.executeQuery();
            rs.beforeFirst();
            rs.next();

            Phase currPhase = new Phase();
            InformationEngineer phaseLeader = new InformationEngineer();

            currPhase.setChangeRequestId(cr.getId());
            currPhase.setName(cr.getCurrPhaseName());
            currPhase.setLeader(phaseLeader);
            phaseLeader.setId(rs.getInt("phIDPhaseLeader"));
            currPhase.setDeadLine(rs.getDate("phDeadLine").toLocalDate());
            currPhase.setPhaseStatus(Phase.PhaseStatus.valueOf(rs.getString("phStatus")));
            currPhase.setExtensionRequest(rs.getBoolean("phExtensionRequest"));
            Date date = rs.getDate("phExceptionTime");
            if(date != null) {
                LocalDate exceptionDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                currPhase.setExceptionTime(exceptionDate);
            }

            crPhases.add(currPhase);
            ps.close();

            // get Information Engineer Phase Position for the current user
            ps = sqlConnection.prepareStatement("SELECT * FROM ieInPhase WHERE crID = ? AND iePhaseName = ? AND IDieInPhase = ?");
            ps.setInt(1, cr.getId());
            ps.setString(2, cr.getCurrPhaseName().toString());
            ps.setInt(3, params.get(0));

            List<IEPhasePosition> iePhasePositionArrayList = new ArrayList<>();

            rs = ps.executeQuery();
            rs.beforeFirst();
            if(rs.next() != false) {
                IEPhasePosition iePhasePosition = new IEPhasePosition();
                iePhasePosition.setInformationEngineer(new InformationEngineer());
                iePhasePosition.getInformationEngineer().setId(params.get(1));
                iePhasePosition.setPhaseName(currPhase.getName());
                iePhasePosition.setPhasePosition(IEPhasePosition.PhasePosition.valueOf(rs.getString("iePhasePosition")));

                iePhasePositionArrayList.add(iePhasePosition);
            }
            currPhase.setIePhasePosition(iePhasePositionArrayList);
            ps.close();

            // get phase Leader info
            ps = sqlConnection.prepareStatement("SELECT * FROM users WHERE IDuser = ?");
            ps.setInt(1, currPhase.getLeader().getId());
            rs = ps.executeQuery();
            rs.beforeFirst();
            rs.next();

            phaseLeader.setFirstName(rs.getString("firstName"));
            phaseLeader.setLastName(rs.getString("lastName"));
            phaseLeader.setEmail(rs.getString("email"));

            cr.setPhases(crPhases);

            crList.add(cr);
            ps.close();

            // get initiator details
            ps = sqlConnection.prepareStatement("SELECT * FROM users WHERE IDuser = ?");
            ps.setInt(1, cr.getInitiator().getId());
            rs = ps.executeQuery();
            rs.beforeFirst();
            rs.next();

            initiator.setFirstName(rs.getString("firstName"));
            initiator.setLastName(rs.getString("lastName"));
            initiator.setEmail(rs.getString("email"));
            ps.close();

            System.out.println("database got leader");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return crList;
    }

}


