package Entity;
public class Passenger {
    private int passengerID;
    private int Age;
    private String passengerName;
    private String Gender;
    private String passengerContact;
    private String passengerEmail;
    private Ticket ticket;

    public Passenger(int passengerID, String passengerName, String passengerContact, String passengerEmail) {
        this.passengerID = passengerID;
        this.passengerName = passengerName;
        this.passengerContact = passengerContact;
        this.passengerEmail = passengerEmail;
    }

    public int getPassengerID() {
        return passengerID;
    }

    public void setPassengerID(int passengerID) {
        this.passengerID = passengerID;
    }
    public int getAge() {
        return Age;
    }

    public String getGender() {
        return Gender;
    }


    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerContact() {
        return passengerContact;
    }

    public void setPassengerContact(String passengerContact) {
        this.passengerContact = passengerContact;
    }

    public String getPassengerEmail() {
        return passengerEmail;
    }

    public void setPassengerEmail(String passengerEmail) {
        this.passengerEmail = passengerEmail;
    }

    public Ticket getTicket() {
        
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public void removeTicket() {
        this.ticket = null;
    }
    
    public void showDetails() {
        System.out.println("Passenger ID: " + passengerID);
        System.out.println("Passenger Name: " + passengerName);
        System.out.println("Passenger Contact: " + passengerContact);
        System.out.println("Passenger Email: " + passengerEmail);
        if (ticket != null) {
            System.out.println("Ticket: " + ticket.getTicketID() + " (" + ticket.getTicketType() + ")");
        } else {
            System.out.println("No ticket purchased.");
        }
    }



}