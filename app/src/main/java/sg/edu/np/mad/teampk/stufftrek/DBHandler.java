package sg.edu.np.mad.teampk.stufftrek;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class DBHandler extends SQLiteOpenHelper {
    public static int DATABASE_VERSION = 1;
    public static String DATABASE_NAME = "stufftrekDB.db";

    public static String TABLE_LOCATION = "Location";
    public static String COLUMN_LOCATIONID = "LocationID";
    public static String COLUMN_NAME = "Name";

    public static String TABLE_ROOM = "Room";
    public static String COLUMN_ROOMID ="RoomID";
    //public static String COLUMN_NAME = "Name";
    public static String COLUMN_PICTURE = "Picture";
    //public static String COLUMN_LOCATIONID = "LocationID";

    public static String TABLE_CONTAINERCATEGORY = "ContainerCategory";
    public static String COLUMN_CONTAINERCATEGORYID="ContainerCategoryID";
    //public static String COLUMN_NAME = "Name";
    //public static String COLUMN_ROOMID="RoomID";

    public static String TABLE_CONTAINER = "Container";
    public static String COLUMN_CONTAINERID="ContainerID";
    //public static String COLUMN_NAME = "Name";
    //public static String COLUMN_PICTURE = "Picture";
    //public static String COLUMN_CONTAINERCATEGORYID="ContainerCategoryID";

    public static String TABLE_ITEM = "Item";
    public static String COLUMN_ITEMID="ItemID";
    //public static String COLUMN_NAME = "Name";
    public static String COLUMN_QUANTITY = "Quantity";
    //public static String COLUMN_PICTURE = "Picture";
    //public static String COLUMN_CATEGORYID = "CategoryID";
    //public static String COLUMN_LOCATIONID = "LocationID";
    //public static String COLUMN_ROOMID="RoomID";
    //public static String COLUMN_CONTAINERCATEGORYID="ContainerCategoryID";
    //public static String COLUMN_CONTAINERID="ContainerID";

    public static String TABLE_CATEGORY = "Category";
    public static String COLUMN_CATEGORYID="CategoryID";
    //public static String COLUMN_NAME = "Name";

    public DBHandler(Context context,String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOCATION_TABLE = "CREATE TABLE " + TABLE_LOCATION +
                "(" + COLUMN_LOCATIONID + " INTEGER NOT NULL PRIMARY KEY," + COLUMN_NAME+ " TEXT NOT NULL" + ")";
        db.execSQL(CREATE_LOCATION_TABLE);

        String CREATE_ROOM_TABLE = "CREATE TABLE " + TABLE_ROOM+
                "(" + COLUMN_ROOMID + " INTEGER NOT NULL PRIMARY KEY," + COLUMN_NAME+ " TEXT NOT NULL,"+COLUMN_PICTURE+" TEXT,"
                +COLUMN_LOCATIONID+" INTEGER NOT NULL"+
                ", FOREIGN KEY("+COLUMN_LOCATIONID+") REFERENCES "+TABLE_LOCATION+"("+COLUMN_LOCATIONID+")" +")";
        db.execSQL(CREATE_ROOM_TABLE);

        String CREATE_CONTAINERCATEGORY_TABLE = "CREATE TABLE " + TABLE_CONTAINERCATEGORY+
                "(" + COLUMN_CONTAINERCATEGORYID + " INTEGER NOT NULL PRIMARY KEY," + COLUMN_NAME+ " TEXT NOT NULL,"
                +COLUMN_ROOMID+" INTEGER NOT NULL"+
                ", FOREIGN KEY("+COLUMN_ROOMID+") REFERENCES "+TABLE_ROOM+"("+COLUMN_ROOMID+")" +")";
        db.execSQL(CREATE_CONTAINERCATEGORY_TABLE);

        String CREATE_CONTAINER_TABLE = "CREATE TABLE " + TABLE_CONTAINER+
                "(" + COLUMN_CONTAINERID + " INTEGER NOT NULL PRIMARY KEY," + COLUMN_NAME+ " TEXT NOT NULL,"+COLUMN_PICTURE+" TEXT,"
                +COLUMN_CONTAINERCATEGORYID+" INTEGER NOT NULL"+
                ", FOREIGN KEY("+COLUMN_CONTAINERCATEGORYID+") REFERENCES "+TABLE_CONTAINERCATEGORY+"("+COLUMN_CONTAINERCATEGORYID+")" +")";
        db.execSQL(CREATE_CONTAINER_TABLE);

        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY +
                "(" + COLUMN_CATEGORYID + " INTEGER NOT NULL PRIMARY KEY," + COLUMN_NAME+ " TEXT NOT NULL" + ")";
        db.execSQL(CREATE_CATEGORY_TABLE);

        String CREATE_ITEM_TABLE = "CREATE TABLE " + TABLE_ITEM+
                "(" + COLUMN_ITEMID + " INTEGER NOT NULL PRIMARY KEY," + COLUMN_NAME+ " TEXT NOT NULL,"+COLUMN_PICTURE+" TEXT,"+COLUMN_QUANTITY+" INTEGER NOT NULL,"
                +COLUMN_CONTAINERID+" INTEGER ,"+
                COLUMN_CATEGORYID+" INTEGER NOT NULL,"+
                COLUMN_ROOMID+" INTEGER ,"+
                COLUMN_LOCATIONID+" INTEGER ,"+
                COLUMN_CONTAINERCATEGORYID+" INTEGER "+
                ", FOREIGN KEY("+COLUMN_CONTAINERCATEGORYID+") REFERENCES "+TABLE_CONTAINERCATEGORY+"("+COLUMN_CONTAINERCATEGORYID+")"
                +", FOREIGN KEY("+COLUMN_ROOMID+") REFERENCES "+TABLE_ROOM+"("+COLUMN_ROOMID+")"
                +", FOREIGN KEY("+COLUMN_LOCATIONID+") REFERENCES "+TABLE_LOCATION+"("+COLUMN_LOCATIONID+")"
                +", FOREIGN KEY("+COLUMN_CONTAINERID+") REFERENCES "+TABLE_CONTAINER+"("+COLUMN_CONTAINERID+")"
                +", FOREIGN KEY("+COLUMN_CATEGORYID+") REFERENCES "+TABLE_CATEGORY+"("+COLUMN_CATEGORYID+")"
                +")";
        db.execSQL(CREATE_ITEM_TABLE);

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,"Unassigned");
        db.insert(TABLE_CATEGORY,null,values);

        /* POPULATING TABLE SAMPLE DATA */
        // To be removed - for testing purpose only.
        /*
        for (int i = 1; i <= 3; i++)
        {
        }
         */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS Location");
            onCreate(db);
        }
    }

    //CreateHandler
    public Integer AddLocation(Location l){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,l.Name);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_LOCATION,null,values);
        String query = "SELECT last_insert_rowid();";
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        l.setLocationID(Integer.parseInt(cursor.getString(0)));
        db.close();
        return l.getLocationID();
    }

    public Integer AddRoom(Room r){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,r.Name);
        values.put(COLUMN_LOCATIONID,r.getLocationID());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_ROOM,null,values);
        String query = "SELECT last_insert_rowid();";
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        r.setRoomID(Integer.parseInt(cursor.getString(0)));
        db.close();
        return r.getRoomID();
    }

    public Integer AddContainerCategory(ContainerCategory cc){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,cc.Name);
        values.put(COLUMN_ROOMID,cc.getRoomID());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_CONTAINERCATEGORY,null,values);
        String query = "SELECT last_insert_rowid();";
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        cc.setContainerCategoryID(Integer.parseInt(cursor.getString(0)));
        db.close();
        return cc.getContainerCategoryID();
    }

    public Integer AddContainer(Container c){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, c.Name);
        values.put(COLUMN_CONTAINERCATEGORYID, c.getContainerCategoryID());
        values.put(COLUMN_PICTURE, c.Picture);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_CONTAINER,null, values);
        String query = "SELECT last_insert_rowid();";
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        c.setContainerID(Integer.parseInt(cursor.getString(0)));
        db.close();
        return c.getContainerID();
    }

    public Integer AddCategory(Category c ){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,c.Name);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_CATEGORY,null,values);
        String query = "SELECT last_insert_rowid();";
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        c.setCategoryID(Integer.parseInt(cursor.getString(0)));
        db.close();
        return c.getCategoryID();
    }

    public Integer AddItem(Item i){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,i.Name);
        values.put(COLUMN_LOCATIONID,i.getLocationID());
        values.put(COLUMN_ROOMID,i.getRoomID());
        values.put(COLUMN_CATEGORYID,i.getCategoryID());
        values.put(COLUMN_QUANTITY, i.Quantity);
        values.put(COLUMN_CONTAINERID,i.getContainerID());
        values.put(COLUMN_CONTAINERCATEGORYID,i.getContainerCategoryID());
        values.put(COLUMN_PICTURE, i.Picture);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_ITEM,null,values);
        String query = "SELECT last_insert_rowid();";
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        i.setItemID(Integer.parseInt(cursor.getString(0)));
        db.close();
        return i.getItemID();
    }

    //GetHandler
    public ArrayList<Location> GetAllLocation(){
        ArrayList<Location> locationArrayList = new ArrayList<Location>();
        String query = "SELECT * FROM " +TABLE_LOCATION;
        SQLiteDatabase db = this.getWritableDatabase(); //readable
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Integer LocationID = Integer.parseInt(cursor.getString(0));
                String Name = cursor.getString(1);
                Location l = new Location(LocationID,Name);
                locationArrayList.add(l);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return locationArrayList;
    }

    public ArrayList<Room> GetAllRoomFromLocation(Integer LocationID){
        ArrayList<Room> roomArrayList = new ArrayList<Room>();
        String query = "SELECT * FROM " +TABLE_ROOM + " WHERE "+COLUMN_LOCATIONID+" = "+LocationID;
        SQLiteDatabase db = this.getWritableDatabase(); //readable
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Integer RoomID = Integer.parseInt(cursor.getString(0));
                String Name = cursor.getString(1);
                String Picture = cursor.getString(2);
                Room r = new Room(RoomID,Name,Picture,LocationID);
                roomArrayList.add(r);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return roomArrayList;
    }

    public ArrayList<ContainerCategory> GetAllContainerCategoryFromRoom(Integer RoomID){
        ArrayList<ContainerCategory> containerCategoryArrayList = new ArrayList<ContainerCategory>();
        String query = "SELECT * FROM " +TABLE_CONTAINERCATEGORY + " WHERE "+COLUMN_ROOMID+" = "+RoomID;
        SQLiteDatabase db = this.getWritableDatabase(); //readable
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Integer ContainerCategoryID=Integer.parseInt(cursor.getString(0));
                String Name=cursor.getString(1);
                ContainerCategory cc = new ContainerCategory(ContainerCategoryID,Name,RoomID);
                containerCategoryArrayList.add(cc);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return containerCategoryArrayList;
    }

    public ArrayList<Container> GetAllContainerFromContainerCategory(Integer ContainerCategoryID){
        ArrayList<Container> containerArrayList = new ArrayList<Container>();
        String query = "SELECT * FROM " +TABLE_CONTAINER+ " WHERE "+COLUMN_CONTAINERCATEGORYID+" = "+ContainerCategoryID;
        SQLiteDatabase db = this.getWritableDatabase(); //readable
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Integer ContainerID =Integer.parseInt(cursor.getString(0));
                String Name =cursor.getString(1);
                String Picture= cursor.getString(2);
                Container c = new Container(ContainerID,Name,Picture,ContainerCategoryID);
                containerArrayList.add(c);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return containerArrayList;
    }

    public ArrayList<Category> GetAllCategories(){
        ArrayList<Category> categoryArrayList = new ArrayList<Category>();
        String query = "SELECT * FROM " +TABLE_CATEGORY;
        SQLiteDatabase db = this.getWritableDatabase(); //readable
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Integer CategoryID =Integer.parseInt(cursor.getString(0));
                String Name =cursor.getString(1);
                Category c = new Category(CategoryID,Name);
                categoryArrayList.add(c);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return categoryArrayList;
    }

    public Integer GetCategoryCount(Integer catID){
        Integer count = 0;
        ArrayList<Category> categoryArrayList = new ArrayList<Category>();
        String query = "SELECT COUNT(*) FROM " +TABLE_ITEM + " WHERE " + COLUMN_CATEGORYID + " = " + catID;
        SQLiteDatabase db = this.getWritableDatabase(); //readable
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            count = Integer.parseInt(cursor.getString(0));
        }
        cursor.close();
        db.close();
        return count;
    }

    public ArrayList<Item> GetAllItemFromLocation(Integer LocationID){
        ArrayList<Item> itemArrayList = new ArrayList<Item>();
        String query = "SELECT "+TABLE_ITEM+".*,"+TABLE_CATEGORY+"."+COLUMN_NAME+" FROM " +TABLE_ITEM
                +" INNER JOIN " + TABLE_CATEGORY +" ON " + TABLE_CATEGORY +"."+COLUMN_CATEGORYID+"="+TABLE_ITEM+"."+COLUMN_CATEGORYID+
                " WHERE "+COLUMN_LOCATIONID+" = "+LocationID
                +" AND " +COLUMN_ROOMID +" IS NULL"
                +" AND " +COLUMN_CONTAINERID +" IS NULL"
                +" AND " +COLUMN_CONTAINERCATEGORYID +" IS NULL"
                ;
        SQLiteDatabase db = this.getWritableDatabase(); //readable
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Integer ItemID =Integer.parseInt(cursor.getString(0));
                String Name=cursor.getString(1);
                String Picture=cursor.getString(2);
                Integer Quantity = Integer.parseInt(cursor.getString(3));
                Item i = new Item(ItemID,Name,Quantity,Picture);
                i = parseItemFK(cursor,i);
                i.CategoryName=cursor.getString(9);
                itemArrayList.add(i);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return itemArrayList;
    }

    public ArrayList<Item> GetAllItemFromRoom(Integer RoomID){
        ArrayList<Item> itemArrayList = new ArrayList<Item>();
        String query = "SELECT "+TABLE_ITEM+".*,"+TABLE_CATEGORY+"."+COLUMN_NAME+" FROM " +TABLE_ITEM
                +" INNER JOIN " + TABLE_CATEGORY +" ON " + TABLE_CATEGORY +"."+COLUMN_CATEGORYID+"="+TABLE_ITEM+"."+COLUMN_CATEGORYID+
                " WHERE "+COLUMN_ROOMID+" = "+RoomID
                +" AND " +COLUMN_CONTAINERID +" IS NULL"
                +" AND " +COLUMN_CONTAINERCATEGORYID +" IS NULL"
                ;
        SQLiteDatabase db = this.getWritableDatabase(); //readable
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Integer ItemID =Integer.parseInt(cursor.getString(0));
                String Name=cursor.getString(1);
                String Picture=cursor.getString(2);
                Integer Quantity = Integer.parseInt(cursor.getString(3));
                Item i = new Item(ItemID,Name,Quantity,Picture);
                i = parseItemFK(cursor,i);
                i.CategoryName=cursor.getString(9);
                itemArrayList.add(i);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return itemArrayList;
    }

    public ArrayList<Item> GetAllItemFromContainer(Integer ContainerID){
        ArrayList<Item> itemArrayList = new ArrayList<Item>();
        String query = "SELECT "+TABLE_ITEM+".*,"+TABLE_CATEGORY+"."+COLUMN_NAME+" FROM " +TABLE_ITEM
                +" INNER JOIN " + TABLE_CATEGORY +" ON " + TABLE_CATEGORY +"."+COLUMN_CATEGORYID+"="+TABLE_ITEM+"."+COLUMN_CATEGORYID+
                " WHERE "+COLUMN_CONTAINERID+" = "+ContainerID;

        SQLiteDatabase db = this.getWritableDatabase(); //readable
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Integer ItemID =Integer.parseInt(cursor.getString(0));
                String Name=cursor.getString(1);
                String Picture=cursor.getString(2);
                Integer Quantity = Integer.parseInt(cursor.getString(3));
                Item i = new Item(ItemID,Name,Quantity,Picture);
                i = parseItemFK(cursor,i);
                i.CategoryName=cursor.getString(9);
                itemArrayList.add(i);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return itemArrayList;
    }

    public ArrayList<Item> GetAllItemFromCategory(Integer CategoryID){
        ArrayList<Item> itemArrayList = new ArrayList<Item>();
        String query = "SELECT ITEM.*,Location.Name,Room.Name,ContainerCategory.Name,Container.Name,Category.Name FROM  " +TABLE_ITEM
                + " LEFT JOIN "+TABLE_LOCATION+" ON " +TABLE_LOCATION+"."+COLUMN_LOCATIONID+" = "+TABLE_ITEM+"."+COLUMN_LOCATIONID
                + " LEFT JOIN "+TABLE_ROOM+" ON " +TABLE_ROOM+"."+COLUMN_ROOMID+" = "+TABLE_ITEM+"."+COLUMN_ROOMID
                + " LEFT JOIN "+TABLE_CONTAINERCATEGORY+" ON " +TABLE_CONTAINERCATEGORY+"."+COLUMN_CONTAINERCATEGORYID+" = "+TABLE_ITEM+"."+COLUMN_CONTAINERCATEGORYID
                + " LEFT JOIN "+TABLE_CONTAINER+" ON " +TABLE_CONTAINER+"."+COLUMN_CONTAINERID+" = "+TABLE_ITEM+"."+COLUMN_CONTAINERID
                + " LEFT JOIN "+TABLE_CATEGORY+" ON " +TABLE_CATEGORY+"."+COLUMN_CATEGORYID+" = "+TABLE_ITEM+"."+COLUMN_CATEGORYID
                + " WHERE Item."+COLUMN_CATEGORYID+" = "+CategoryID;
        SQLiteDatabase db = this.getWritableDatabase(); //readable
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Integer ItemID =Integer.parseInt(cursor.getString(0));
                String Name =cursor.getString(1);
                String Picture=cursor.getString(2);
                Integer Quantity = Integer.parseInt(cursor.getString(3));
                Item i = new Item(ItemID,Name,Quantity,Picture);
                i = parseItemFK(cursor,i);
                String LocationName =cursor.getString(9);
                String RoomName =cursor.getString(10);
                String ContainerCategoryName =cursor.getString(11);
                String ContainerName =cursor.getString(12);
                String CategoryName =cursor.getString(13);
                i = parseItemFK(cursor,i);
                i=parseItemFKName(cursor,i);
                itemArrayList.add(i);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return itemArrayList;
    }

    public ArrayList<Item> GetAllUnassignedItem(){
        ArrayList<Item> itemArrayList = new ArrayList<Item>();
        String query = "SELECT * FROM " +TABLE_ITEM
                +" WHERE "
                +COLUMN_LOCATIONID +" IS NULL AND "
                +COLUMN_CONTAINERID +" IS NULL AND "
                +COLUMN_CONTAINERCATEGORYID +" IS NULL AND "
                +COLUMN_ROOMID +" IS NULL"
                ;
        SQLiteDatabase db = this.getWritableDatabase(); //readable
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Integer ItemID =Integer.parseInt(cursor.getString(0));
                String Name=cursor.getString(1);
                String Picture=cursor.getString(2);
                Integer Quantity = Integer.parseInt(cursor.getString(3));
                Item i = new Item(ItemID,Name,Quantity,Picture);
                itemArrayList.add(i);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return itemArrayList;
    }

    public ArrayList<Item> GetAllItem(){
        ArrayList<Item> itemArrayList = new ArrayList<Item>();
        String query = "SELECT ITEM.*, Location.Name, Room.Name, ContainerCategory.Name, Container.Name, Category.Name FROM " +TABLE_ITEM
                + " LEFT JOIN "+TABLE_LOCATION+" ON " +TABLE_LOCATION+"."+COLUMN_LOCATIONID+" = "+TABLE_ITEM+"."+COLUMN_LOCATIONID
                + " LEFT JOIN "+TABLE_ROOM+" ON " +TABLE_ROOM+"."+COLUMN_ROOMID+" = "+TABLE_ITEM+"."+COLUMN_ROOMID
                + " LEFT JOIN "+TABLE_CONTAINERCATEGORY+" ON " +TABLE_CONTAINERCATEGORY+"."+COLUMN_CONTAINERCATEGORYID+" = "+TABLE_ITEM+"."+COLUMN_CONTAINERCATEGORYID
                + " LEFT JOIN "+TABLE_CONTAINER+" ON " +TABLE_CONTAINER+"."+COLUMN_CONTAINERID+" = "+TABLE_ITEM+"."+COLUMN_CONTAINERID
                + " LEFT JOIN "+TABLE_CATEGORY+" ON " +TABLE_CATEGORY+"."+COLUMN_CATEGORYID+" = "+TABLE_ITEM+"."+COLUMN_CATEGORYID
                ;
        SQLiteDatabase db = this.getWritableDatabase(); //readable
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Integer ItemID =Integer.parseInt(cursor.getString(0));
                String Name=cursor.getString(1);
                String Picture=cursor.getString(2);
                Integer Quantity = Integer.parseInt(cursor.getString(3));
                Item i = new Item(ItemID,Name,Quantity,Picture);
//                i = parseItemFK(cursor,i);

//                if (cursor.getString(10) == null)
//                {
//                    i.RoomName = "";
//                }
//                else
//                {
//                    i.RoomName = cursor.getString(10);
//                }
//
//                if (cursor.getString(11) == null)
//                {
//                    i.ContainerCategoryName = "";
//                }
//                else
//                {
//                    i.ContainerCategoryName = cursor.getString(12);
//                }
//
//                if (cursor.getString(12) == null)
//                {
//                    i.ContainerName = "";
//                }
//                else
//                {
//                    i.ContainerName = cursor.getString(12);
//                }
//
//                if (cursor.getString(9) == null)
//                {
//                    i.LocationName = "";
//                }
//                else
//                {
//                    i.LocationName = cursor.getString(9);
//                }
//
//                i.CategoryName = cursor.getString(13);
//
//                String LocationName =cursor.getString(9);
//                String RoomName =cursor.getString(10);
//                String ContainerCategoryName =cursor.getString(11);
//                String ContainerName =cursor.getString(12);
//                String CategoryName =cursor.getString(13);
                i = parseItemFK(cursor,i);
                i=parseItemFKName(cursor,i);

                itemArrayList.add(i);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return itemArrayList;
    }
    public ArrayList<Item> SearchItem(String itemName){
        ArrayList<Item> itemArrayList = new ArrayList<Item>();
        String query = "SELECT ITEM.*,Location.Name,Room.Name,ContainerCategory.Name,Container.Name,Category.Name FROM  " +TABLE_ITEM
                + " LEFT JOIN "+TABLE_LOCATION+" ON " +TABLE_LOCATION+"."+COLUMN_LOCATIONID+" = "+TABLE_ITEM+"."+COLUMN_LOCATIONID
                + " LEFT JOIN "+TABLE_ROOM+" ON " +TABLE_ROOM+"."+COLUMN_ROOMID+" = "+TABLE_ITEM+"."+COLUMN_ROOMID
                + " LEFT JOIN "+TABLE_CONTAINERCATEGORY+" ON " +TABLE_CONTAINERCATEGORY+"."+COLUMN_CONTAINERCATEGORYID+" = "+TABLE_ITEM+"."+COLUMN_CONTAINERCATEGORYID
                + " LEFT JOIN "+TABLE_CONTAINER+" ON " +TABLE_CONTAINER+"."+COLUMN_CONTAINERID+" = "+TABLE_ITEM+"."+COLUMN_CONTAINERID
                + " LEFT JOIN "+TABLE_CATEGORY+" ON " +TABLE_CATEGORY+"."+COLUMN_CATEGORYID+" = "+TABLE_ITEM+"."+COLUMN_CATEGORYID
                + " WHERE "+TABLE_ITEM+"."+COLUMN_NAME+" LIKE '%"+itemName+"%'";
        System.out.println(query);

        SQLiteDatabase db = this.getWritableDatabase(); //readable
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Integer ItemID =Integer.parseInt(cursor.getString(0));
                String Name =cursor.getString(1);
                String Picture=cursor.getString(2);
                Integer Quantity = Integer.parseInt(cursor.getString(3));
                Item i = new Item(ItemID,Name,Quantity,Picture);
                i = parseItemFK(cursor,i);
                String LocationName = cursor.getString(9);
                String RoomName = cursor.getString(10);
                String ContainerCategoryName =cursor.getString(11);
                String ContainerName =cursor.getString(12);
                String CategoryName =cursor.getString(13);
                i = parseItemFK(cursor,i);
                i=parseItemFKName(cursor,i);
                itemArrayList.add(i);
                cursor.moveToNext();
            }
        }else{
            System.out.println("No result");
        }
        cursor.close();
        db.close();
        return itemArrayList;
    }

    public Item parseItemFK(Cursor cursor,Item i){
        try{
            Integer ContainerID = Integer.parseInt(cursor.getString(4));
            i.setContainerID(ContainerID);
        }
        catch(NumberFormatException e){
            //havent figure out what to do with catch
        }
        try{
            Integer CategoryID=Integer.parseInt(cursor.getString(5));
            i.setCategoryID(CategoryID);
        }
        catch(NumberFormatException e){
            //havent figure out what to do with catch
        }
        try{
            Integer RoomID=Integer.parseInt(cursor.getString(6));
            i.setRoomID(RoomID);
        }
        catch(NumberFormatException e){
            //havent figure out what to do with catch
        }
        try{
            Integer LocationID=Integer.parseInt(cursor.getString(7));
            i.setLocationID(LocationID);
        }
        catch(NumberFormatException e){
            //havent figure out what to do with catch
        }
        try{
            Integer ContainerCategoryID=Integer.parseInt(cursor.getString(8));
            i.setContainerCategoryID(ContainerCategoryID);
        }
        catch(NumberFormatException e){
            //havent figure out what to do with catch
        }

        return i;
    }

    public Item parseItemFKName(Cursor cursor,Item i){
        try{
            String LocationName =cursor.getString(9);
            i.LocationName=LocationName;
        }
        catch(IllegalStateException e){
            //havent figure out what to do with catch
        }
        try{
            String RoomName =cursor.getString(10);
            i.RoomName=RoomName;
        }
        catch(IllegalStateException e){
            //havent figure out what to do with catch
        }
        try{
            String ContainerCategoryName =cursor.getString(11);
            i.ContainerCategoryName=ContainerCategoryName;
        }
        catch(IllegalStateException e){
            //havent figure out what to do with catch
        }
        try{
            String ContainerName =cursor.getString(12);
            i.ContainerName=ContainerName;
        }
        catch(IllegalStateException e){
            //havent figure out what to do with catch
        }
        try{
            String CategoryName =cursor.getString(13);
            i.CategoryName=CategoryName;
        }
        catch(IllegalStateException e){
            //havent figure out what to do with catch
        }

        return i;
    }

    public Room GetRoomWithID(Integer roomID){
        String query = "SELECT * FROM " +TABLE_ROOM + " WHERE " +COLUMN_ROOMID +" = "+roomID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        Room r = null;
        if (cursor.moveToFirst()){
            String Name=cursor.getString(1);
            String Picture;
            Integer LocationID=Integer.parseInt(cursor.getString(3));
            try{
                Picture = cursor.getString(2);
                r = new Room(roomID,Name,Picture,LocationID);
            }
            catch(IllegalStateException e){
                r = new Room(roomID,Name,LocationID);
            }
        }
        cursor.close();
        db.close();
        return r;
    }

    //DeleteHandler

    public boolean DeleteItem(Integer ItemID){
        boolean result = false;
        String query = "SELECT * FROM " +TABLE_ITEM + " WHERE " +COLUMN_ITEMID +" = "+ItemID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            String deleteItemQuery = "DELETE FROM " +TABLE_ITEM +" WHERE "  +COLUMN_ITEMID +" = "+ItemID;
            db.execSQL(deleteItemQuery);
            cursor.close();
            result=true;
        }
        db.close();
        return result;
    }
    // Check if there are items in container. (True/False:Yes/No)
    public boolean CheckContainerItems(Integer ContainerID){
        boolean result = false;
        String query = "SELECT * FROM " +TABLE_ITEM + " WHERE " +COLUMN_CONTAINERID +" = "+ContainerID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            cursor.close();
            result=true;
        }
        db.close();
        return result;
    }
    public boolean DeleteContainer(Integer ContainerID){
        boolean result = false;
        String query = "SELECT * FROM " +TABLE_CONTAINER + " WHERE " +COLUMN_CONTAINERID +" = "+ContainerID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            String updateQuery = "UPDATE " +TABLE_ITEM +" SET "+COLUMN_CONTAINERID+ " = NULL"
                    +" WHERE " +COLUMN_CONTAINERID +" = "+ContainerID;
            String deleteContainerQuery = "DELETE FROM "+TABLE_CONTAINER + " WHERE " +COLUMN_CONTAINERID +" = "+ContainerID;

            cursor.close();
            db.execSQL(updateQuery);
            db.execSQL(deleteContainerQuery);
            result=true;
        }
        db.close();
        return result;
    }
    // Check if there are containers in containerCategory. (True/False:Yes/No)
    public boolean CheckContainerCategoryContainers(Integer ContainerCategoryID){
        boolean result = false;
        String query = "SELECT * FROM " +TABLE_CONTAINER + " WHERE " +COLUMN_CONTAINERCATEGORYID +" = "+ContainerCategoryID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            cursor.close();
            result=true;
        }
        db.close();
        return result;
    }

    public boolean DeleteContainerCategory(Integer ContainerCategoryID){
        boolean result = false;
        String query = "SELECT * FROM " +TABLE_CONTAINERCATEGORY + " WHERE " +COLUMN_CONTAINERCATEGORYID +" = "+ContainerCategoryID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            String updateQuery = "UPDATE " +TABLE_ITEM +" SET "+COLUMN_CONTAINERCATEGORYID+ " = NULL,"+COLUMN_CONTAINERID+ " = NULL"
                    +" WHERE " +COLUMN_CONTAINERCATEGORYID +" = "+ContainerCategoryID;
            String deleteContainerQuery = "DELETE FROM " +TABLE_CONTAINER + " WHERE " +COLUMN_CONTAINERCATEGORYID +" = "+ContainerCategoryID;
            String deleteContainerCategoryQuery = "DELETE FROM " +TABLE_CONTAINERCATEGORY + " WHERE " +COLUMN_CONTAINERCATEGORYID +" = "+ContainerCategoryID;

            cursor.close();
            db.execSQL(updateQuery);
            db.execSQL(deleteContainerQuery);
            db.execSQL(deleteContainerCategoryQuery);
            result=true;
        }
        db.close();
        return result;
    }

    // Check if there are containers in room. (True/False:Yes/No)
    public boolean CheckRoomContainerCategory(Integer RoomID){
        boolean result = false;
        String query = "SELECT * FROM " +TABLE_CONTAINERCATEGORY + " WHERE " +COLUMN_ROOMID +" = "+RoomID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            cursor.close();
            result=true;
        }
        db.close();
        return result;
    }

    public boolean DeleteRoom(Integer RoomID){
        boolean result = false;
        String query = "SELECT * FROM " +TABLE_ROOM + " WHERE " +COLUMN_ROOMID +" = "+RoomID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            String updateQuery = "UPDATE " +TABLE_ITEM +" SET "+COLUMN_ROOMID+ " = NULL,"+COLUMN_CONTAINERCATEGORYID+ " = NULL,"+COLUMN_CONTAINERID+ " = NULL"
                    +" WHERE " +COLUMN_ROOMID +" = "+RoomID;
            String deleteContainerQuery = "DELETE FROM " +TABLE_CONTAINER + " WHERE " +COLUMN_CONTAINERCATEGORYID +" IN " +
                    "(SELECT "+COLUMN_CONTAINERCATEGORYID + " FROM " +TABLE_CONTAINERCATEGORY +" WHERE " +COLUMN_ROOMID+" = "
                    +RoomID+")";
            String deleteContainerCategoryQuery = "DELETE FROM " +TABLE_CONTAINERCATEGORY + " WHERE " +COLUMN_ROOMID +" = "
                    +RoomID;
            String deleteRoomQuery = "DELETE FROM " +TABLE_ROOM + " WHERE " +COLUMN_ROOMID +" = "+RoomID;

            cursor.close();
            db.execSQL(updateQuery);
            db.execSQL(deleteContainerQuery);
            db.execSQL(deleteContainerCategoryQuery);
            db.execSQL(deleteRoomQuery);
            result=true;
        }
        db.close();
        return result;
    }

    // Check if there are Rooms in Location. (True/False:Yes/No)
    public boolean CheckLocationRoom(Integer LocationID){
        boolean result = false;
        String query = "SELECT * FROM " +TABLE_ROOM + " WHERE " +COLUMN_LOCATIONID +" = "+LocationID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            cursor.close();
            result=true;
        }
        db.close();
        return result;
    }

    public boolean DeleteLocation(Integer LocationID){
        boolean result = false;
        String query = "SELECT * FROM " +TABLE_LOCATION + " WHERE " +COLUMN_LOCATIONID +" = "+LocationID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            String updateQuery = "UPDATE " +TABLE_ITEM +" SET "
                    +COLUMN_LOCATIONID+ " = NULL,"
                    +COLUMN_ROOMID+ " = NULL,"
                    +COLUMN_CONTAINERCATEGORYID+ " = NULL,"
                    +COLUMN_CONTAINERID+ " = NULL,"
                    +COLUMN_LOCATIONID+ " = NULL"
                    +" WHERE " +COLUMN_LOCATIONID +" = "+LocationID;
            String deleteContainerQuery = "DELETE FROM " +TABLE_CONTAINER + " WHERE " +COLUMN_CONTAINERCATEGORYID +" IN " +
                    "(SELECT "+COLUMN_CONTAINERCATEGORYID + " FROM " +TABLE_CONTAINERCATEGORY +" WHERE " +COLUMN_ROOMID+" IN "
                    +"(SELECT "+COLUMN_ROOMID+" FROM "+TABLE_ROOM +" WHERE "+COLUMN_LOCATIONID +" = "+LocationID+"))";
            String deleteContainerCategoryQuery = "DELETE FROM " +TABLE_CONTAINERCATEGORY + " WHERE " +COLUMN_ROOMID +" IN "
                    +"(SELECT "+COLUMN_ROOMID+" FROM "+TABLE_ROOM +" WHERE "+COLUMN_LOCATIONID +" = "+LocationID+")";
            String deleteRoomQuery = "DELETE FROM " +TABLE_ROOM + " WHERE " +COLUMN_LOCATIONID +" = "+LocationID;
            String deleteLocationQuery = "DELETE FROM " +TABLE_LOCATION + " WHERE " +COLUMN_LOCATIONID +" = "+LocationID;


            cursor.close();
            db.execSQL(updateQuery);
            db.execSQL(deleteContainerQuery);
            db.execSQL(deleteContainerCategoryQuery);
            db.execSQL(deleteRoomQuery);
            db.execSQL(deleteLocationQuery);

            result=true;
        }
        db.close();
        return result;
    }

    // Check if there are items in category. (True/False:Yes/No)
    public boolean CheckCategoryItem(Integer CategoryID){
        boolean result = false;
        String query = "SELECT * FROM " +TABLE_ITEM + " WHERE " +COLUMN_CATEGORYID +" = "+CategoryID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            cursor.close();
            result=true;
        }
        db.close();
        return result;
    }

    // Deletes Category (True/False:Success/Failure)
    public boolean DeleteCategory(Integer CategoryID){
        boolean result = false;
        String query = "SELECT * FROM " +TABLE_CATEGORY + " WHERE " +COLUMN_CATEGORYID +" = "+CategoryID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            String updateQuery = "UPDATE " +TABLE_ITEM+" SET "+COLUMN_CATEGORYID+ " = 1 WHERE " +COLUMN_CATEGORYID +" = "+CategoryID;
            String deleteQuery = "DELETE FROM " +TABLE_CATEGORY + " WHERE " +COLUMN_CATEGORYID +" = "+CategoryID;
            db.execSQL(updateQuery);
            db.execSQL(deleteQuery);
            cursor.close();
            result=true;
        }
        db.close();
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("DELETE FROM " +TABLE_CATEGORY +" WHERE "  +COLUMN_CATEGORYID +" = "+CategoryID);
//        ArrayList<Item> list = GetAllItemFromCategory(CategoryID);
//        for(Item i : list) {
//            String query = "SELECT * FROM " + TABLE_CATEGORY + " WHERE " + COLUMN_NAME + " = 'Unassigned'";
//            Cursor cursor = db.rawQuery(query,null);
//            if (cursor.moveToFirst()) {
//                Integer unassignedID =Integer.parseInt(cursor.getString(0));
//                ContentValues cv = new ContentValues();
//                cv.put(COLUMN_CATEGORYID,unassignedID);
//                db.update(TABLE_ITEM, cv, COLUMN_ITEMID + " = " + i.getItemID(), null);
//            }
//        }
//        db.close();
        return result;
    }

    //UpdateHandler

    public boolean UpdateRoomPhoto(Integer roomID,String path){
        boolean result = false;
        String query = "SELECT * FROM " +TABLE_ROOM + " WHERE " +COLUMN_ROOMID +" = "+roomID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            String updateQuery = "UPDATE " + TABLE_ROOM + " SET " + COLUMN_PICTURE + " = \"" + path
                    + "\" WHERE " + COLUMN_ROOMID + " = " + roomID;
            db.execSQL(updateQuery);
            cursor.close();
            result=true;
        }
        db.close();
        return result;
    }

    public boolean UpdateItemPhoto(Integer itemID,String path){
        boolean result = false;
        String query = "SELECT * FROM " +TABLE_ITEM + " WHERE " +COLUMN_ITEMID +" = "+itemID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            String updateQuery = "UPDATE " + TABLE_ITEM + " SET "+ COLUMN_PICTURE + " = \"" + path
                    + "\" WHERE " + COLUMN_ITEMID + " = " + itemID;
            db.execSQL(updateQuery);
            cursor.close();
            result=true;
        }
        db.close();
        return result;
    }

    public boolean UpdateContainerPhoto(Integer containerID,String path){
        boolean result = false;
        String query = "SELECT * FROM " +TABLE_CONTAINER + " WHERE " +COLUMN_CONTAINERID +" = "+containerID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            String updateQuery = "UPDATE " + TABLE_CONTAINER + " SET " + COLUMN_PICTURE + " = \"" + path
                    + "\" WHERE " +COLUMN_CONTAINERID +" = "+containerID;
            db.execSQL(updateQuery);
            cursor.close();
            result=true;
        }
        db.close();
        return result;
    }
    public boolean UpdateLocation(Location l){
        boolean result = false;
        String query = "SELECT * FROM " +TABLE_LOCATION + " WHERE " +COLUMN_LOCATIONID +" = "+l.getLocationID();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            String updateQuery = "UPDATE "+TABLE_LOCATION
                    + " SET " + COLUMN_NAME + " = \"" + l.Name +"\""
                    + " WHERE " +COLUMN_LOCATIONID +" = "+l.getLocationID();
            db.execSQL(updateQuery);
            cursor.close();
            result=true;
        }
        db.close();
        return result;
    }

    public boolean UpdateRoom(Room r){
        boolean result = false;
        String query = "SELECT * FROM " +TABLE_ROOM + " WHERE " +COLUMN_ROOMID +" = "+r.getRoomID();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            String updateQuery = "UPDATE "+TABLE_ROOM
                    + " SET " + COLUMN_NAME + " = \"" + r.Name +"\""
                    + " WHERE " +COLUMN_ROOMID +" = "+r.getRoomID();
            db.execSQL(updateQuery);
            cursor.close();
            result=true;
        }
        db.close();
        return result;
    }

    public boolean UpdateContainerCategory(ContainerCategory cc){
        boolean result = false;
        String query = "SELECT * FROM " +TABLE_CONTAINERCATEGORY + " WHERE " +COLUMN_CONTAINERCATEGORYID +" = "+cc.getContainerCategoryID();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            String updateQuery = "UPDATE "+TABLE_CONTAINERCATEGORY
                    + " SET " + COLUMN_NAME + " = \"" + cc.Name +"\""
                    + " WHERE " +COLUMN_CONTAINERCATEGORYID +" = "+cc.getContainerCategoryID();
            db.execSQL(updateQuery);
            cursor.close();
            result=true;
        }
        db.close();
        return result;
    }

    public boolean UpdateContainer(Container c){
        boolean result = false;
        String query = "SELECT * FROM " +TABLE_CONTAINER + " WHERE " +COLUMN_CONTAINERID +" = "+c.getContainerID();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            String updateQuery = "UPDATE "+TABLE_CONTAINER
                    + " SET " + COLUMN_NAME + " = \"" + c.Name +"\""
                    + " SET " + COLUMN_PICTURE + " = \"" + c.Picture+"\""
                    + " WHERE " +COLUMN_CONTAINERID +" = "+c.getContainerID();
            db.execSQL(updateQuery);
            cursor.close();
            result=true;
        }
        db.close();
        return result;
    }

    public boolean UpdateItem(Item i){
        boolean result = false;
        String query = "SELECT * FROM " +TABLE_ITEM + " WHERE " +COLUMN_ITEMID +" = "+i.getItemID();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            String updateQuery = "UPDATE "+TABLE_ITEM
                    + " SET " + COLUMN_NAME + " = \"" + i.Name +"\""
                    + " SET " + COLUMN_PICTURE + " = \"" + i.Picture+"\""
                    + " SET " + COLUMN_QUANTITY + " = " + i.Quantity
                    + " WHERE " +COLUMN_ITEMID +" = "+i.getItemID();
            db.execSQL(updateQuery);
            cursor.close();
            result=true;
        }
        db.close();
        return result;
    }

    public boolean UpdateCategory(Category c){
        boolean result = false;
        String query = "SELECT * FROM " +TABLE_CATEGORY + " WHERE " +COLUMN_CATEGORYID +" = "+c.getCategoryID();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            String updateQuery = "UPDATE "+TABLE_ITEM
                    + " SET " + COLUMN_NAME + " = \"" + c.Name +"\""
                    + " WHERE " +COLUMN_CATEGORYID +" = "+c.getCategoryID();
            db.execSQL(updateQuery);
            cursor.close();
            result=true;
        }
        db.close();
        return result;
    }
}
