package sg.edu.np.mad.teampk.stufftrek;

public class Item {
    private Integer ItemID;
    public String Name;
    public Integer Quantity;
    public String Picture;
    private Integer ContainerID;
    private Integer LocationID;
    private Integer RoomID;
    private Integer CategoryID;
    private Integer ContainerCategoryID;
    public String CategoryName;
    public String LocationName;
    public String ContainerCategoryName;
    public String RoomName;
    public String ContainerName;


    public void setItemID(Integer ID){
        this.ItemID=ID;
    }
    public void setContainerID(Integer ID){
        this.ContainerID=ID;
    }
    public void setLocationID(Integer ID){
        this.LocationID=ID;
    }
    public void setCategoryID(Integer ID){
        this.CategoryID=ID;
    }
    public void setContainerCategoryID(Integer ID){
        this.ContainerCategoryID=ID;
    }
    public void setRoomID(Integer ID){
        this.RoomID=ID;
    }
    public Integer getCategoryID(){return this.CategoryID;}
    public Integer getItemID(){return this.ItemID;}
    public Integer getLocationID(){return this.LocationID;}
    public Integer getContainerID(){return this.ContainerID;}
    public Integer getContainerCategoryID(){return this.ContainerCategoryID;}
    public Integer getRoomID(){return this.RoomID;}

    public Item(Integer itemID, String name, Integer quantity, String picture) {
        ItemID = itemID;
        Name = name;
        Quantity = quantity;
        Picture = picture;
    }

    public Item(String name, Integer quantity, String picture) {
        Name = name;
        Quantity = quantity;
        Picture = picture;
    }
    public Item(String name, Integer quantity) {
        Name = name;
        Quantity = quantity;
    }
}
