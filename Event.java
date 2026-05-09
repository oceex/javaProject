public abstract class Event {
    
    private int eventId;
    private String title;
    private EventDateTime dateTime;
    private String location;
    private Ticket[] tickets;
    private static int nextId = 0;
    static final int MAX_TICKETS_PER_BOOKING = 5;


    public Event(String title, EventDateTime dateTime,String location, Ticket[] tickets) {
        this.title = title;
        this.location = location;
        this.dateTime = dateTime;
        this.tickets = tickets;
        this.eventId=generateId();
    }


    public Event() {
        this.eventId=generateId();
    }


    public void setTickets(Ticket[] tickets) {
        this.tickets = tickets;
    }

    public Ticket[] getTickets() {
        return tickets;
    }


    public int getEventId() {
        return eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public EventDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(EventDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public static int getNextId() {
        return nextId;
    }

    public void displayTickets(){
        if (tickets == null || tickets.length == 0) {
            System.out.println("No tickets available");

        }
        else
            for (int i = 0; i < tickets.length; i++) {
                System.out.println(tickets[i]);
            }
    }
    
    public final boolean isAvailable() {
        if (tickets == null)
            return false;

        for (Ticket t : tickets) {
            if (t != null && t.getStatus() != null && t.getStatus().equalsIgnoreCase("Available")) {
                    return true;
         }
      }
         return false;
    }
    
    public abstract String eventType();

    public static int generateId(){
        return ++nextId;
    }

    @Override
    public String toString(){
        return String.format("Event: %s, Location: %s, %s, Available: %b",title, location, dateTime, isAvailable());
    }
    
}
