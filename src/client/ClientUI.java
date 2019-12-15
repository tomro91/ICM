package client;

import server.ServerService;

public interface ClientUI {
    void handleMessageFromClientController(ServerService serverService);
//    public abstract void displayMsg(Alert.AlertType AlertType, String message);
}
