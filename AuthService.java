import java.util.*;
public class AuthService {
    private ArrayList<User> users;

    public AuthService() {}

    public AuthService(ArrayList<User> users) {
        this.users = users;
    }

    public User login(String email, String pass) {
        if (users == null) return null;

        for (User u : users) {
        if (u.getEmail().equals(email) && u.validatePassword(pass)) {
        return u;
        }
        }
        return null;
    }

    public void signUp(User user) {
        for (User u : users) {
            if (u.getEmail().equals(user.getEmail())) {
                throw new IllegalArgumentException("Email already exists");
            }
        }
        users.add(user);
    }

    public boolean validate(String email, String pass) {
        return login(email, pass) != null;
    }
    public ArrayList<User> getUsers() {
        return users;
    }
}
