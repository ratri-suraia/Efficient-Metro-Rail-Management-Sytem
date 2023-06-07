package Interface;
import java.awt.*;
import java.awt.print.*;

import Entity.Ticket;

public class TicketPrintable implements Printable {
    private Ticket ticket;

    public TicketPrintable(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex >= 1) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        // Print ticket content
        int y = 100;
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Ticket ID: " + ticket.getTicketID(), 50, y);
        y += 30;
        g2d.drawString("Passenger: " + ticket.getPassenger().getPassengerName(), 50, y);
        y += 30;
        g2d.drawString("Train: " + ticket.getTrain().getTransportName(), 50, y);
        y += 30;
        g2d.drawString("Schedule: " + ticket.getTrainSchedule().getScheduleID(), 50, y);
        y += 30;
        g2d.drawString("Distance: " + ticket.getDistance() + " km", 50, y);
        y += 30;
        g2d.drawString("Price: " + ticket.getPrice() + " TK", 50, y);

        return PAGE_EXISTS;
    }
}
