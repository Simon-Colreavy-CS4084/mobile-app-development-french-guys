package ie.ul.makevent.models;

import java.util.ArrayList;

public class HighTechEvent {
    //field
     public String name_event;
     public String date;
     public String hour;
     public String location;
     public String theme;
     public String nb_participant;
     public ArrayList<String> participants;

     //constructor
    public HighTechEvent(){
        name_event = "";
        date = "" ;
        hour = "";
        location = "";
        theme = "";
        nb_participant = "";
        participants = new ArrayList<>();
    }

}
