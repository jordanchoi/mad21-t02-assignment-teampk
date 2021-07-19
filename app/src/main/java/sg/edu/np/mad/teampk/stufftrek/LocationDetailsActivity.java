package sg.edu.np.mad.teampk.stufftrek;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class LocationDetailsActivity extends AppCompatActivity {
    //Dialog Widgets
    TextView createTitle;
    EditText createField;
    TextView errorMsgText;
    ImageButton dialogCancelBtn;
    Button dialogAddBtn;
    ArrayList<Room> roomList;
    ArrayList<Item> locationItemList;
    TextView noItemTV;
    DBHandler db;
    RoomAdapter roomAdapter;
    ItemsWithPathAdapter itemsAdapter;
    TextView noRoomTV;
    Integer LocationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);



        // Receive Intent
        Intent receiveIntent = getIntent();
        String LocationName = receiveIntent.getStringExtra("LocationName");
        LocationID = receiveIntent.getIntExtra("LocationID",0);

        // Toolbar for LocationActivity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        ActionBar tb = getSupportActionBar();
        tb.setHomeAsUpIndicator(R.drawable.ic_back);
        tb.setDisplayHomeAsUpEnabled(true);
        tb.setTitle(LocationName);

        // Set Title for location
        TextView locationDetailsTitle = findViewById(R.id.locationDetailsPageTitleTV);
        locationDetailsTitle.setText(LocationName);

        // Construct DBHandler to retrieve DB information.
        db = new DBHandler(this, null, null, 1);

        // Call GetAllRoomFromLocation to retrieve all rooms in location
        roomList = db.GetAllRoomFromLocation(LocationID);
        // RV for rooms
        RecyclerView roomrv = findViewById(R.id.roomRV);
        roomAdapter = new RoomAdapter(this,roomList);
        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        roomrv.setLayoutManager(lm);
        roomrv.setAdapter(roomAdapter);
        // Handler for no items found
        noRoomTV = findViewById(R.id.noRoomTV);
        if (roomList.size() == 0)
        {
            noRoomTV.setText("You have no rooms created");
        }
        else
        {
            noRoomTV.setVisibility(View.GONE);
        }

        // Call GetAllItemFromLocation() from DBHandler to retrieve ALL items in location.
        locationItemList = db.GetAllItemFromLocation(LocationID);

        // RV for items
        RecyclerView itemrv = findViewById(R.id.itemRV);
        itemsAdapter = new ItemsWithPathAdapter(this, locationItemList);
        LinearLayoutManager lm2 = new LinearLayoutManager(this);
        itemrv.setLayoutManager(lm2);
        itemrv.setAdapter(itemsAdapter);


        // Handler for no items found
        noItemTV = findViewById(R.id.noItemTV);
        if (locationItemList.size() == 0)
        {
            noItemTV.setText("You have no items created");
        }
        else
        {
            noItemTV.setVisibility(View.GONE);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.location_details_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;

            case R.id.mAddItem:
                // code here if add Item. Intent to create item activity + bundle locationId, roomId, etc?
                BottomSheetDialog dialog = new BottomSheetDialog(LocationDetailsActivity.this);
                dialog.setContentView(R.layout.dialog_create);

                // Get the respective items in the view
                createTitle = dialog.findViewById(R.id.dialogTitleTV);
                createField = dialog.findViewById(R.id.dialogFieldET);
                dialogCancelBtn = dialog.findViewById(R.id.dialogCancelBtn);
                dialogAddBtn = dialog.findViewById(R.id.dialogAddBtn);

                // Set the respective texts of the items in the view
                createTitle.setText("Item Name: ");
                dialogAddBtn.setText("Add Item");

                dialogCancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

                dialogAddBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String itemName = createField.getText().toString();

                        if(itemName.length() == 0)
                        {
                            errorMsgText = dialog.findViewById(R.id.errorMsgTV);
                            errorMsgText.setText("Item name cannot be empty!");
                            errorMsgText.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            if (locationItemList.size() == 0)
                            {
                                noItemTV.setVisibility(View.GONE);
                            }

                            Item i = new Item(itemName,1,null);
                            i.setLocationID(LocationID);
                            i.setItemID(db.AddItem(i));
                            locationItemList.add(i);
                            itemsAdapter.notifyDataSetChanged();
                            dialog.cancel();
                        }
                    }
                });

                Window window = dialog.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialog.show();
                return true;

            case R.id.mAddRoom:
                // code here if add Room. This one no need intent, just use bottomsheetdialog from category/location
                dialog = new BottomSheetDialog(LocationDetailsActivity.this);
                dialog.setContentView(R.layout.dialog_create);

                // Get the respective items in the view
                createTitle = dialog.findViewById(R.id.dialogTitleTV);
                createField = dialog.findViewById(R.id.dialogFieldET);
                dialogCancelBtn = dialog.findViewById(R.id.dialogCancelBtn);
                dialogAddBtn = dialog.findViewById(R.id.dialogAddBtn);

                // Set the respective texts of the items in the view
                createTitle.setText("Room Name: ");
                dialogAddBtn.setText("Add Room");

                dialogCancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

                dialogAddBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String roomName = createField.getText().toString();

                        if(roomName.length() == 0)
                        {
                            errorMsgText = dialog.findViewById(R.id.errorMsgTV);
                            errorMsgText.setText("Item name cannot be empty!");
                            errorMsgText.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            if (roomList.size() == 0)
                            {
                                noRoomTV.setVisibility(View.GONE);
                            }

                            Room r = new Room(roomName,null,LocationID);
                            r.setRoomID(db.AddRoom(r));
                            roomList.add(r);
                            roomAdapter.notifyDataSetChanged();
                            dialog.cancel();
                        }
                    }
                });

                window = dialog.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialog.show();
                return true;
        }
        return (super.onOptionsItemSelected(item));
    }
}