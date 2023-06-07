package List;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

import Entity.Ticket;

public class TicketList {
    private Ticket[] tickets;
    private int size;
    private String filename;

    public TicketList(int capacity, String filename) {
        tickets = new Ticket[capacity];
        size = 0;
        this.filename = filename;
    }

    public int getSize() {
        return size;
    }

    public Ticket getTicket(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return tickets[index];
    }

    public void addTicket(Ticket ticket) {
        if (size < tickets.length) {
            tickets[size] = ticket;
            size++;
        } else {
            System.out.println("Unable to add ticket. TicketList capacity is full.");
        }
    }

    public void removeTicket(Ticket ticket) {
        for (int i = 0; i < size; i++) {
            if (tickets[i].getTicketID() == ticket.getTicketID()) {
                for (int j = i; j < size - 1; j++) {
                    tickets[j] = tickets[j + 1];
                }
                tickets[size - 1] = null;
                size--;
                saveToFile();
                break;
            }
        }
    }

    public void updateTicket(Ticket ticket) {
        for (int i = 0; i < size; i++) {
            if (tickets[i].getTicketID() == ticket.getTicketID()) {
                tickets[i] = ticket;
                saveToFile();
                break;
            }
        }
    }

    public Ticket getTicketByID(int id) {
        for (int i = 0; i < size; i++) {
            if (tickets[i].getTicketID() == id) {
                return tickets[i];
            }
        }
        return null;
    }

    public Ticket[] getTicketsByType(String type) {
        Ticket[] matchingTickets = new Ticket[size];
        int numMatches = 0;
        for (int i = 0; i < size; i++) {
            if (tickets[i].getTicketType().equalsIgnoreCase(type)) {
                matchingTickets[numMatches] = tickets[i];
                numMatches++;
            }
        }
        if (numMatches == 0) {
            return null;
        } else {
            return Arrays.copyOf(matchingTickets, numMatches);
        }
    }

    public Ticket[] getAllTickets() {
        return Arrays.copyOf(tickets, size);
    }

    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new File(filename))) {
            for (int i = 0; i < size; i++) {
                Ticket ticket = tickets[i];
                writer.println(ticket.getTicketID() + "," + ticket.getTicketType() + "," + ticket.getDistance());
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
                String type = parts[1];
                int distance = Integer.parseInt(parts[2]);
                Ticket ticket = new Ticket(id, type, distance);
                addTicket(ticket);
            }

    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
}
}
