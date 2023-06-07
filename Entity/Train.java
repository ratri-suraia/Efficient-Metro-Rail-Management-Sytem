package Entity;

public final class Train extends Transport {

    private TrainSchedule schedule;
    private TrainRoute route;

    public Train(int transportID, String transportName, TrainSchedule schedule, TrainRoute route) {
        super(transportID, transportName);
        this.schedule = schedule;
        this.route = route;
    }
    

    public int getTrainID() {
        return super.getTransportID();
    }

    // Getters and setters for TrainSchedule and TrainRoute

    public TrainSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(TrainSchedule schedule) {
        this.schedule = schedule;
    }

    public TrainRoute getRoute() {
        return route;
    }

    public void setRoute(TrainRoute route) {
        this.route = route;
    }

    // Getters and setters for arrival time, destination, departure time, source using TrainSchedule and TrainRoute

    public String getArrivalTime() {
        return schedule.getArrivalTime();
    }

    public void setArrivalTime(String arrivalTime) {
        schedule.setArrivalTime(arrivalTime);
    }

    public String getDestination() {
        return route.getDestination();
    }

    public void setDestination(String destination) {
        route.setDestination(destination);
    }

    public String getDepartureTime() {
        return schedule.getDepartureTime();
    }

    public void setDepartureTime(String departureTime) {
        schedule.setDepartureTime(departureTime);
    }

    public String getSource() {
        return route.getSource();
    }

    public void setSource(String source) {
        route.setSource(source);
    }
  
    public double calculatePrice(int distance) {
        double basePrice = 100;
        double pricePerKm = 5;
        return basePrice + (distance * pricePerKm);
    }

    @Override
    public void start() {
        System.out.println("Train started.");
    }

    @Override
    public void stop() {
        System.out.println("Train stopped.");
    }
}
