package sg.edu.np.mad.teampk.stufftrek;

public class Category {
    private Integer CategoryID;
    public String Name;
    public  Integer Count;
    public void setCategoryID(Integer ID){
        this.CategoryID=ID;
    }
    public Integer getCategoryID(){return this.CategoryID;}

    public void setCount(Integer count){
        this.Count=count;
    }
    public Integer getCount(){return this.Count;}

    public Category(Integer categoryID, String name) {
        CategoryID = categoryID;
        Name = name;
    }
    public Category(String name) {
        Name = name;
    }
}
