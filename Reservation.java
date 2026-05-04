public class Reservation implements Printable{
     private int reservationId;
     private User user;
     private Event event;
     private Ticket[] tickets;
     private String status;

    public Reservation() {
    }
 
    public Reservation(int reservationId, User user, Event event, Ticket[] tickets, String status) {
        this.reservationId = reservationId;
        this.user = user;
        this.event = event;
        this.tickets = tickets;
        this.status = status;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Ticket[] getTickets() {
        return tickets;
    }

    public void setTickets(Ticket[] ticlets) {
        this.tickets = ticlets;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public double calcTotalPrice() {
        double total = 0;
        for (Ticket t : tickets) {
            if (t != null)
                total += t.getPrice();
        }
        return total + (total * VAT);
    }

    public void confirm() { 
        this.status = "Confirmed"; 
    }
    public void cancel() {
         this.status = "Cancelled"; 
        }

    @Override
    public void printDetails() {
        System.out.println("Reservation ID: " + reservationId + " | Status: " + status);
        System.out.println("Total Price: " + calcTotalPrice());
   }
}