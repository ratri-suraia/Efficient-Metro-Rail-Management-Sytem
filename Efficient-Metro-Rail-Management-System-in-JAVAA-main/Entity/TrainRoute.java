package Entity;

public class TrainRoute {
    private int id;
    private String source;
    private String destination;
    private int distance;
    private int duration;

    public TrainRoute(int id, String source, String destination, int distance, int duration) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.distance = distance;
        this.duration = duration;
    }

    public int getID() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public int getDistance() {
        return distance;
    }

    public int getDuration() {
        return duration;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double calculatePrice(int distance) {
        double basePrice = 100;
        double pricePerKm = 5;
        return basePrice + (distance * pricePerKm);
    }

    public void displayRoute() {
        System.out.println("Source: " + source);
        System.out.println("Destination: " + destination);
        System.out.println("Distance: " + distance + " km");
        System.out.println("Duration: " + duration + " hours");
    }
    
    @Override
    public String toString() {
        return source + " to " + destination;
    }
}
