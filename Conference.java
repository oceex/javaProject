public class Conference extends Event{
    
    private String topic;
    private String speaker;

    public Conference() {
        
    }

    public Conference(String title, EventDateTime dateTime, String location, Ticket[] tickets,String topic, String speaker) {
        super(title, dateTime, location, tickets);
        this.topic = topic;
        this.speaker = speaker;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }
    
    public void showAgenda(){
        System.out.println("Topic: "+topic+", Speaker: "+speaker);
    }
    
    @Override
    public String eventType(){
        return "Conference";
    }
    
    @Override
    public String toString(){
        return String.format("%s, Topic: %s, Speaker: %s", super.toString(),topic,speaker);
    }
}