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
import android.widget.TextView;

import java.util.ArrayList;

public class LocationDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);

        // Receive Intent
        Intent receiveIntent = getIntent();
        String LocationName = receiveIntent.getStringExtra("LocationName");
        Integer LocationID = receiveIntent.getIntExtra("LocationID",0);

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
        DBHandler db = new DBHandler(this, null, null, 1);

        // Call GetAllRoomFromLocation to retrieve all rooms in location
        ArrayList<Room> roomList = db.GetAllRoomFromLocation(LocationID);
        // RV for rooms
        RecyclerView roomrv = findViewById(R.id.roomRV);
        RoomAdapter adapter = new RoomAdapter(this,roomList);
        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        roomrv.setLayoutManager(lm);
        roomrv.setAdapter(adapter);
        // Handler for no items found
        TextView noRoomTV = findViewById(R.id.noRoomTV);
        if (roomList.size() == 0)
        {
            noRoomTV.setText("You have no rooms created");
        }
        else
        {
            noRoomTV.setVisibility(View.GONE);
        }

        // Call GetAllItemFromLocation() from DBHandler to retrieve ALL items in location.
        ArrayList<Item> locationItemList = db.GetAllItemFromLocation(LocationID);

        // RV for items
        RecyclerView itemrv = findViewById(R.id.itemRV);
        ItemsWithPathAdapter itemsAdapter = new ItemsWithPathAdapter(this, locationItemList);
        LinearLayoutManager lm2 = new LinearLayoutManager(this);
        itemrv.setLayoutManager(lm2);
        itemrv.setAdapter(itemsAdapter);


        // Handler for no items found
        TextView noItemTV = findViewById(R.id.noItemTV);
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
                return true;

            case R.id.mAddRoom:
                // code here if add Room. This one no need intent, just use bottomsheetdialog from category/location
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }
}