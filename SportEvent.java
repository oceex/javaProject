public class SportEvent extends Event{
    
    private String team1;
    private String team2;


    public SportEvent(String title, EventDateTime dateTime, String location, Ticket[] tickets,String team1,String team2) {
        super(title, dateTime,location, tickets);
        this.team1 = team1;
        this.team2 = team2;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }
    
    public void showTeams(){
        System.out.println(team1+" vs "+team2);
    }
    
    @Override
    public String eventType(){
        return "Sport Event";
    }
    
    @Override
    public String toString(){
        return String.format("%s, Team 1: %s, Team 2: %s", super.toString(),team1,team2);
    }
}