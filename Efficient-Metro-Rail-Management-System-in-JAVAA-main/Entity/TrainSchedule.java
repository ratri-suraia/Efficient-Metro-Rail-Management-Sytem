package Entity;

public class TrainSchedule {
    private int scheduleID;
    private String departureTime;
    private String arrivalTime;

    public TrainSchedule(int scheduleID, String departureTime, String arrivalTime) {
        this.scheduleID = scheduleID;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public int getScheduleID() {
        return scheduleID;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void displaySchedule() {
        System.out.println("Schedule ID: " + scheduleID);
        System.out.println("Departure Time: " + departureTime);
        System.out.println("Arrival Time: " + arrivalTime);
    }
}
