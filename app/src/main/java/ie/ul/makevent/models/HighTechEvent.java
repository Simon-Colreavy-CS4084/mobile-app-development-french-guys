package ie.ul.makevent.models;

public class HighTechEvent {
    //field
     public String name_event;
     public String date;
     public String hour;
     public String location;
     public String theme;
     public int nb_participant;

     //constructor
    public HighTechEvent(){
        name_event = "";
        date = "" ;
        hour = "";
        location = "";
        theme = "";
        nb_participant = 0;
    }

}
