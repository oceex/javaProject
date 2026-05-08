import java.io.*;
import java.util.*;
import javafx.application.Application;


public class TheREalOne {

    public static void main(String[] args) {
        try{
        Scanner k = new Scanner(System.in);

        BookingManager manager = new BookingManager();

        // Music 1
        EventDateTime dt1 = new EventDateTime("10-10-2026", "8PM");
        Ticket[] tickets1 = new Ticket[]{
                new Ticket(1, "VIP", 500, "Available", null),
                new Ticket(2, "VIP", 500, "Available", null),
                new Ticket(3, "Regular", 200, "Available", null),
                new Ticket(4, "Regular", 200, "Available", null),
                new Ticket(5, "Economy", 100, "Available", null)
        };
        Event event1 = new MusicEvent("Concert", dt1, "Riyadh", tickets1, "ArtistX", "Pop");
        manager.getEvents().add(event1);

        // Music 2
        EventDateTime dt2 = new EventDateTime("15-11-2026", "7PM");
        Ticket[] tickets2 = new Ticket[]{
                new Ticket(6, "VIP", 600, "Available", null),
                new Ticket(7, "Regular", 250, "Available", null),
                new Ticket(8, "Economy", 120, "Available", null)
        };
        Event event2 = new MusicEvent("Jazz Night", dt2, "Jeddah", tickets2, "The Jazz Collective", "Jazz");
        manager.getEvents().add(event2);

        // Sport 1
        EventDateTime dt3 = new EventDateTime("20-10-2026", "6PM");
        Ticket[] tickets3 = new Ticket[]{
                new Ticket(9, "Season", 1500, "Available", null),
                new Ticket(10, "Regular", 300, "Available", null),
                new Ticket(11, "Regular", 300, "Available", null)
        };
        Event event3 = new SportEvent("Football Championship", dt3, "Dammam", tickets3, "Al-Hilal", "Al-Nassr");
        manager.getEvents().add(event3);

        // Sport 2
        EventDateTime dt4 = new EventDateTime("05-01-2027", "5PM");
        Ticket[] tickets4 = new Ticket[]{
                new Ticket(12, "VIP", 800, "Available", null),
                new Ticket(13, "Regular", 250, "Available", null)
        };
        Event event4 = new SportEvent("Tennis Tournament", dt4, "Diriyah", tickets4, "Djokovic", "Alcaraz");
        manager.getEvents().add(event4);

        // Conference
        EventDateTime dt5 = new EventDateTime("10-12-2026", "10AM");
        Ticket[] tickets5 = new Ticket[]{
                new Ticket(14, "VIP", 400, "Available", null),
                new Ticket(15, "VIP", 400, "Available", null),
                new Ticket(16, "Regular", 150, "Available", null),
                new Ticket(17, "Regular", 150, "Available", null),
                new Ticket(18, "Student", 50, "Available", null)
        };
        Event event5 = new Conference("Tech Conference", dt5, "Riyadh", tickets5, "AI in Healthcare", "Dr. Smith");
        manager.getEvents().add(event5);

        AuthService auth = new AuthService();
        auth.loadUsersFromFile();
        if (auth.getUsers().isEmpty()) {
            auth.getUsers().add(new Employee(100, "Admin", "admin@company.com", "admin123", "manager"));
            auth.getUsers().add(new Employee(101, "Support", "support@company.com", "support123", "supporter"));
        }

        int choice = 0;
        User logged = null;

        while (choice != 5) {

            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1- SIGN UP");
            System.out.println("2- LOGIN");
            System.out.println("3- BROWSE EVENTS");
            System.out.println("4- SHOW EVENTS IN GUI");
            System.out.println("5- EXIT");
            System.out.print("Choose: ");
            choice = k.nextInt();

            switch (choice) {

                case 1:
                    System.out.println("\n--- SIGN UP ---");
                    System.out.println("1- Customer");
                    System.out.println("2- Employee");
                    int type = k.nextInt();

                    System.out.print("ID: ");
                    long id = k.nextLong();
                    while (!(id >= 1100000000 && id < 1200000000)) {
                        System.out.println(":< ID must be 10 digits and start with 11");
                        System.out.print("ID: ");
                        id = k.nextLong();
                    }

                    System.out.print("Name: ");
                    String name = k.next();

                    System.out.print("Email: ");
                    String email = k.next();
                    while (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                        System.out.println(":< Invalid email format");
                        System.out.print("Email: ");
                        email = k.next();
                    }

                    String pass = "";
                    boolean valid = false;

                    while (!valid) {
                        System.out.print("Password: ");
                        pass = k.next();

                        if (pass.length() < 6) {
                            System.out.println(":< Password must be at least 6 characters");
                        } else if (!pass.matches(".*[0-9].*")) {
                            System.out.println(":< Password must contain at least one digit");
                        } else if (!pass.matches(".*[A-Za-z].*")) {
                            System.out.println(":< Password must contain at least one letter");
                        } else {
                            valid = true;
                        }
                    }
                    System.out.println(";) Password accepted");

                    if (type == 1) {
                        System.out.println("Enter interests (type 'done' to stop):");
                        ArrayList<String> interests = new ArrayList<>();
                        String inter = "";

                        while (!inter.equalsIgnoreCase("done")) {
                            inter = k.next();
                            if (!inter.equalsIgnoreCase("done"))
                                interests.add(inter);
                        }

                        int i = auth.signUp(new Customer((int)id, name, email, pass, interests));
                        if (i == 0) {
                            System.out.println(";) Customer created successfully");
                            auth.saveUsersToFile();
                        }

                    } else if (type == 2) {
                        System.out.print("Role (manager/supporter): ");
                        String role = k.next();

                        int n = auth.signUp(new Employee((int)id, name, email, pass, role));
                        if (n == 0) {
                            System.out.println(";) Employee created successfully");
                            auth.saveUsersToFile();
                        }
                    }
                    break;

                case 2:
                    System.out.println("\n--- LOGIN ---");
                    System.out.print("Email: ");
                    String em = k.next();

                    System.out.print("Password: ");
                    String pw = k.next();

                    User found = null;

                    for (User us : auth.getUsers()) {
                        if (us.getEmail().equalsIgnoreCase(em)) {
                            found = us;
                            break;
                        }
                    }

                    if (found == null) {
                        System.out.println(":< User not found");
                        break;
                    }

                    if (!found.getPassword().equals(pw)) {
                        System.out.println(":< Incorrect password");
                        break;
                    }

                    System.out.println(";) Login SUCCESS - Welcome, " + found.getName());
                    logged = found;

                    if (logged instanceof Customer) {
                        customerMenu((Customer) logged, manager, k, auth.getUsers());
                    } else if (logged instanceof Employee) {
                        employeeMenu((Employee) logged, manager, k, auth.getUsers());
                    }
                    break;

                case 3:
                    System.out.println("\n========== ALL EVENTS ==========");
                    displayAllEvents(manager.getEvents());
                    break;

                case 4:
                    System.out.println("Launching Events GUI...");
                    EventGUI.setEvents(manager.getEvents());
                    Application.launch(EventGUI.class);
                    break;
            }
        }

        System.out.println("\n========== PROGRAM CLOSED ==========");
        k.close();
        }
        catch(Exception e){
            if (e instanceof IllegalArgumentException)
               System.err.println(":< Error Illegal Argument");
            else if (e instanceof FileNotFoundException)
               System.err.println(":< Error File Not Found"); 
            else if (e instanceof InputMismatchException)
               System.err.println(":< Error Wrong Input");
            else 
                System.err.println(":< Error Of Type " + e.getMessage() + " Found");
       }
    }

