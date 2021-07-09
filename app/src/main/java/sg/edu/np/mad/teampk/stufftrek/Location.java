package sg.edu.np.mad.teampk.stufftrek;

public class Location {
    private Integer LocationID;
    public String Name;

    public Location(String name)
    {
        Name = name;
    }

    public Location(Integer locationID, String name)
    {
        LocationID = locationID;
        Name = name;
    }

    public Integer getLocationID()
    {
        return this.LocationID;
    }

    public void setLocationID (Integer ID)
    {
        this.LocationID = ID;
    }

}
