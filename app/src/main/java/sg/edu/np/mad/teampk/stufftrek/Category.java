package sg.edu.np.mad.teampk.stufftrek;

public class Category {
    private Integer CategoryID;
    public String Name;
    public void setCategoryID(Integer ID){
        this.CategoryID=ID;
    }
    public Integer getCategoryID(){return this.CategoryID;}

    public Category(Integer categoryID, String name) {
        CategoryID = categoryID;
        Name = name;
    }
    public Category(String name) {
        Name = name;
    }
}