    public static void displayAllEvents(ArrayList<Event> events) {
        if (events.isEmpty()) {
            System.out.println("No events available");
            return;
        }

        for (Event e : events) {
            System.out.println("\n" + "----------------------------------------");
            System.out.println("ID: " + e.getEventId() + " | " + e.eventType());
            System.out.println("Title: " + e.getTitle());
            System.out.println(e.getDateTime());
            System.out.println("Location: " + e.getLocation());

            if (e instanceof MusicEvent) {
                MusicEvent me = (MusicEvent) e;
                System.out.println("Artist: " + me.getArtistName() + " | Genre: " + me.getGenre());
            } else if (e instanceof SportEvent) {
                SportEvent se = (SportEvent) e;
                System.out.println("Match: " + se.getTeam1() + " vs " + se.getTeam2());
            } else if (e instanceof Conference) {
                Conference c = (Conference) e;
                System.out.println("Topic: " + c.getTopic() + " | Speaker: " + c.getSpeaker());
            }

            int availableCount = 0;
            for (Ticket t : e.getTickets()) {
                if (t.getStatus().equalsIgnoreCase("Available")) {
                    availableCount++;
                }
            }
            System.out.println("Available Tickets: " + availableCount + "/" + e.getTickets().length);
        }
        System.out.println("----------------------------------------");
    }

