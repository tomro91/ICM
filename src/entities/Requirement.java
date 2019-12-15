package entities;

import java.io.Serializable;
import java.util.Objects;

public class Requirement implements Serializable {

    private Integer id;
    private String initiatorName;
    private String infoSystem;
    private String currState;
    private String requestedChange;
    private String phaseName;
    private String ieName;


    public Requirement() {
    }

    public Requirement(Integer id, String initiatorName, String infoSystem, String currState, String requestedChange, String phaseName, String ieName) {
        this.id = id;
        this.initiatorName = initiatorName;
        this.infoSystem = infoSystem;
        this.currState = currState;
        this.requestedChange = requestedChange;
        this.phaseName = phaseName;
        this.ieName = ieName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInitiatorName() {
        return initiatorName;
    }

    public void setInitiatorName(String initiatorName) {
        this.initiatorName = initiatorName;
    }

    public String getInfoSystem() {
        return infoSystem;
    }

    public void setInfoSystem(String infoSystem) {
        this.infoSystem = infoSystem;
    }

    public String getCurrState() {
        return currState;
    }

    public void setCurrState(String currState) {
        this.currState = currState;
    }

    public String getRequestedChange() {
        return requestedChange;
    }

    public void setRequestedChange(String requestedChange) {
        this.requestedChange = requestedChange;
    }

    public String getPhaseName() {
        return phaseName;
    }

    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }

    public String getIeName() {
        return ieName;
    }

    public void setIeName(String ieName) {
        this.ieName = ieName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Requirement that = (Requirement) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Requirement{" +
                "id=" + id +
                ", initiatorName='" + initiatorName + '\'' +
                ", infoSystem='" + infoSystem + '\'' +
                ", currState='" + currState + '\'' +
                ", requestedChange='" + requestedChange + '\'' +
                ", phaseName='" + phaseName + '\'' +
                ", ieName='" + ieName + '\'' +
                '}';
    }
}
