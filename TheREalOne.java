import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

public class TheREalOne extends Application {

    private AuthService auth;
    private BookingManager manager;
    private ArrayList<User> users;
    private User loggedUser;

    private BorderPane rootPane;
    private Label statusLabel;
    private ListView<Event> eventListView;
    private TextArea detailArea;
    private Button bookButton;
    private Button reservationsButton;
    private Button logoutButton;
    private Button loginButton;
    private Button signUpButton;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        initData();

        rootPane = new BorderPane();
        rootPane.setPadding(new Insets(12));
        rootPane.setLeft(createMenuPane());
        rootPane.setCenter(createWelcomePane());
        rootPane.setBottom(createStatusBar());

        Scene scene = new Scene(rootPane, 1000, 650);
        stage.setTitle("TheREalOne Event Booking");
        stage.setScene(scene);
        stage.show();

        updateStatus("Ready. Please choose an action from the menu.");
    }

    private void initData() {
        users = new ArrayList<>();
        manager = new BookingManager();

        users.add(new Employee(100, "Admin", "admin@company.com", "admin123", "manager"));
        users.add(new Employee(101, "Support", "support@company.com", "supporter"));

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

        EventDateTime dt2 = new EventDateTime("15-11-2026", "7PM");
        Ticket[] tickets2 = new Ticket[]{
                new Ticket(6, "VIP", 600, "Available", null),
                new Ticket(7, "Regular", 250, "Available", null),
                new Ticket(8, "Economy", 120, "Available", null)
        };
        Event event2 = new MusicEvent("Jazz Night", dt2, "Jeddah", tickets2, "The Jazz Collective", "Jazz");
        manager.getEvents().add(event2);

        EventDateTime dt3 = new EventDateTime("20-10-2026", "6PM");
        Ticket[] tickets3 = new Ticket[]{
                new Ticket(9, "Season", 1500, "Available", null),
                new Ticket(10, "Regular", 300, "Available", null),
                new Ticket(11, "Regular", 300, "Available", null)
        };
        Event event3 = new SportEvent("Football Championship", dt3, "Dammam", tickets3, "Al-Hilal", "Al-Nassr");
        manager.getEvents().add(event3);

        EventDateTime dt4 = new EventDateTime("05-01-2027", "5PM");
        Ticket[] tickets4 = new Ticket[]{
                new Ticket(12, "VIP", 800, "Available", null),
                new Ticket(13, "Regular", 250, "Available", null)
        };
        Event event4 = new SportEvent("Tennis Tournament", dt4, "Diriyah", tickets4, "Djokovic", "Alcaraz");
        manager.getEvents().add(event4);

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

        auth = new AuthService(users);
    }

    private Button bookTicketButton;
    private Button suggestionsButton;
    private Button giftTicketButton;
    private Button cancelReservationButton;
    private Button viewAllEventsButton;
    private Button viewAllReservationsButton;
    private Button viewUsersButton;
    private Button statisticsButton;
    private Button eventDetailsButton;

    private VBox createMenuPane() {
        VBox menu = new VBox(10);
        menu.setPadding(new Insets(10));
        menu.setPrefWidth(240);

        Label title = new Label("Main Menu");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        signUpButton = new Button("Sign Up");
        loginButton = new Button("Login");
        Button browseButton = new Button("Browse Events");
        bookTicketButton = new Button("Book Ticket");
        suggestionsButton = new Button("Get Event Suggestions");
        reservationsButton = new Button("View My Reservations");
        giftTicketButton = new Button("Gift Ticket");
        cancelReservationButton = new Button("Cancel Reservation");
        viewAllEventsButton = new Button("View All Events");
        viewAllReservationsButton = new Button("View All Reservations");
        viewUsersButton = new Button("View All Users");
        statisticsButton = new Button("System Statistics");
        eventDetailsButton = new Button("View Event Details");
        logoutButton = new Button("Logout");
        Button exitButton = new Button("Exit");

        Button[] buttons = new Button[]{
                signUpButton,
                loginButton,
                browseButton,
                bookTicketButton,
                suggestionsButton,
                reservationsButton,
                giftTicketButton,
                cancelReservationButton,
                viewAllEventsButton,
                viewAllReservationsButton,
                viewUsersButton,
                statisticsButton,
                eventDetailsButton,
                logoutButton,
                exitButton
        };

        for (Button button : buttons) {
            button.setMaxWidth(Double.MAX_VALUE);
        }

        signUpButton.setOnAction(e -> showSignUpPane());
        loginButton.setOnAction(e -> showLoginPane());
        browseButton.setOnAction(e -> showEventBrowserPane());
        bookTicketButton.setOnAction(e -> showBookTicketPane());
        suggestionsButton.setOnAction(e -> showSuggestionPane());
        reservationsButton.setOnAction(e -> showReservationsPane());
        giftTicketButton.setOnAction(e -> showGiftTicketPane());
        cancelReservationButton.setOnAction(e -> showCancelReservationPane());
        viewAllEventsButton.setOnAction(e -> showAllEventsPane());
        viewAllReservationsButton.setOnAction(e -> showAllReservationsPane());
        viewUsersButton.setOnAction(e -> showAllUsersPane());
        statisticsButton.setOnAction(e -> showSystemStatisticsPane());
        eventDetailsButton.setOnAction(e -> showEmployeeEventDetailsPane());
        logoutButton.setOnAction(e -> doLogout());
        exitButton.setOnAction(e -> rootPane.getScene().getWindow().hide());

        menu.getChildren().addAll(title, signUpButton, loginButton, browseButton, bookTicketButton, suggestionsButton, reservationsButton, giftTicketButton, cancelReservationButton, viewAllEventsButton, viewAllReservationsButton, viewUsersButton, statisticsButton, eventDetailsButton, logoutButton, exitButton);
        updateMenuState();
        return menu;
    }

    private HBox createStatusBar() {
        HBox statusBox = new HBox();
        statusBox.setPadding(new Insets(8));
        statusBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #cccccc;");
        statusLabel = new Label();
        statusLabel.setWrapText(true);
        statusBox.getChildren().add(statusLabel);
        return statusBox;
    }

    private void updateStatus(String message) {
        statusLabel.setText(message);
    }

    private Node createWelcomePane() {
        Label welcome = new Label("Welcome to TheREalOne Event Booking UI.");
        welcome.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        TextArea intro = new TextArea("Use the menu to sign up, log in, browse events, or view your reservations.\n\n" +
                "This application uses the existing model classes and JavaFX GUI components.");
        intro.setEditable(false);
        intro.setWrapText(true);
        intro.setPrefHeight(420);

        VBox content = new VBox(12, welcome, intro);
        content.setPadding(new Insets(10));
        return content;
    }

    private void updateMenuState() {
        boolean authenticated = loggedUser != null;
        boolean isCustomer = loggedUser instanceof Customer;
        boolean isEmployee = loggedUser instanceof Employee;

        signUpButton.setDisable(authenticated);
        loginButton.setDisable(authenticated);
        bookTicketButton.setDisable(!isCustomer);
        suggestionsButton.setDisable(!isCustomer);
        reservationsButton.setDisable(!isCustomer);
        giftTicketButton.setDisable(!isCustomer);
        cancelReservationButton.setDisable(!isCustomer);
        viewAllEventsButton.setDisable(!isEmployee);
        viewAllReservationsButton.setDisable(!isEmployee);
        viewUsersButton.setDisable(!isEmployee);
        statisticsButton.setDisable(!isEmployee);
        eventDetailsButton.setDisable(!isEmployee);
        logoutButton.setDisable(!authenticated);
    }

    private void showSignUpPane() {
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(12));

        Label heading = new Label("Create a new account");
        heading.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label idLabel = new Label("User ID:");
        TextField idField = new TextField();
        idField.setPromptText("10 digits, starts with 11");

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Label typeLabel = new Label("Account type:");
        ChoiceBox<String> typeChoice = new ChoiceBox<>(FXCollections.observableArrayList("Customer", "Employee"));
        typeChoice.getSelectionModel().selectFirst();

        Label interestsLabel = new Label("Interests (comma separated):");
        TextField interestsField = new TextField();

        Label roleLabel = new Label("Role:");
        TextField roleField = new TextField();
        roleField.setPromptText("manager or supporter");
        roleField.setDisable(true);

        Button submit = new Button("Create Account");

        typeChoice.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            boolean customer = "Customer".equals(newValue);
            interestsField.setDisable(!customer);
            interestsLabel.setDisable(!customer);
            roleField.setDisable(customer);
            roleLabel.setDisable(customer);
        });

        form.add(heading, 0, 0, 2, 1);
        form.add(typeLabel, 0, 1);
        form.add(typeChoice, 1, 1);
        form.add(idLabel, 0, 2);
        form.add(idField, 1, 2);
        form.add(nameLabel, 0, 3);
        form.add(nameField, 1, 3);
        form.add(emailLabel, 0, 4);
        form.add(emailField, 1, 4);
        form.add(passwordLabel, 0, 5);
        form.add(passwordField, 1, 5);
        form.add(interestsLabel, 0, 6);
        form.add(interestsField, 1, 6);
        form.add(roleLabel, 0, 7);
        form.add(roleField, 1, 7);
        form.add(submit, 0, 8, 2, 1);

        GridPane.setHgrow(idField, Priority.ALWAYS);
        GridPane.setHgrow(nameField, Priority.ALWAYS);
        GridPane.setHgrow(emailField, Priority.ALWAYS);
        GridPane.setHgrow(passwordField, Priority.ALWAYS);
        GridPane.setHgrow(interestsField, Priority.ALWAYS);
        GridPane.setHgrow(roleField, Priority.ALWAYS);

        submit.setOnAction(event -> {
            String type = typeChoice.getValue();
            String idText = idField.getText().trim();
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();
            String interestsText = interestsField.getText().trim();
            String role = roleField.getText().trim();

            if (!validateSignUpInputs(type, idText, name, email, password, interestsText, role)) {
                return;
            }

            int id;
            try {
                long parsed = Long.parseLong(idText);
                if (parsed < 1100000000L || parsed >= 1200000000L) {
                    showAlert(AlertType.WARNING, "Invalid ID", "ID must be 10 digits and start with 11.");
                    return;
                }
                id = (int) parsed;
            } catch (NumberFormatException ex) {
                showAlert(AlertType.WARNING, "Invalid ID", "Please enter a valid numeric ID.");
                return;
            }

            int result;
            if ("Customer".equals(type)) {
                ArrayList<String> interests = new ArrayList<>();
                if (!interestsText.isBlank()) {
                    interests.addAll(Arrays.asList(interestsText.split("\\s*,\\s*")));
                }
                result = auth.signUp(new Customer(id, name, email, password, interests));
            } else {
                if (role.isBlank()) {
                    showAlert(AlertType.WARNING, "Invalid Role", "Role is required for employees.");
                    return;
                }
                result = auth.signUp(new Employee(id, name, email, password, role));
            }

            if (result == 0) {
                updateStatus("Account created successfully. You may now log in.");
                rootPane.setCenter(new VBox(new Label("Account created successfully.")));
            } else {
                showAlert(AlertType.ERROR, "Sign Up Failed", "That user ID or email is already taken.");
            }
        });

        rootPane.setCenter(form);
        updateStatus("Fill in the fields to create a new account.");
    }

    private boolean validateSignUpInputs(String type, String idText, String name, String email, String password, String interestsText, String role) {
        if (idText.isBlank() || name.isBlank() || email.isBlank() || password.isBlank()) {
            showAlert(AlertType.WARNING, "Missing Fields", "Please complete all required fields.");
            return false;
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            showAlert(AlertType.WARNING, "Invalid Email", "Please enter a valid email address.");
            return false;
        }
        if (password.length() < 6) {
            showAlert(AlertType.WARNING, "Weak Password", "Password must be at least 6 characters long.");
            return false;
        }
        if (!password.matches(".*[0-9].*")) {
            showAlert(AlertType.WARNING, "Weak Password", "Password must contain at least one digit.");
            return false;
        }
        if (!password.matches(".*[A-Za-z].*")) {
            showAlert(AlertType.WARNING, "Weak Password", "Password must contain at least one letter.");
            return false;
        }
        if ("Employee".equals(type) && role.isBlank()) {
            showAlert(AlertType.WARNING, "Invalid Role", "Employee role cannot be empty.");
            return false;
        }
        return true;
    }

    private void showLoginPane() {
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(12));

        Label heading = new Label("Login to your account");
        heading.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button submit = new Button("Login");

        form.add(heading, 0, 0, 2, 1);
        form.add(emailLabel, 0, 1);
        form.add(emailField, 1, 1);
        form.add(passwordLabel, 0, 2);
        form.add(passwordField, 1, 2);
        form.add(submit, 0, 3, 2, 1);

        GridPane.setHgrow(emailField, Priority.ALWAYS);
        GridPane.setHgrow(passwordField, Priority.ALWAYS);

        submit.setOnAction(event -> {
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();
            if (email.isBlank() || password.isBlank()) {
                showAlert(AlertType.WARNING, "Missing Credentials", "Please enter both email and password.");
                return;
            }
            User user = auth.login(email, password);
            if (user == null) {
                showAlert(AlertType.ERROR, "Login Failed", "Incorrect email or password.");
                updateStatus("Login failed. Please check your credentials.");
            } else {
                loggedUser = user;
                updateMenuState();
                updateStatus("Welcome back, " + user.getName() + ".");
                showEventBrowserPane();
            }
        });

        rootPane.setCenter(form);
        updateStatus("Enter your email and password to log in.");
    }

    private void doLogout() {
        if (loggedUser != null) {
            updateStatus("Goodbye, " + loggedUser.getName() + ". You have been logged out.");
            loggedUser = null;
            updateMenuState();
            rootPane.setCenter(createWelcomePane());
        } else {
            updateStatus("No active session.");
        }
    }

    private void showEventBrowserPane() {
        VBox browserPane = new VBox(12);
        browserPane.setPadding(new Insets(12));

        Label title = new Label("All Events");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        eventListView = new ListView<>();
        eventListView.setItems(FXCollections.observableArrayList(manager.getEvents()));
        eventListView.setCellFactory(list -> new ListCell<Event>() {
            @Override
            protected void updateItem(Event item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    String available = item.isAvailable() ? "(Available)" : "(Sold Out)";
                    setText(String.format("%s - %s %s %s", item.getEventId(), item.getTitle(), item.eventType(), available));
                }
            }
        });
        eventListView.setPrefHeight(280);

        detailArea = new TextArea();
        detailArea.setEditable(false);
        detailArea.setWrapText(true);
        detailArea.setPrefHeight(220);

        bookButton = new Button("Book Selected Event");
        bookButton.setDisable(true);
        Button refreshButton = new Button("Refresh");

        HBox actions = new HBox(10, bookButton, refreshButton);
        actions.setAlignment(Pos.CENTER_LEFT);

        eventListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, selectedEvent) -> {
            updateEventDetails(selectedEvent);
            updateBookButtonState(selectedEvent);
        });

        refreshButton.setOnAction(event -> {
            refreshEventBrowser();
            updateStatus("Event list refreshed.");
        });

        bookButton.setOnAction(event -> bookSelectedEvent());

        browserPane.getChildren().addAll(title, eventListView, detailArea, actions);
        VBox.setVgrow(eventListView, Priority.ALWAYS);
        VBox.setVgrow(detailArea, Priority.ALWAYS);

        rootPane.setCenter(browserPane);
        updateStatus("Browse events and select one to see details.");
    }

    private void updateBookButtonState(Event selectedEvent) {
        boolean canBook = selectedEvent != null && selectedEvent.isAvailable() && loggedUser instanceof Customer;
        bookButton.setDisable(!canBook);
    }

    private void updateEventDetails(Event event) {
        if (event == null) {
            detailArea.setText("Select an event to see details.");
            return;
        }

        StringBuilder text = new StringBuilder();
        text.append("Event ID: ").append(event.getEventId()).append("\n");
        text.append("Title: ").append(event.getTitle()).append("\n");
        text.append("Type: ").append(event.eventType()).append("\n");
        text.append("Date/Time: ").append(event.getDateTime()).append("\n");
        text.append("Location: ").append(event.getLocation()).append("\n\n");

        if (event instanceof MusicEvent) {
            MusicEvent me = (MusicEvent) event;
            text.append("Artist: ").append(me.getArtistName()).append("\n");
            text.append("Genre: ").append(me.getGenre()).append("\n\n");
        } else if (event instanceof SportEvent) {
            SportEvent se = (SportEvent) event;
            text.append("Match: ").append(se.getTeam1()).append(" vs ").append(se.getTeam2()).append("\n\n");
        } else if (event instanceof Conference) {
            Conference conf = (Conference) event;
            text.append("Topic: ").append(conf.getTopic()).append("\n");
            text.append("Speaker: ").append(conf.getSpeaker()).append("\n\n");
        }

        text.append("Tickets:\n");
        int availableCount = 0;
        for (Ticket ticket : event.getTickets()) {
            text.append(String.format("  • #%d %s - %.2f ⃁ - %s\n", ticket.getTicketId(), ticket.getType(), ticket.getPrice(), ticket.getStatus()));
            if ("Available".equalsIgnoreCase(ticket.getStatus())) {
                availableCount++;
            }
        }
        text.append("\nAvailable Tickets: ").append(availableCount).append(" / ").append(event.getTickets().length).append("\n");
        detailArea.setText(text.toString());
    }

    private List<String> getAvailableTicketTypes(Event event) {
        Map<String, Integer> ticketAvailability = new HashMap<>();
        for (Ticket ticket : event.getTickets()) {
            if ("Available".equalsIgnoreCase(ticket.getStatus())) {
                ticketAvailability.put(ticket.getType(), ticketAvailability.getOrDefault(ticket.getType(), 0) + 1);
            }
        }
        return new ArrayList<>(ticketAvailability.keySet());
    }

    private void bookSelectedEvent() {
        Event event = eventListView.getSelectionModel().getSelectedItem();
        if (event == null) {
            updateStatus("No event selected.");
            return;
        }
        if (!(loggedUser instanceof Customer)) {
            showAlert(AlertType.ERROR, "Booking Required", "You must log in as a customer to book tickets.");
            return;
        }

        List<String> ticketTypes = getAvailableTicketTypes(event);
        if (ticketTypes.isEmpty()) {
            showAlert(AlertType.INFORMATION, "Sold Out", "No available tickets for this event.");
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Book Tickets");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(12));

        ChoiceBox<String> ticketTypeChoice = new ChoiceBox<>(FXCollections.observableArrayList(ticketTypes));
        ticketTypeChoice.getSelectionModel().selectFirst();
        TextField countField = new TextField("1");
        countField.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("[0-9]*")) {
                return change;
            }
            return null;
        }));

        grid.add(new Label("Ticket Type:"), 0, 0);
        grid.add(ticketTypeChoice, 1, 0);
        grid.add(new Label("Quantity:"), 0, 1);
        grid.add(countField, 1, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String type = ticketTypeChoice.getValue();
            int quantity;
            try {
                quantity = Integer.parseInt(countField.getText().trim());
            } catch (NumberFormatException ex) {
                showAlert(AlertType.WARNING, "Invalid Quantity", "Please enter a valid ticket quantity.");
                return;
            }
            try {
                manager.bookTicket(loggedUser, event, quantity, type);
                updateStatus("Booked " + quantity + " " + type + " ticket(s) for " + event.getTitle() + ".");
                refreshEventBrowser();
            } catch (Exception ex) {
                showAlert(AlertType.ERROR, "Booking Failed", ex.getMessage());
                updateStatus("Booking failed: " + ex.getMessage());
            }
        }
    }

    private void refreshEventBrowser() {
        if (eventListView == null) {
            return;
        }
        ObservableList<Event> items = FXCollections.observableArrayList(manager.getEvents());
        eventListView.setItems(items);
        Event selected = eventListView.getSelectionModel().getSelectedItem();
        updateEventDetails(selected);
        updateBookButtonState(selected);
    }

    private void showReservationsPane() {
        if (!(loggedUser instanceof Customer)) {
            updateStatus("Only customers can view reservations.");
            return;
        }

        Customer customer = (Customer) loggedUser;
        VBox pane = new VBox(12);
        pane.setPadding(new Insets(12));

        Label heading = new Label("My Reservations");
        heading.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextArea reservationsArea = new TextArea();
        reservationsArea.setEditable(false);
        reservationsArea.setWrapText(true);

        StringBuilder builder = new StringBuilder();
        if (customer.getReservations().isEmpty()) {
            builder.append("You have no reservations yet.\n");
        } else {
            for (Reservation reservation : customer.getReservations()) {
                builder.append("Reservation ID: ").append(reservation.getReservationId()).append("\n");
                builder.append("Event: ").append(reservation.getEvent().getTitle()).append("\n");
                builder.append("Status: ").append(reservation.getStatus()).append("\n");
                builder.append("Tickets:\n");
                for (Ticket ticket : reservation.getTickets()) {
                    builder.append(String.format("  • #%d %s - %.2f ⃁ - %s\n", ticket.getTicketId(), ticket.getType(), ticket.getPrice(), ticket.getStatus()));
                }
                builder.append("Total: ").append(String.format("%.2f ⃁", reservation.calcTotalPrice())).append("\n");
                builder.append("--------------------------------\n");
            }
        }

        reservationsArea.setText(builder.toString());
        pane.getChildren().addAll(heading, reservationsArea);
        rootPane.setCenter(pane);
        updateStatus("Review your existing reservations.");
    }

    private void showBookTicketPane() {
        if (!(loggedUser instanceof Customer)) {
            showAlert(AlertType.ERROR, "Permission Required", "Only customers can book tickets.");
            return;
        }

        List<Event> events = manager.getEvents();
        if (events.isEmpty()) {
            showAlert(AlertType.INFORMATION, "No Events", "There are no events available to book.");
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Book Ticket");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(12));

        ChoiceBox<Event> eventChoice = new ChoiceBox<>(FXCollections.observableArrayList(events));
        eventChoice.setConverter(new javafx.util.StringConverter<Event>() {
            @Override
            public String toString(Event event) {
                return event == null ? "" : event.getEventId() + " - " + event.getTitle();
            }

            @Override
            public Event fromString(String string) {
                return null;
            }
        });
        eventChoice.getSelectionModel().selectFirst();

        ChoiceBox<String> ticketTypeChoice = new ChoiceBox<>();
        updateTicketTypesForSelectedEvent(eventChoice.getValue(), ticketTypeChoice);
        eventChoice.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> updateTicketTypesForSelectedEvent(newValue, ticketTypeChoice));

        TextField countField = new TextField("1");
        countField.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("[0-9]*")) {
                return change;
            }
            return null;
        }));

        grid.add(new Label("Event:"), 0, 0);
        grid.add(eventChoice, 1, 0);
        grid.add(new Label("Ticket Type:"), 0, 1);
        grid.add(ticketTypeChoice, 1, 1);
        grid.add(new Label("Quantity:"), 0, 2);
        grid.add(countField, 1, 2);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Event selectedEvent = eventChoice.getValue();
            String ticketType = ticketTypeChoice.getValue();
            if (selectedEvent == null || ticketType == null || ticketType.isBlank()) {
                showAlert(AlertType.WARNING, "Missing Selection", "Please select an event and ticket type.");
                return;
            }

            int quantity;
            try {
                quantity = Integer.parseInt(countField.getText().trim());
            } catch (NumberFormatException ex) {
                showAlert(AlertType.WARNING, "Invalid Quantity", "Please enter a valid numeric quantity.");
                return;
            }

            try {
                manager.bookTicket(loggedUser, selectedEvent, quantity, ticketType);
                updateStatus("Booked " + quantity + " " + ticketType + " ticket(s) for " + selectedEvent.getTitle() + ".");
                showEventBrowserPane();
            } catch (Exception ex) {
                showAlert(AlertType.ERROR, "Booking Failed", ex.getMessage());
                updateStatus("Booking failed: " + ex.getMessage());
            }
        }
    }

    private void updateTicketTypesForSelectedEvent(Event event, ChoiceBox<String> ticketTypeChoice) {
        ticketTypeChoice.getItems().clear();
        if (event == null) {
            return;
        }

        Map<String, Integer> ticketAvailability = new HashMap<>();
        for (Ticket ticket : event.getTickets()) {
            if (ticket != null && "Available".equalsIgnoreCase(ticket.getStatus())) {
                ticketAvailability.put(ticket.getType(), ticketAvailability.getOrDefault(ticket.getType(), 0) + 1);
            }
        }

        ticketTypeChoice.getItems().addAll(ticketAvailability.keySet());
        if (!ticketTypeChoice.getItems().isEmpty()) {
            ticketTypeChoice.getSelectionModel().selectFirst();
        }
    }

    private void showSuggestionPane() {
        if (!(loggedUser instanceof Customer)) {
            updateStatus("Only customers can view suggested events.");
            return;
        }

        Customer customer = (Customer) loggedUser;
        Event[] suggestions = manager.suggestEvents(customer);

        VBox pane = new VBox(12);
        pane.setPadding(new Insets(12));
        Label heading = new Label("Suggested Events");
        heading.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextArea suggestionsArea = new TextArea();
        suggestionsArea.setEditable(false);
        suggestionsArea.setWrapText(true);

        StringBuilder builder = new StringBuilder();
        builder.append("Your interests: ").append(String.join(", ", customer.getInterests())).append("\n\n");
        if (suggestions.length == 0) {
            builder.append("No suggestions available based on your interests.");
        } else {
            for (Event event : suggestions) {
                builder.append(event.getEventId()).append(" - ").append(event.getTitle()).append(" (" + event.eventType() + ")\n");
            }
        }

        suggestionsArea.setText(builder.toString());
        pane.getChildren().addAll(heading, suggestionsArea);
        rootPane.setCenter(pane);
        updateStatus("Showing event suggestions for " + customer.getName() + ".");
    }

    private void showGiftTicketPane() {
        if (!(loggedUser instanceof Customer)) {
            showAlert(AlertType.ERROR, "Permission Required", "Only customers can gift tickets.");
            return;
        }

        Customer customer = (Customer) loggedUser;
        List<Ticket> ownTickets = new ArrayList<>();
        for (Reservation reservation : customer.getReservations()) {
            for (Ticket ticket : reservation.getTickets()) {
                if (ticket != null && ticket.getOwner() == customer && "Booked".equalsIgnoreCase(ticket.getStatus())) {
                    ownTickets.add(ticket);
                }
            }
        }

        if (ownTickets.isEmpty()) {
            updateStatus("No tickets available to gift.");
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Gift Ticket");
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(12));

        ChoiceBox<Ticket> ticketChoice = new ChoiceBox<>(FXCollections.observableArrayList(ownTickets));
        ticketChoice.setConverter(new javafx.util.StringConverter<Ticket>() {
            @Override
            public String toString(Ticket ticket) {
                return ticket == null ? "" : "#" + ticket.getTicketId() + " " + ticket.getType() + " for " + ticket.getOwner().getName();
            }

            @Override
            public Ticket fromString(String string) {
                return null;
            }
        });
        ticketChoice.getSelectionModel().selectFirst();

        TextField receiverEmail = new TextField();
        receiverEmail.setPromptText("receiver@example.com");

        grid.add(new Label("Ticket:"), 0, 0);
        grid.add(ticketChoice, 1, 0);
        grid.add(new Label("Receiver Email:"), 0, 1);
        grid.add(receiverEmail, 1, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Ticket selectedTicket = ticketChoice.getValue();
            String email = receiverEmail.getText().trim();
            if (selectedTicket == null || email.isBlank()) {
                showAlert(AlertType.WARNING, "Invalid Input", "Please select a ticket and enter a receiver email.");
                return;
            }

            User receiver = users.stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
            if (!(receiver instanceof Customer)) {
                showAlert(AlertType.ERROR, "Receiver Not Found", "Receiver must be an existing customer.");
                return;
            }

            manager.giftTicket(selectedTicket, receiver);
            updateStatus("Gifted ticket #" + selectedTicket.getTicketId() + " to " + receiver.getName() + ".");
            showReservationsPane();
        }
    }

    private void showCancelReservationPane() {
        if (!(loggedUser instanceof Customer)) {
            showAlert(AlertType.ERROR, "Permission Required", "Only customers can cancel reservations.");
            return;
        }

        Customer customer = (Customer) loggedUser;
        List<Reservation> reservations = customer.getReservations();
        if (reservations.isEmpty()) {
            updateStatus("You have no reservations to cancel.");
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Cancel Reservation");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(12));

        ChoiceBox<Reservation> reservationChoice = new ChoiceBox<>(FXCollections.observableArrayList(reservations));
        reservationChoice.setConverter(new javafx.util.StringConverter<Reservation>() {
            @Override
            public String toString(Reservation reservation) {
                return reservation == null ? "" : "#" + reservation.getReservationId() + " - " + reservation.getEvent().getTitle();
            }

            @Override
            public Reservation fromString(String string) {
                return null;
            }
        });
        reservationChoice.getSelectionModel().selectFirst();

        grid.add(new Label("Reservation:"), 0, 0);
        grid.add(reservationChoice, 1, 0);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Reservation reservation = reservationChoice.getValue();
            if (reservation == null) {
                showAlert(AlertType.WARNING, "Invalid Selection", "Please select a reservation to cancel.");
                return;
            }
            manager.cancelReservation(reservation);
            updateStatus("Reservation #" + reservation.getReservationId() + " cancelled.");
            showReservationsPane();
        }
    }

    private void showAllEventsPane() {
        showEventBrowserPane();
        updateStatus("Employee view: browse all events.");
    }

    private void showAllReservationsPane() {
        if (!(loggedUser instanceof Employee)) {
            showAlert(AlertType.ERROR, "Permission Required", "Only employees can view all reservations.");
            return;
        }

        VBox pane = new VBox(12);
        pane.setPadding(new Insets(12));

        Label heading = new Label("All Reservations");
        heading.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextArea reservationsArea = new TextArea();
        reservationsArea.setEditable(false);
        reservationsArea.setWrapText(true);

        StringBuilder builder = new StringBuilder();
        ArrayList<Reservation> allReservations = manager.getReservation();
        if (allReservations.isEmpty()) {
            builder.append("No reservations in system.");
        } else {
            for (Reservation reservation : allReservations) {
                builder.append("Reservation ID: ").append(reservation.getReservationId()).append("\n");
                builder.append("Customer: ").append(reservation.getUser().getName()).append(" (").append(reservation.getUser().getEmail()).append(")\n");
                builder.append("Event: ").append(reservation.getEvent().getTitle()).append("\n");
                builder.append("Status: ").append(reservation.getStatus()).append("\n");
                builder.append("Tickets:\n");
                for (Ticket ticket : reservation.getTickets()) {
                    builder.append(String.format("  • #%d %s - %.2f ⃁ - %s\n", ticket.getTicketId(), ticket.getType(), ticket.getPrice(), ticket.getStatus()));
                }
                builder.append("--------------------------------\n");
            }
        }

        reservationsArea.setText(builder.toString());
        pane.getChildren().addAll(heading, reservationsArea);
        rootPane.setCenter(pane);
        updateStatus("Viewing all reservations.");
    }

    private void showAllUsersPane() {
        if (!(loggedUser instanceof Employee)) {
            showAlert(AlertType.ERROR, "Permission Required", "Only employees can view all users.");
            return;
        }

        VBox pane = new VBox(12);
        pane.setPadding(new Insets(12));

        Label heading = new Label("All Users");
        heading.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextArea usersArea = new TextArea();
        usersArea.setEditable(false);
        usersArea.setWrapText(true);

        StringBuilder builder = new StringBuilder();
        for (User user : users) {
            builder.append(user.getUserId()).append(" - ").append(user.getName()).append(" (" + user.userType() + ")\n");
            builder.append("Email: ").append(user.getEmail()).append("\n");
            if (user instanceof Customer) {
                builder.append("Interests: ").append(String.join(", ", ((Customer) user).getInterests())).append("\n");
                builder.append("Reservations: ").append(user.getReservations().size()).append("\n");
            } else if (user instanceof Employee) {
                builder.append("Role: ").append(((Employee) user).getRole()).append("\n");
            }
            builder.append("--------------------------------\n");
        }

        usersArea.setText(builder.toString());
        pane.getChildren().addAll(heading, usersArea);
        rootPane.setCenter(pane);
        updateStatus("Viewing all registered users.");
    }

    private void showSystemStatisticsPane() {
        if (!(loggedUser instanceof Employee)) {
            showAlert(AlertType.ERROR, "Permission Required", "Only employees can view system statistics.");
            return;
        }

        VBox pane = new VBox(12);
        pane.setPadding(new Insets(12));

        Label heading = new Label("System Statistics");
        heading.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextArea statsArea = new TextArea();
        statsArea.setEditable(false);
        statsArea.setWrapText(true);

        int totalReservations = manager.getReservation().size();
        int totalEvents = manager.getEvents().size();
        int totalTickets = 0;
        int bookedTickets = 0;
        double totalRevenue = 0;

        for (Event event : manager.getEvents()) {
            totalTickets += event.getTickets().length;
            for (Ticket ticket : event.getTickets()) {
                if ("Booked".equalsIgnoreCase(ticket.getStatus())) {
                    bookedTickets++;
                }
            }
        }

        for (Reservation reservation : manager.getReservation()) {
            if ("confirmed".equalsIgnoreCase(reservation.getStatus())) {
                totalRevenue += reservation.calcTotalPrice();
            }
        }

        statsArea.setText(String.format("Total Events: %d\nTotal Tickets: %d\nBooked Tickets: %d/%d\nOccupancy Rate: %.1f%%\nTotal Reservations: %d\nTotal Revenue: %.2f ⃁\n", totalEvents, totalTickets, bookedTickets, totalTickets, totalTickets == 0 ? 0.0 : ((double) bookedTickets / totalTickets) * 100, totalReservations, totalRevenue));
        pane.getChildren().addAll(heading, statsArea);
        rootPane.setCenter(pane);
        updateStatus("Viewing system statistics.");
    }

    private void showEmployeeEventDetailsPane() {
        if (!(loggedUser instanceof Employee)) {
            showAlert(AlertType.ERROR, "Permission Required", "Only employees can view event details.");
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("View Event Details");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(12));

        ChoiceBox<Event> eventChoice = new ChoiceBox<>(FXCollections.observableArrayList(manager.getEvents()));
        eventChoice.setConverter(new javafx.util.StringConverter<Event>() {
            @Override
            public String toString(Event event) {
                return event == null ? "" : event.getEventId() + " - " + event.getTitle();
            }

            @Override
            public Event fromString(String string) {
                return null;
            }
        });
        eventChoice.getSelectionModel().selectFirst();

        grid.add(new Label("Event:"), 0, 0);
        grid.add(eventChoice, 1, 0);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Event selectedEvent = eventChoice.getValue();
            if (selectedEvent == null) {
                showAlert(AlertType.WARNING, "No Event Selected", "Please select an event.");
                return;
            }

            VBox pane = new VBox(12);
            pane.setPadding(new Insets(12));
            Label heading = new Label("Event Details");
            heading.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

            TextArea detailsArea = new TextArea();
            detailsArea.setEditable(false);
            detailsArea.setWrapText(true);
            detailsArea.setText(detailTextForEvent(selectedEvent));

            pane.getChildren().addAll(heading, detailsArea);
            rootPane.setCenter(pane);
            updateStatus("Viewing details for event " + selectedEvent.getTitle() + ".");
        }
    }

    private String detailTextForEvent(Event event) {
        StringBuilder text = new StringBuilder();
        text.append("Event ID: ").append(event.getEventId()).append("\n");
        text.append("Title: ").append(event.getTitle()).append("\n");
        text.append("Type: ").append(event.eventType()).append("\n");
        text.append("Date/Time: ").append(event.getDateTime()).append("\n");
        text.append("Location: ").append(event.getLocation()).append("\n\n");

        if (event instanceof MusicEvent) {
            MusicEvent me = (MusicEvent) event;
            text.append("Artist: ").append(me.getArtistName()).append("\n");
            text.append("Genre: ").append(me.getGenre()).append("\n\n");
        } else if (event instanceof SportEvent) {
            SportEvent se = (SportEvent) event;
            text.append("Match: ").append(se.getTeam1()).append(" vs ").append(se.getTeam2()).append("\n\n");
        } else if (event instanceof Conference) {
            Conference conf = (Conference) event;
            text.append("Topic: ").append(conf.getTopic()).append("\n");
            text.append("Speaker: ").append(conf.getSpeaker()).append("\n\n");
        }

        text.append("Tickets:\n");
        for (Ticket ticket : event.getTickets()) {
            text.append(String.format("  • #%d %s - %.2f ⃁ - %s\n", ticket.getTicketId(), ticket.getType(), ticket.getPrice(), ticket.getStatus()));
        }
        return text.toString();
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
