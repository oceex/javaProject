
import java.util.*;

public abstract class User {
    private int userId;
    private String name;
    private String email;
    private String password;
    private ArrayList<Reservation> reservations;

    private static int totalRegisteredUsers = 0;

    public User() {
        this.reservations = new ArrayList<>();
        totalRegisteredUsers++;
    }

    public User(int id, String name, String email, String password) {
        this.userId = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.reservations = new ArrayList<>();
        totalRegisteredUsers++;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public void setUserId(int id) {
        this.userId = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addReservation(Reservation r) {
        reservations.add(r);
    }

    public boolean validatePassword(String pw) {
        return this.password.equals(pw);
    }

    public final void showBasicInfo() {
        System.out.println("ID: " + userId + ", Name: " + name + ", Email: " + email);
    }

    public abstract String userType();

    public static int getTotalRegisteredUsers() {
        return totalRegisteredUsers;
    }

    @Override
    public String toString() {
        return "User{name='" + name + "', email='" + email + "'}";
    }

}