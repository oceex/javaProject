import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Customer extends User {

    private List<String> interests = new ArrayList<>();

    public Customer() {
        super();
    }

    public Customer(int userId, String name, String email, ArrayList<String> interests) {
        this(userId, name, email, "", interests);
    }

    public Customer(int userId, String name, String email, String password, ArrayList<String> interests) {
        super(userId, name, email, password);
        if (interests != null) {
            this.interests = interests;
        }
    }

    public String[] getInterests() {
        return interests.toArray(new String[0]);
    }

    public void setInterests(String[] interests) {
        this.interests = interests != null ? new ArrayList<>(Arrays.asList(interests)) : new ArrayList<>();
    }

    public void addInterest(String interest) {
        if (interest != null && !interest.isBlank() && !interests.contains(interest)) {
            interests.add(interest);
        }
    }

    public void removeInterest(String interest) {
        interests.remove(interest);
    }


    public Event[] suggestEvents(List<Event> allEvents) {
        if (allEvents == null || interests.isEmpty())
            return new Event[0];

        List<Event> suggestions = new ArrayList<>();
        for (Event e : allEvents) {
            if (e == null)
                continue;
            String title = e.getTitle() != null ? e.getTitle().toLowerCase() : "";
            for (String interest : interests) {
                if (interest != null && !interest.isBlank() && title.contains(interest.toLowerCase())) {
                    suggestions.add(e);
                    break;
                }
            }
        }
        return suggestions.toArray(new Event[0]);
    }

    @Override
    public String toString() {
        return "Customer{" + super.toString() + ", interests=" + interests + "}";
    }

    public String serialize() {
        String sb = "Customer|";
        sb += getUserId() + "|" + getName() + "|" + getEmail() + "|" + getPassword() + "|";
        if (interests != null && interests.size() > 0) {
            for (int i = 0; i < interests.size(); i++) {
                sb+=interests.get(i);
                if (i < interests.size() - 1)
                    sb+=";";
            }
        }
        return sb;
    }

    @Override
    public String userType() {
        return "Customer";
    }
}
