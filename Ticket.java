public class Ticket implements Printable{
    private int ticketId;
    private String type;
    private double price;
    private String status;
    private User owner;


    public Ticket(int ticketId, String type, double price, String status, User owner) {
        this.ticketId = ticketId;
        this.type = type;
        this.price = price;
        this.status = status;
        this.owner = owner;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
    
    @Override
public void printDetails() {
    String ownerName = (owner != null) ? owner.getName() : "No owner";
    System.out.println("Ticket ID: " + ticketId +
            " | Type: " + type +
            " | Price: " + price +
            "SAR | Status: " + status +
            " | Owner: " + ownerName);
}

    @Override
    public String toString() {
        return "Ticket{id=" + ticketId + ", price=" + price + " SAR }";
    }
}
    
