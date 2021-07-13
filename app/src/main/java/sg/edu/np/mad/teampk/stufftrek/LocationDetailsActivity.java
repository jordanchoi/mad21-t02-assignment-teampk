package sg.edu.np.mad.teampk.stufftrek;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class LocationDetailsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);

        // Receive Intent
        Intent receiveIntent = getIntent();
        String LocationName = receiveIntent.getStringExtra("LocationName");
        Integer LocationID = receiveIntent.getIntExtra("LocationID",0);

        // Set Title in the Actionbar
        ActionBarActivity.abTitle.setText("Manage Rooms");

        rightBtn.setImageResource(R.drawable.ic_more); // for future usage
        ActionBarActivity.rightBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V)
            {
                /* TO REMOVE THE COMMENT WHEN CreateLocationActivity is created.
                Intent createLocationActivity = new Intent(LocationActivity.this, CreateLocationActivity.class);
                startActivity(createLocationActivity);
                */
            }
        });

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
}