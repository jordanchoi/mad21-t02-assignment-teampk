package sg.edu.np.mad.teampk.stufftrek;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int AddLocation(Location l){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,l.Name);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_LOCATION,null,values);
        String query = "SELECT last_insert_rowid();";
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        l.LocationID=Integer.parseInt(cursor.getString(0));
        db.close();
        return l.LocationID;
    }
}
