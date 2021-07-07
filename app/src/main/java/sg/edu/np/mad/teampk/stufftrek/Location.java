package sg.edu.np.mad.teampk.stufftrek;

public class Location {
    private Integer LocationID;
    public String Name;
    public void setLocationID (Integer ID){
        this.LocationID=ID;
    }
    public Integer getLocationID(){return this.LocationID;}

    public Location(Integer locationID, String name) {
        LocationID = locationID;
        Name = name;
    }

    public Location(String name) {
        Name = name;
    }

}
