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

        TextView locationDetailsTitle = findViewById(R.id.locationDetailsPageTitleTV);
        locationDetailsTitle.setText(LocationName);
        RecyclerView rv = findViewById(R.id.roomRV);
        DBHandler db = new DBHandler(this, null, null, 1);
        ArrayList<Room> roomList = db.GetAllRoomFromLocation(1);
        RoomAdapter adapter = new RoomAdapter(this,roomList);
        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);
    }
}