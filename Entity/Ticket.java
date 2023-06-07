package Entity;

public class Ticket {
    private final int ticketID;
    private String ticketType;
    private int distance;
    private double price;
    private Train train;
    private TrainSchedule trainSchedule;
    private TrainRoute trainRoute;
    private Passenger passenger;

    public Ticket(int ticketID, String ticketType, int distance) {
        this.ticketID = ticketID;
        this.ticketType = ticketType;
        this.distance = distance;
        this.price = calculatePrice(distance);
    }

    public Ticket(int ticketID, Passenger passenger, Train train, int distance, double price) {
        this.ticketID = ticketID;
        this.passenger = passenger;
        this.train = train;
        this.distance = distance;
        this.price = price;
    }

    // Getters and setters for train, trainSchedule, trainRoute, and passenger

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public TrainSchedule getTrainSchedule() {
        return trainSchedule;
    }

    public void setTrainSchedule(TrainSchedule trainSchedule) {
        this.trainSchedule = trainSchedule;
    }

    public TrainRoute getTrainRoute() {
        return trainRoute;
    }

    public void setTrainRoute(TrainRoute trainRoute) {
        this.trainRoute = trainRoute;
        this.distance = trainRoute.getDistance();
        this.price = calculatePrice(distance);
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public int getTicketID() {
        return ticketID;
    }

    public String getTicketType() {
        return ticketType;
    }

    public int getDistance() {
        return distance;
    }

    public double getPrice() {
        return price;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public void setDistance(int distance) {
        this.distance = distance;
        this.price = calculatePrice(distance);
    }

    public double calculatePrice(int distance) {
        double basePrice = 100;
        double pricePerKm = 5;
        return basePrice + (distance * pricePerKm);
    }

    public void showDetails() {
        System.out.println("Ticket ID: " + ticketID);
        System.out.println("Ticket Type: " + ticketType);
        System.out.println("Distance: " + distance + " km");
        System.out.println("Price: " + price + " TK");
    }
}
