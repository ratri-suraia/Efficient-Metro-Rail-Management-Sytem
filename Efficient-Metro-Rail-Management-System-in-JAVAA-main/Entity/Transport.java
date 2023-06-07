package Entity;
public abstract class Transport {
    private int transportID;
    private String transportName;

    public Transport(int transportID, String transportName) {
        this.transportID = transportID;
        this.transportName = transportName;
    }

    public int getTransportID() {
        return transportID;
    }

    public String getTransportName() {
        return transportName;
    }

    public void setTransportID(int transportID) {
        this.transportID = transportID;
    }

    public void setTransportName(String transportName) {
        this.transportName = transportName;
    }

    public abstract void start();

    public abstract void stop();
}


