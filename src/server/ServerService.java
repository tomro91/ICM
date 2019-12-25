package server;

import java.io.Serializable;
import java.util.List;

public class ServerService implements Serializable {

    private DatabaseService databaseService;
    private List params;

    public ServerService(DatabaseService databaseService, List params) {
        this.databaseService = databaseService;
        this.params = params;
    }

    public DatabaseService getDatabaseService() {
        return databaseService;
    }

    public void setDatabaseService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public List getParams() {
        return params;
    }

    public void setParams(List params) {
        this.params = params;
    }

    public enum DatabaseService {
        Login,
        Get_All_Requests,
        Get_All_Requests_New,
        Get_Request_Details,
        Update_Request_Status,
        Submit_New_Request,
        View_Evaluation_Report

    }
}
