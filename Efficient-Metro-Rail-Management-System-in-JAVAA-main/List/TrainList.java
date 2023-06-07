package List;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import Entity.*;
import Entity.Train;

public class TrainList {
    private Train[] trains;
    private int size;
    private String filename;

    public TrainList(int capacity, String filename) {
        trains = new Train[capacity];
        size = 0;
        this.filename = filename;
    }

    public int getSize() {
        return size;
    }

    public Train getTrain(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return trains[index];
    }

    public void addTrain(Train train) {
        if (size < trains.length) {
            trains[size] = train;
            size++;
        } else {
            System.out.println("Unable to add train. TrainList capacity is full.");
        }
    }

    public void removeTrain(Train train) {
        for (int i = 0; i < size; i++) {
            if (trains[i].getTransportID() == train.getTransportID()) {
                for (int j = i; j < size - 1; j++) {
                    trains[j] = trains[j + 1];
                }
                trains[size - 1] = null;
                size--;
                saveToFile();
                break;
            }
        }
    }

    public void updateTrain(Train train) {
        for (int i = 0; i < size; i++) {
            if (trains[i].getTransportID() == train.getTransportID()) {
                trains[i] = train;
                saveToFile();
                break;
            }
        }
    }

    public Train getTrainByID(int id) {
        for (int i = 0; i < size; i++) {
            if (trains[i].getTransportID() == id) {
                return trains[i];
            }
        }
        return null;
    }

    public Train[] getTrainsByName(String searchTerm) {
        Train[] matchingTrains = new Train[size];
        int numMatches = 0;
        for (int i = 0; i < size; i++) {
            if (trains[i].getTransportName().equalsIgnoreCase(searchTerm)) {
                matchingTrains[numMatches] = trains[i];
                numMatches++;
            }
        }
        if (numMatches == 0) {
            return null;
        } else {
            return Arrays.copyOf(matchingTrains, numMatches);
        }
    }

    public Train[] getAllTrains() {
        return Arrays.copyOf(trains, size);
    }

   
    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new File(filename))) {
            for (int i = 0; i < size; i++) {
                Train train = trains[i];
                TrainRoute route = train.getRoute();
                TrainSchedule schedule = train.getSchedule();
                writer.println(train.getTransportID() + "," + train.getTransportName() + "," + route.getSource() + "," + route.getDestination() + "," + schedule.getDepartureTime() + "," + schedule.getArrivalTime());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile() {
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String source = parts[2];
                String destination = parts[3];
                String departureTime = parts[4];
                String arrivalTime = parts[5];
                TrainRoute route = new TrainRoute(id, source, destination, id, id);
                TrainSchedule schedule = new TrainSchedule(id, departureTime, arrivalTime);
            Train train = new Train(id, name, schedule, route);
                addTrain(train);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
