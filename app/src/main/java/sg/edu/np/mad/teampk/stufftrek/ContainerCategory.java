package sg.edu.np.mad.teampk.stufftrek;

public class ContainerCategory {
    private Integer ContainerCategoryID;
    public String Name;
    private Integer RoomID;
    public void setContainerCategoryID(Integer ID){
        this.ContainerCategoryID=ID;
    }
    public void setRoomID(Integer ID){
        this.RoomID=ID;
    }
    public Integer getContainerCategoryID(){return this.ContainerCategoryID;}
    public Integer getRoomID(){return this.RoomID;}

    public ContainerCategory(Integer containerCategoryID, String name, Integer roomID) {
        ContainerCategoryID = containerCategoryID;
        Name = name;
        RoomID = roomID;
    }

    public ContainerCategory(String name, Integer roomID) {
        Name = name;
        RoomID = roomID;
    }

}
