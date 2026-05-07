import java.util.ArrayList;

public class BookingManager {
       
    private ArrayList<Event> events;
    private ArrayList<Reservation> reservation;

    public BookingManager() {
        events=new ArrayList<>();
         reservation=new ArrayList<>();
    }

    public BookingManager(ArrayList<Event> events, ArrayList<Reservation> reservation) {
        this.events = events;
        this.reservation = reservation;
    }
    public ArrayList<Event> getEvents() {
        return events;
    }

    public ArrayList<Reservation> getReservation() {
        return reservation;
    }

    
        public Event viewEventDetails(int eventId){
        for(Event r: events){
        if(r.getEventId()==eventId)
         return r;}
        
        return null;}
        
        
        
        public Reservation bookTicket(User user,Event event, int ticketCount)
           throws TicketLimitExceededException{
              if (ticketCount > Event.MAX_TICKETS_PER_BOOKING) {
        throw new TicketLimitExceededException("Max ticket number exceeded");
    } 
       
        
       Ticket[] eventTickets=event.getTickets();
       ArrayList<Ticket> selected=new ArrayList<>();
       
       for(Ticket t : eventTickets){
       if (t.getStatus().equalsIgnoreCase("Available")) {
    selected.add(t);
    if (selected.size() == ticketCount) break;
}
       
       if (selected.size()<ticketCount){
       throw new RuntimeException("Not enough tickets available");
       
       }
       
       for(Ticket y:selected){
       y.setStatus("Booked");
       y.setOwner(user);
       }
    
       Ticket[] booked= selected.toArray(new Ticket[selected.size()]);
       
       int id = (int)(Math.random() * 1000000);
       Reservation r=new Reservation (id,user,event,booked,"confirmed");
       
       reservation.add(r);
       user.addReservation(r);
        
        
        
        
        return r;}

        public Reservation bookTicket(User user, Event event, int ticketCount, String ticketType)
           throws TicketLimitExceededException{
              if (ticketCount > Event.MAX_TICKETS_PER_BOOKING) {
        throw new TicketLimitExceededException("Max ticket number exceeded");
    } 
       
        
       Ticket[] eventTickets=event.getTickets();
       ArrayList<Ticket> selected=new ArrayList<>();
       
       for(Ticket t : eventTickets){
       if(t.getStatus().equalsIgnoreCase("Available") && t.getType().equalsIgnoreCase(ticketType))
       selected.add(t); if (selected.size()==ticketCount) break;}
       
       if (selected.size()<ticketCount){
       throw new RuntimeException("Not enough " + ticketType + " tickets available");
       
       }
       
       for(Ticket y:selected){
       y.setStatus("Booked");
       y.setOwner(user);
       }
    
       Ticket[] booked= selected.toArray(new Ticket[selected.size()]);
       
       int id = (int)(Math.random() * 1000000);
       Reservation r=new Reservation (id,user,event,booked,"confirmed");
       
       reservation.add(r);
       user.addReservation(r);
        
        
        
        
        return r;}
        
 public void cancelReservation(Reservation r){
    if (r == null) return;

    r.cancel(); 

    for (Ticket t : r.getTickets()) {
        t.setStatus("Available");
        t.setOwner(null);
    }

  
    r.getUser().getReservations().remove(r);

    
    reservation.remove(r);

    System.out.println("Reservation cancelled successfully");
}
        
        
        public void giftTicket(Ticket ticket,User toUser){
        ticket.setOwner(toUser);
            System.out.println(" you successfully sent a gift to :"+toUser.getName());
        }
        
        
        
        public Event[] suggestEvents(Customer customer){
            ArrayList<Event> suggested=new ArrayList<>();
            for(Event e:events){
                for(String interest: customer.getInterests()){
                    if(e.getClass().getSimpleName().toLowerCase().contains(interest.toLowerCase()) || interest.toLowerCase().contains(e.getClass().getSimpleName().toLowerCase())){
                        suggested.add(e);
                        break;
                    }
                }
            }
            return suggested.toArray(new Event[suggested.size()]); 
        }

        public User findUserByEmail(String email) {
            for (Event e : events) {
                for (Ticket t : e.getTickets()) {
                    if (t.getOwner() != null &&
                        t.getOwner().getEmail().equalsIgnoreCase(email)) {
                        return t.getOwner();
                    }
                }
            }
            return null;
        }

}
