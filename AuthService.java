import java.util.*;
import java.io.*;
public class AuthService extends FileManager{
    private ArrayList<User> users = new ArrayList<>();

    public AuthService() {
        
    }

    public AuthService(ArrayList<User> users) {
        this.users = users;
    }

    public int signUp(User user) {
        for (User u : users) {
            if (u.getUserId() == user.getUserId())
                {
                    System.err.println("==== :< ID already exists ====");
                    return (-1);
                }
            else if (u.getEmail().equalsIgnoreCase(user.getEmail()))
             {
                System.err.println("==== :< Email already exists ====");
                return(-1);
            }

        }
        users.add(user);
        return (0);
    }


    public User login(String email, String pass) {
        if (users == null)
            return null;

        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email) && u.validatePassword(pass)) {
                return u;
            }
        }
        return null;
    }


    public boolean validate(String email, String pass) {
        return login(email, pass) != null;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void saveUsersToFile() {
        try (Formatter formatter = new Formatter(new File(FileManager.FILE_NAME))) {
            for (User u : users) {
                String line = u.getUserId() + "," + u.getName() + "," + u.getEmail() + "," + u.getPassword() + ",";
                if (u instanceof Customer) {
                    Customer c = (Customer) u;
                    line += "Customer," + String.join(";", c.getInterests());
                } else if (u instanceof Employee) {
                    Employee e = (Employee) u;
                    line += "Employee," + e.getRole();
                }
                formatter.format("%s%n", line);
            }
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }


    public void loadUsersFromFile() {
        try (Scanner scanner = new Scanner(new File(FileManager.FILE_NAME))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String email = parts[2];
                    String password = parts[3];
                    String type = parts[4];

                    if (type.equals("Customer")) {
                        ArrayList<String> interests = new ArrayList<>();
                        if (parts.length > 5) {
                            String[] ints = parts[5].split(";");
                            interests.addAll(Arrays.asList(ints));
                        }
                        users.add(new Customer(id, name, email, password, interests));
                    } else if (type.equals("Employee")) {
                        String role = parts.length > 5 ? parts[5] : "";
                        users.add(new Employee(id, name, email, password, role));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
    }

}
