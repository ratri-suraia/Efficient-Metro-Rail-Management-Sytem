package List;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import Entity.TrainSchedule;

public class TrainScheduleList {
    private TrainSchedule[] schedules;
    private int numSchedules;
    private String filename;

    public TrainScheduleList(int capacity, String filename) {
        schedules = new TrainSchedule[capacity];
        numSchedules = 0;
        this.filename = filename;
    }


    public void addSchedule(TrainSchedule schedule) {
        if (numSchedules < schedules.length) {
            schedules[numSchedules] = schedule;
            numSchedules++;
        } else {
            System.out.println("Schedule list is full.");
        }
    }

    public void removeSchedule(TrainSchedule schedule) {
        for (int i = 0; i < numSchedules; i++) {
            if (schedules[i] == schedule) {
                schedules[i] = null;
                for (int j = i; j < numSchedules - 1; j++) {
                    schedules[j] = schedules[j + 1];
                }
                schedules[numSchedules - 1] = null;
                numSchedules--;
                break;
            }
        }
    }

    public void updateSchedule(TrainSchedule schedule) {
        for (int i = 0; i < numSchedules; i++) {
            if (schedules[i].getScheduleID() == schedule.getScheduleID()) {
                schedules[i].setDepartureTime(schedule.getDepartureTime());
                schedules[i].setArrivalTime(schedule.getArrivalTime());
                break;
            }
        }
    }

    public TrainSchedule getScheduleByID(int scheduleID) {
        for (int i = 0; i < numSchedules; i++) {
            if (schedules[i].getScheduleID() == scheduleID) {
                return schedules[i];
            }
        }
        return null;
    }

    public TrainSchedule getScheduleByDeparture(String scheduleDeparture) {
        for (int i = 0; i < numSchedules; i++) {
            if (schedules[i].getDepartureTime().equals(scheduleDeparture)) {
                return schedules[i];
            }
        }
        return null;
    }

    public TrainSchedule getScheduleByArrival(String scheduleArrival) {
        for (int i = 0; i < numSchedules; i++) {
            if (schedules[i].getArrivalTime().equals(scheduleArrival)) {
                return schedules[i];
            }
        }
        return null;
    }

    public TrainSchedule[] getAllSchedules() {
        TrainSchedule[] allSchedules = new TrainSchedule[numSchedules];
        System.arraycopy(schedules, 0, allSchedules, 0, numSchedules);
        return allSchedules;
    }

    public void saveToFile() {
        try (FileWriter writer = new FileWriter(filename)) {
            for (int i = 0; i < numSchedules; i++) {
                TrainSchedule schedule = schedules[i];
                String line = schedule.getScheduleID() + "," + schedule.getDepartureTime() + "," + schedule.getArrivalTime() + System.lineSeparator();
                writer.write(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile() {
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0]);
                    String departureTime = parts[1];
                    String arrivalTime = parts[2];
                    TrainSchedule schedule = new TrainSchedule(id, departureTime, arrivalTime);
                    addSchedule(schedule);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
