import java.util.*;
public class AuthService {
    private ArrayList<User> users;

    public AuthService() {users =new ArrayList<>();}

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

    public int signUp(User user) {
        for (User u : users) {
            if (u.getUserId()== user.getUserId())
                {
                    System.out.println("++++++++++  :< ID already exists  ++++++++++");
                    return (-1);
                }
            else if (u.getEmail().equals(user.getEmail()))
             {
                System.out.println("++++++++++  :< Email already exists  ++++++++++");
                return(-1);
            }

        }
        users.add(user);
        return (0);
    }

    public boolean validate(String email, String pass) {
        return login(email, pass) != null;
    }
    public ArrayList<User> getUsers() {
        return users;
    }
}
