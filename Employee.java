
public class Employee extends User {

    private String role;

    public Employee() {
        super();
        this.role = "";
    }

    public Employee(int userId, String name, String email, String password, String role) {
        super(userId, name, email, password);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Employee{" + super.toString() + ", role='" + role + "'}";
    }

    @Override
    public String userType() {
        return "Employee";
    }
}

