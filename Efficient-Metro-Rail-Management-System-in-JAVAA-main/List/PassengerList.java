package List;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import Entity.Passenger;

public class PassengerList {
    private Passenger[] passengers;
    private int numPassengers;
    private String filename;

    public PassengerList(int capacity, String filename) {
        passengers = new Passenger[capacity];
        numPassengers = 0;
        this.filename = filename;
    }

    public void addPassenger(Passenger passenger) {
        if (numPassengers < passengers.length) {
            passengers[numPassengers] = passenger;
            numPassengers++;
            System.out.println("Add passenger");
            //saveToFile(); // call saveToFile method after adding passenger
        } else {
            System.out.println("Passenger list is full.");
        }
    }

    public void removePassenger(Passenger passenger) {
        for (int i = 0; i < numPassengers; i++) {
            if (passengers[i] == passenger) {
                passengers[i] = null;
                for (int j = i; j < numPassengers - 1; j++) {
                    passengers[j] = passengers[j + 1];
                }
                passengers[numPassengers - 1] = null;
                numPassengers--;
                saveToFile(); // call saveToFile method after removing passenger
                break;
            }
        }
    }
    public void updatePassenger(Passenger passenger) {
        for (int i = 0; i < numPassengers; i++) {
            if (passengers[i].getPassengerID() == passenger.getPassengerID()) {
                passengers[i].setPassengerName(passenger.getPassengerName());
                passengers[i].setPassengerContact(passenger.getPassengerContact());
                passengers[i].setPassengerEmail(passenger.getPassengerEmail());
                saveToFile(); // call saveToFile method after updating passenger
                break;
            }
        }
    }
    

    public Passenger getPassengerByID(int passengerID) {
        for (int i = 0; i < numPassengers; i++) {
            if (passengers[i].getPassengerID() == passengerID) {
                return passengers[i];
            }
        }
        return null;
    }

    public Passenger[] getPassengersByName(String name) {
        Passenger[] matchingPassengers = new Passenger[numPassengers];
        int count = 0;
        for (int i = 0; i < numPassengers; i++) {
            if (passengers[i].getPassengerName().equalsIgnoreCase(name)) {
                matchingPassengers[count] = passengers[i];
                count++;
            }
        }
        if (count > 0) {
            Passenger[] result = new Passenger[count];
            for (int i = 0; i < count; i++) {
                result[i] = matchingPassengers[i];
            }
            return result;
        } else {
            return null;
        }
    }

    public Passenger[] getAllPassengers() {
        Passenger[] allPassengers = new Passenger[numPassengers];
        for (int i = 0; i < numPassengers; i++) {
            allPassengers[i] = passengers[i];
        }
        return allPassengers;
    }

    public void saveToFile() {
        try {
            FileWriter writer = new FileWriter(filename);
            for (int i = 0; i < numPassengers; i++) {
                Passenger passenger = passengers[i];
                writer.write(passenger.getPassengerID() + "," + passenger.getPassengerName() + "," + passenger.getPassengerContact() + "," + passenger.getPassengerEmail() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    int passengerID = Integer.parseInt(parts[0]);
                    String passengerName = parts[1];
                    String passengerContact = parts[2];
                    String passengerEmail = parts[3];
                    Passenger passenger = new Passenger(passengerID, passengerName, passengerContact, passengerEmail);
                    addPassenger(passenger);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}