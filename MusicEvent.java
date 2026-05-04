public class MusicEvent extends Event{
    private String artistName;
    private String genre;

    public MusicEvent() {
    }

    public MusicEvent( String title, EventDateTime dateTime, String location, Ticket[] tickets,String artistName, String genre) {
        super(title, dateTime,location, tickets);
        this.artistName = artistName;
        this.genre = genre;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public void playMusicPreview(){
        System.out.println("Playing preview for "+artistName);
    }
    
    @Override
    public String eventType(){
        return "Music Event";
    }
    
    @Override
    public String toString(){
        return String.format("%s, Artist: %s, Genre: %s", super.toString(),artistName,genre);
    }
}
