package sg.edu.np.mad.teampk.stufftrek;

public class Container {
    private Integer ContainerID;
    public String Name;
    public String Picture;
    private Integer ContainerCategoryID;
    public void setContainerID(Integer ID){
        this.ContainerID = ID;
    }
    public void setContainerCategoryID(Integer ID){
        this.ContainerCategoryID = ID;
    }
    public Integer getContainerID(){return this.ContainerID;}
    public Integer getContainerCategoryID(){return this.ContainerCategoryID;}


    public Container(Integer containerID, String name, String picture, Integer containerCategoryID) {
        ContainerID = containerID;
        Name = name;
        Picture = picture;
        ContainerCategoryID = containerCategoryID;
    }
    public Container(String name, String picture, Integer containerCategoryID) {
        Name = name;
        Picture = picture;
        ContainerCategoryID = containerCategoryID;
    }

}