    public static void customerMenu(Customer customer, BookingManager manager, Scanner k, ArrayList<User> users) {

        int c = 0;
        Reservation last = null;

        while (c != 7) {

            System.out.println("\n========== CUSTOMER MENU ==========");
            System.out.println("1- Browse Events");
            System.out.println("2- Book Ticket");
            System.out.println("3- Get Event Suggestions");
            System.out.println("4- View My Reservations");
            System.out.println("5- Gift Ticket");
            System.out.println("6- Cancel Reservation");
            System.out.println("7- Logout");
            System.out.print("Choose: ");
            c = k.nextInt();

            switch (c) {

                case 1:
                    displayAllEvents(manager.getEvents());
                    break;

                case 2:
                    System.out.println("\n--- BOOK TICKET ---");
                    displayAllEvents(manager.getEvents());
                    System.out.print("Enter Event ID to book: ");
                    int eventId = k.nextInt();

                    Event selectedEvent = manager.viewEventDetails(eventId);
                    if (selectedEvent == null) {
                        System.out.println(":< Event not found");
                        break;
                    }

                    if (selectedEvent instanceof MusicEvent) {
                        MusicEvent me = (MusicEvent) selectedEvent;
                        me.playMusicPreview();
                    }

                    System.out.println("\n--- AVAILABLE TICKET TYPES ---");
                    Map<String, Integer> ticketAvailability = new HashMap<>();
                    Map<String, Double> ticketPrices = new HashMap<>();
                    
                    for (Ticket t : selectedEvent.getTickets()) {
                        if (t.getStatus().equalsIgnoreCase("Available")) {
                            String type = t.getType();
                            ticketAvailability.put(type, ticketAvailability.getOrDefault(type, 0) + 1);
                            ticketPrices.put(type, t.getPrice());
                        }
                    }

                    if (ticketAvailability.isEmpty()) {
                        System.out.println(":< No tickets available for this event");
                        break;
                    }

                    int option = 1;
                    for (Map.Entry<String, Integer> entry : ticketAvailability.entrySet()) {
                        System.out.println(option + "- " + entry.getKey() + " | Price: " + ticketPrices.get(entry.getKey()) + " SAR | Available: " + entry.getValue());
                        option++;
                    }

                    System.out.print("Choose ticket type: ");
                    int typeChoice = k.nextInt();

                    String selectedType = null;
                    int counter = 1;
                    for (Map.Entry<String, Integer> entry : ticketAvailability.entrySet()) {
                        if (counter == typeChoice) {
                            selectedType = entry.getKey();
                            break;
                        }
                        counter++;
                    }

                    if (selectedType == null) {
                        System.out.println(":< Invalid ticket type selection");
                        break;
                    }

                    try {
                        System.out.print("How many " + selectedType + " tickets (max 5): ");
                        int count = k.nextInt();

                        if (count > ticketAvailability.get(selectedType)) {
                            System.out.println(":< Only " + ticketAvailability.get(selectedType) + " " + selectedType + " tickets available");
                            break;
                        }

                        last = manager.bookTicket(customer, selectedEvent, count, selectedType);
                        System.out.println(";) Booking SUCCESS");
                        last.printDetails();

                    } catch (Exception e) {
                        System.out.println(":< " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("\n--- SUGGESTED EVENTS ---");
                    System.out.println("Your interests: " + String.join(", ", customer.getInterests()));
                    Event[] suggestions = manager.suggestEvents(customer);
                    if (suggestions.length == 0) {
                        System.out.println(":< No suggestions available");
                    } else {
                        for (Event e : suggestions) {
                            System.out.println("→ " + e.getTitle() + " (" + e.eventType() + ")");
                        }
                    }
                    break;

                case 4:
                    System.out.println("\n--- YOUR RESERVATIONS ---");
                    ArrayList<Reservation> reservations = customer.getReservations();
                    if (reservations.isEmpty()) {
                        System.out.println("No reservations yet");
                    } else {
                        for (Reservation r : reservations) {
                            System.out.println("\n" + "-----------------------------------------");
                            System.out.println("Event: " + r.getEvent().getTitle());
                            r.printDetails();
                            System.out.print("Tickets: ");
                            for (Ticket t : r.getTickets()) {
                                System.out.print("[" + t.getType() + ": " + t.getPrice() + "SAR] ");
                            }
                            System.out.println();
                        }
                    }
                    break;

                case 5:
                    if (last == null || last.getTickets().length == 0) {
                        System.out.println(":< No tickets to gift");
                        break;
                    }

                    System.out.println("\n--- GIFT TICKET ---");
                    System.out.print("Enter receiver email: ");
                    String email = k.next();

                    User receiver = null;
                    for (User u : users) {
                        if (u.getEmail().equalsIgnoreCase(email)) {
                            receiver = u;
                            break;
                        }
                    }

                    if (receiver == null || !(receiver instanceof Customer)) {
                        System.out.println(":< Receiver not found or not a customer");
                        break;
                    }

                    try {
                        Ticket t = last.getTickets()[0];
                        manager.giftTicket(t, receiver);
                        System.out.println(";) Gift SUCCESS to " + receiver.getName());

                    } catch (Exception e) {
                        System.out.println(":< " + e.getMessage());
                    }
                    break;

                case 6:
                    if (last == null) {
                        System.out.println(":< No reservation to cancel");
                        break;
                    }

                    try {
                        manager.cancelReservation(last);
                        System.out.println(";) Reservation CANCELED");
                        last = null;

                    } catch (Exception e) {
                        System.out.println(":< " + e.getMessage());
                    }
                    break;
            }
        }
    }

    public static void employeeMenu(Employee employee, BookingManager manager, Scanner k, ArrayList<User> users) {

        int c = 0;

        while (c != 6) {

            System.out.println("\n========== EMPLOYEE MENU (" + employee.getRole().toUpperCase() + ") ==========");
            System.out.println("1- View All Events");
            System.out.println("2- View All Reservations");
            System.out.println("3- View All Users");
            System.out.println("4- System Statistics");
            System.out.println("5- View Event Details");
            System.out.println("6- Logout");
            System.out.print("Choose: ");
            c = k.nextInt();

            switch (c) {

                case 1:
                    System.out.println("\n========== ALL EVENTS ==========");
                    displayAllEvents(manager.getEvents());
                    break;

                case 2:
                    System.out.println("\n========== ALL RESERVATIONS ==========");
                    ArrayList<Reservation> allReservations = manager.getReservation();
                    if (allReservations.isEmpty()) {
                        System.out.println("No reservations in system");
                    } else {
                        for (Reservation r : allReservations) {
                            System.out.println("\n" + "----------------------------------------");
                            System.out.println("Reservation ID: " + r.getReservationId());
                            System.out.println("Customer: " + r.getUser().getName() + " (" + r.getUser().getEmail() + ")");
                            System.out.println("Event: " + r.getEvent().getTitle());
                            System.out.println("Status: " + r.getStatus());
                            r.printDetails();
                        }
                    }
                    break;

                case 3:
                    System.out.println("\n========== ALL USERS ==========");
                    int customerCount = 0;
                    int employeeCount = 0;
                    for (User u : users) {
                        if (u instanceof Customer) {
                            Customer cust = (Customer) u;
                            System.out.println("\nID: " + u.getUserId() + " | " + u.getName() + " (CUSTOMER)");
                            System.out.println("Email: " + u.getEmail());
                            System.out.println("Interests: " + String.join(", ", cust.getInterests()));
                            System.out.println("Reservations: " + u.getReservations().size());
                            customerCount++;
                        } else if (u instanceof Employee) {
                            Employee emp = (Employee) u;
                            System.out.println("\nID: " + u.getUserId() + " | " + u.getName() + " (EMPLOYEE)");
                            System.out.println("Email: " + u.getEmail());
                            System.out.println("Role: " + emp.getRole());
                            employeeCount++;
                        }
                    }
                    System.out.println("\n" + "----------------------------------------");
                    System.out.println("Total Customers: " + customerCount);
                    System.out.println("Total Employees: " + employeeCount);
                    break;

                case 4:
                    System.out.println("\n========== SYSTEM STATISTICS ==========");
                    int totalReservations = manager.getReservation().size();
                    int totalEvents = manager.getEvents().size();
                    int totalTickets = 0;
                    int bookedTickets = 0;
                    double totalRevenue = 0;

                    for (Event e : manager.getEvents()) {
                        totalTickets += e.getTickets().length;
                        for (Ticket t : e.getTickets()) {
                            if (t.getStatus().equalsIgnoreCase("Booked")) {
                                bookedTickets++;
                            }
                        }
                    }

                    for (Reservation r : manager.getReservation()) {
                        if (r.getStatus().equalsIgnoreCase("confirmed")) {
                            totalRevenue += r.calcTotalPrice();
                        }
                    }

                    System.out.println("Total Events: " + totalEvents);
                    System.out.println("Total Tickets: " + totalTickets);
                    System.out.println("Booked Tickets: " + bookedTickets + "/" + totalTickets);
                    System.out.println("Occupancy Rate: " + String.format("%.1f", ((double)bookedTickets/totalTickets)*100) + "%");
                    System.out.println("Total Reservations: " + totalReservations);
                    System.out.println("Total Revenue: " + totalRevenue + " SAR");
                    break;

                case 5:
                    System.out.println("\n--- EVENT DETAILS ---");
                    displayAllEvents(manager.getEvents());
                    System.out.print("Enter Event ID: ");
                    int eventId = k.nextInt();
                    Event event = manager.viewEventDetails(eventId);
                    if (event == null) {
                        System.out.println(":< Event not found");
                    } else {
                        System.out.println("\n" + "----------------------------------------");
                        System.out.println("Event: " + event.getTitle() + " (" + event.eventType() + ")");
                        System.out.println(event.getDateTime());
                        System.out.println("Location: " + event.getLocation());
                        
                        if (event instanceof MusicEvent) {
                            MusicEvent me = (MusicEvent) event;
                            System.out.println("Artist: " + me.getArtistName() + " | Genre: " + me.getGenre());
                        } else if (event instanceof SportEvent) {
                            SportEvent se = (SportEvent) event;
                            System.out.println("Match: " + se.getTeam1() + " vs " + se.getTeam2());
                        } else if (event instanceof Conference) {
                            Conference conf = (Conference) event;
                            System.out.println("Topic: " + conf.getTopic() + " | Speaker: " + conf.getSpeaker());
                        }

                        System.out.println("\nTickets:");
                        for (Ticket t : event.getTickets()) {
                            t.printDetails();
                        }
                    }
                    break;
            }
        }
    }
}


