package sg.edu.np.mad.teampk.stufftrek;

public class Room {
    private Integer RoomID;
    public String Name;
    public String Picture;
    private Integer LocationID;
    public void setRoomID(Integer ID){
        this.RoomID=ID;
    }
    public void setLocationID(Integer ID){
        this.LocationID=ID;
    }
    public Integer getRoomID(){return this.RoomID;}
    public Integer getLocationID(){return this.LocationID;}

    public Room(String name, String picture, Integer locationID) {
        Name = name;
        Picture = picture;
        LocationID = locationID;
    }

    public Room(Integer roomID, String name, String picture, Integer locationID) {
        RoomID = roomID;
        Name = name;
        Picture = picture;
        LocationID = locationID;
    }

    public Room(Integer roomID, String name, Integer locationID) {
        Name = name;
        RoomID=roomID;
        LocationID = locationID;
    }
}
