package ie.ul.makevent.models;

public class HighTechEvent {
    //field
     private String name_event;
     private String date;
     private String hour;
     private String location;
     private String theme;
     private int nb_participant;

     //constructor
    public HighTechEvent(String name_event, String date, String hour, String location, String theme, int nb_participant){
        this.name_event = name_event;
        this.date = date;
        this.hour = hour;
        this.location = location;
        this.theme = theme;
        this.nb_participant = nb_participant;
    }

    //methods

    public String getName_event() {return name_event;}
    public String getDate() {return date;}
    public String getHour() {return hour;}
    public String getLocation() {return location;}
    public String getTheme() {return theme;}
    public int getNb_participant() {return nb_participant;}

}
