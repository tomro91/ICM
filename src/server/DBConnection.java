package server;

import entities.*;
import server.ServerService.DatabaseService;

import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import client.crDetails.CrDetails;

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

            user.setId(rs.getInt("IDuser"));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setTitle(ChangeInitiator.Title.valueOf(rs.getString("title")));
            user.setPhoneNumber(rs.getString("phone"));
            user.setDepartment(CiDepartment.valueOf(rs.getString("department")));
            String userPosition = rs.getString("position");
            if (userPosition != null)
                user.setPosition(Position.valueOf(userPosition));

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
                    "SELECT CR.crID, CR.crInfoSystem, CR.crDate, CR.crCurrPhaseName " +
                            "FROM changeRequest CR " +
                            "WHERE CR.crIDuser = ?;"
            );
            ps.setInt(1, currUser.getId());
            // go throw the results and add it to arrayList
            Set<ChangeRequest> tempSet = insertRequestsIntoList(currUser.getId());
            myRequests.addAll(tempSet);
            allRequests.add(myRequests);

            if (currUser.getTitle() != ChangeInitiator.Title.INFOENGINEER)
                return allRequests;


            tempSet = new HashSet<>();
            switch (currUser.getPosition()) {
                case ITD_MANAGER:
                    ps = sqlConnection.prepareStatement("SELECT CR.crID, CR.crInfoSystem, CR.crDate, CR.crCurrPhaseName " +
                            "FROM changeRequest CR " +
                            "WHERE CR.crSuspended = 1");
                    break;
                case SUPERVISOR:
                    ps = sqlConnection.prepareStatement("SELECT CR.crID, CR.crInfoSystem, CR.crDate, CR.crCurrPhaseName " +
                            "FROM changeRequest CR");
                    break;
                case CHAIRMAN: case CCC:
                    ps = sqlConnection.prepareStatement("SELECT CR.crID, CR.crInfoSystem, CR.crDate, CR.crCurrPhaseName " +
                            "FROM changeRequest CR,  ieInPhase IE " +
                            "WHERE CR.crCurrPhaseName = 'EXAMINATION' " +
                                "OR (CR.crCurrPhaseName = IE.iePhaseName AND " +
                                    "IE.iePhasePosition = 'TESTER')");
                    break;
                case REGULAR:
                    ps = sqlConnection.prepareStatement(
                            "SELECT CR.crID, CR.crInfoSystem, CR.crDate, CR.crCurrPhaseName " +
                                    "FROM changeRequest CR, ieInPhase IE " +
                                    "WHERE CR.crID = IE.crID AND " +
                                    "CR.crCurrPhaseName = IE.iePhaseName AND " +
                                    "IE.IDieInPhase = ?");
                    ps.setInt(1, currUser.getId());
                    break;
            }

            // get requests where the user has any position
            tempSet.addAll(insertRequestsIntoList(currUser.getId()));
            inMyTreatmentRequests.addAll(tempSet);

            allRequests.add(inMyTreatmentRequests);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allRequests;
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


    // helper function for getAllRequests()
    private Set<ChangeRequest> insertRequestsIntoList(int userId) throws SQLException {
        ResultSet rs = ps.executeQuery();

        Set<ChangeRequest> requestSet = new HashSet<>();
        rs.beforeFirst();
        while (rs.next()) {
            ChangeRequest row = new ChangeRequest();
            row.setId(rs.getInt("crID"));
            row.setInfoSystem(InfoSystem.valueOf(rs.getString("crInfoSystem")));
            row.setDate(rs.getDate("crDate").toLocalDate());
            row.setCurrPhaseName(Phase.PhaseName.valueOf(rs.getString("crCurrPhaseName")));
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
        List<Phase> crPhaseList = new ArrayList<>();
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

            // get request current phase
            ps = sqlConnection.prepareStatement("SELECT * FROM phase WHERE phIDChangeRequest = ? AND phPhaseName = ?");
            ps.setInt(1, cr.getId());
            ps.setString(2, cr.getCurrPhaseName().toString());

            rs = ps.executeQuery();
            rs.beforeFirst();
            rs.next();

            Phase currPhase = new Phase();

            currPhase.setChangeRequestId(cr.getId());
            currPhase.setName(cr.getCurrPhaseName());
            currPhase.setDeadLine(rs.getDate("phDeadLine").toLocalDate());
            currPhase.setPhaseStatus(Phase.PhaseStatus.valueOf(rs.getString("phStatus")));
            currPhase.setExtensionRequest(rs.getBoolean("phExtensionRequest"));
            Date date = rs.getDate("phExceptionTime");
            if(date != null) {
                LocalDate exceptionDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                currPhase.setExceptionTime(exceptionDate);
            }

            crPhaseList.add(currPhase);
            ps.close();

            // get Information Engineer Phase Position for the current user
            ps = sqlConnection.prepareStatement("SELECT * FROM ieInPhase WHERE crID = ? AND iePhaseName = ?");
            ps.setInt(1, cr.getId());
            ps.setString(2, cr.getCurrPhaseName().toString());

            Map<IEPhasePosition.PhasePosition, IEPhasePosition> iePhasePositionMap = new HashMap<>();

            rs = ps.executeQuery();
            rs.beforeFirst();
            while (rs.next()) {
                IEPhasePosition iePhasePosition = new IEPhasePosition();
                iePhasePosition.setInformationEngineer(new InformationEngineer());
                iePhasePosition.getInformationEngineer().setId(rs.getInt("IDieInPhase"));
                iePhasePosition.setCrID(params.get(1));
                iePhasePosition.setPhaseName(currPhase.getName());
                iePhasePosition.setPhasePosition(IEPhasePosition.PhasePosition.valueOf(rs.getString("iePhasePosition")));

                iePhasePositionMap.put(iePhasePosition.getPhasePosition(),iePhasePosition);
            }

            currPhase.setIePhasePosition(iePhasePositionMap);
            cr.setPhases(crPhaseList);
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


	public List<Boolean> createEvaluationReport(List<String> requirementList1) {
		boolean flag=false;
		List<Boolean>l=new ArrayList<Boolean>();
		//insert new evaluation report to db
		try {
			System.out.println("insert new evaluation report");
			PreparedStatement ps=sqlConnection.prepareStatement("INSERT INTO evaluationReport(cRequestID,infoSystem,requestedChange,expectedResult,risksAndConstraints,EvaluatedTime) VALUES(?,?,?,?,?,?)");
			ps.setInt(1, Integer.parseInt(requirementList1.get(0)));
			ps.setString(2, requirementList1.get(1));
			ps.setString(3, requirementList1.get(2));
			ps.setString(4, requirementList1.get(3));
			ps.setString(5, requirementList1.get(4));
			ps.setDate(6, Date.valueOf(requirementList1.get(5)));
			ps.executeUpdate();
			flag=true;
			l.add(flag);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			flag=false;
			l.add(flag);
			e.printStackTrace();
		}
		//update phase of specific request to examination in change request table
		try {
			System.out.println("update current phase of request to examination");
			PreparedStatement ps1=sqlConnection.prepareStatement("UPDATE changeRequest SET crCurrPhaseName = 'EXAMINATION' WHERE crID = ?");
			ps1.setInt(1, Integer.parseInt(requirementList1.get(0)));
			ps1.executeUpdate();
			flag=true;
			l.add(flag);
		} catch (SQLException e) {
			flag=false;
			l.add(flag);
			e.printStackTrace();
		}
		//update evaluation phase of specific request status to done
		try {
			PreparedStatement ps1=sqlConnection.prepareStatement("UPDATE phase SET phStatus = 'DONE' WHERE phIDChangeRequest = ?");
			ps1.setInt(1, Integer.parseInt(requirementList1.get(0)));
			ps1.executeUpdate();
			flag=true;
			l.add(flag);
		} catch (SQLException e) {
			flag=false;
			l.add(flag);
			e.printStackTrace();
		}
		//insert examination phase to specific request with status phase leader assigned
		try {
			PreparedStatement ps=sqlConnection.prepareStatement("INSERT INTO phase VALUES(?,'EXAMINATION',?,'PHASE_LEADER_ASSIGNED',0,null)");
			ps.setInt(1, Integer.parseInt(requirementList1.get(0)));//id
			LocalDate newDate=CrDetails.getCurrRequest().getPhases().get(0).getDeadLine().plusDays(7);
			ps.setDate(2, Date.valueOf(newDate));
			ps.executeUpdate();
			flag=true;
			l.add(flag);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			flag=false;
			l.add(flag);
			e.printStackTrace();
		}
		return l;
	
	}


	public List<Boolean> requestTimeEvaluation(List<Object> requestTimeDetails) {
		List<Boolean>list=new ArrayList<Boolean>();
		try {
			PreparedStatement ps=sqlConnection.prepareStatement("UPDATE cbaricmy_ICM.phase SET phDeadline = ? ,phStatus='TIME_REQUESTED' WHERE phIDChangeRequest =?");
			ps.setInt(2, (int)requestTimeDetails.get(0));
			ps.setDate(1, Date.valueOf((LocalDate) requestTimeDetails.get(1)));
			ps.executeUpdate();
			list.add(true);
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			list.add(false);
			e.printStackTrace();
		}
		return list;
	}


	public List<Object> forgotPasswordRequest(List<String> params) {
		List<Object>l=new ArrayList<Object>();
		try {
			PreparedStatement ps=sqlConnection.prepareStatement("SELECT IDuser,firstName,password FROM cbaricmy_ICM.users where email=?");
			ps.setString(1, params.get(0));
			ResultSet rs=ps.executeQuery();
			
			if(rs.next()) {
				l.add(true);
				l.add(rs.getInt("IDUser"));
				l.add(rs.getString("firstName"));
				l.add(rs.getString("password"));
				l.add(params.get(0));
			}
			else
				l.add(false);
					
		} 
		catch (SQLException e) {
			
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return l;
	}

}
