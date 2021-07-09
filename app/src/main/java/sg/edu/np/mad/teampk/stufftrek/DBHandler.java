package sg.edu.np.mad.teampk.stufftrek;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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
                "(" + COLUMN_ROOMID + " INTEGER NOT NULL PRIMARY KEY," + COLUMN_NAME+ " TEXT NOT NULL,"+COLUMN_PICTURE+"TEXT,"
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
                "(" + COLUMN_ITEMID + " INTEGER NOT NULL PRIMARY KEY," + COLUMN_NAME+ " TEXT NOT NULL,"+COLUMN_PICTURE+" TEXT,"+COLUMN_QUANTITY+" INTEGER,"
                +COLUMN_CONTAINERID+" INTEGER NOT NULL,"+
                COLUMN_CATEGORYID+" INTEGER NOT NULL,"+
                COLUMN_ROOMID+" INTEGER NOT NULL,"+
                COLUMN_LOCATIONID+" INTEGER NOT NULL,"+
                COLUMN_CONTAINERCATEGORYID+" INTEGER NOT NULL"+
                ", FOREIGN KEY("+COLUMN_CONTAINERCATEGORYID+") REFERENCES "+TABLE_CONTAINERCATEGORY+"("+COLUMN_CONTAINERCATEGORYID+")"
                +", FOREIGN KEY("+COLUMN_ROOMID+") REFERENCES "+TABLE_ROOM+"("+COLUMN_ROOMID+")"
                +", FOREIGN KEY("+COLUMN_LOCATIONID+") REFERENCES "+TABLE_LOCATION+"("+COLUMN_LOCATIONID+")"
                +", FOREIGN KEY("+COLUMN_CONTAINERID+") REFERENCES "+TABLE_CONTAINER+"("+COLUMN_CONTAINERID+")"
                +", FOREIGN KEY("+COLUMN_CATEGORYID+") REFERENCES "+TABLE_CATEGORY+"("+COLUMN_CATEGORYID+")"
                +")";
        db.execSQL(CREATE_ITEM_TABLE);


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
        values.put(COLUMN_NAME,c.Name);
        values.put(COLUMN_CONTAINERCATEGORYID,c.getContainerCategoryID());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_ROOM,null,values);
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
        db.insert(TABLE_ROOM,null,values);
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
        values.put(COLUMN_CONTAINERID,i.getContainerID());
        values.put(COLUMN_CONTAINERCATEGORYID,i.getContainerCategoryID());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_ROOM,null,values);
        String query = "SELECT last_insert_rowid();";
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        i.setItemID(Integer.parseInt(cursor.getString(0)));
        db.close();
        return i.getItemID();
    }

    //READ
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
        return containerArrayList;
    }

    public ArrayList<Category> GetAllCategory(){
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
        return categoryArrayList;
    }

    public ArrayList<Item> GetAllItemFromLocation(Integer LocationID){
        ArrayList<Item> itemArrayList = new ArrayList<Item>();
        String query = "SELECT * FROM " +TABLE_ITEM+ " WHERE "+COLUMN_LOCATIONID+" = "+LocationID;
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
                itemArrayList.add(i);
                cursor.moveToNext();
            }
        }
        return itemArrayList;
    }

    public ArrayList<Item> GetAllItemFromRoom(Integer RoomID){
        ArrayList<Item> itemArrayList = new ArrayList<Item>();
        String query = "SELECT * FROM " +TABLE_ITEM+ " WHERE "+COLUMN_ROOMID+" = "+RoomID;
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
                itemArrayList.add(i);
                cursor.moveToNext();
            }
        }
        return itemArrayList;
    }

    public ArrayList<Item> GetAllItemFromContainer(Integer ContainerID){
        ArrayList<Item> itemArrayList = new ArrayList<Item>();
        String query = "SELECT * FROM " +TABLE_ITEM+ " WHERE "+COLUMN_CONTAINERID+" = "+ContainerID;
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
                itemArrayList.add(i);
                cursor.moveToNext();
            }
        }
        return itemArrayList;
    }

    public ArrayList<Item> GetAllItemFromCategory(Integer CategoryID){
        ArrayList<Item> itemArrayList = new ArrayList<Item>();
        String query = "SELECT * FROM " +TABLE_ITEM+ " WHERE "+COLUMN_CATEGORYID+" = "+CategoryID;
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
                itemArrayList.add(i);
                cursor.moveToNext();
            }
        }
        return itemArrayList;
    }

    public ArrayList<Item> GetAllItem(){
        ArrayList<Item> itemArrayList = new ArrayList<Item>();
        String query = "SELECT * FROM " +TABLE_ITEM;
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
                itemArrayList.add(i);
                cursor.moveToNext();
            }
        }
        return itemArrayList;
    }

    public ArrayList<Item> GetAllUnassingedItem(){
        ArrayList<Item> itemArrayList = new ArrayList<Item>();
        String query = "SELECT * FROM " +TABLE_ITEM+ " WHERE "+COLUMN_LOCATIONID+" IS NULL AND " +COLUMN_CONTAINERID+" IS NULL AND "+COLUMN_ROOMID+" IS NULL";
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
                itemArrayList.add(i);
                cursor.moveToNext();
            }
        }
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

    public boolean DeleteItem(Integer ItemID){
        boolean result = false;
        String query = "SELECT * FROM " +TABLE_ITEM + " WHERE " +COLUMN_ITEMID +" = "+ItemID;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            String deleteQuery = "DELETE FROM " +TABLE_ITEM +" WHERE "  +COLUMN_ITEMID +" = "+ItemID;
            Cursor cursor2 = db.rawQuery(deleteQuery,null);
            cursor.close();
            cursor2.close();
            result=true;
        }
        db.close();
        return result;
    }
}
