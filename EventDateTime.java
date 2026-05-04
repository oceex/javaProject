public class EventDateTime {

    private String date;
    private String time;

    public EventDateTime() {
        
    }

    public EventDateTime(String date, String time) {
        this.date = date;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
    @Override
    public String toString(){
        return String.format("Date: %s, Time: %s", date,time);
    }
}