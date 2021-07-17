package sg.edu.np.mad.teampk.stufftrek;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RoomActivity extends ActionBarActivity {
    TextView itemsTitle;
    TextView containersTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        // Receive Intent
        Intent receiveIntent = getIntent();
        int roomId = receiveIntent.getIntExtra("RoomId", -1);
        String roomName = receiveIntent.getStringExtra("RoomName");

        // Set Title in the Actionbar
        ActionBarActivity.abTitle.setText(roomName);
        // Change the right button of the action bar.
        rightBtn.setImageResource(R.drawable.ic_more); // for future usage

        // Respective texts within the activity
        itemsTitle = findViewById(R.id.itemsTitleTV);
        containersTitle = findViewById(R.id.containerTitleTV);
        itemsTitle.setText("Items");
        containersTitle.setText("Containers");

        // Construct DBHandler to retrieve DB information.
        DBHandler db = new DBHandler(this, null, null, 1);

        // Containers RecyclerView

        // Items RecyclerView

        // Call DBHandler method to retrieve all items within the room
        ArrayList<Item> roomItemsList = db.GetAllItemFromRoom(roomId);

        RecyclerView rv = findViewById(R.id.itemsRV);
        ItemsWithPathAdapter itemsAdapter = new ItemsWithPathAdapter(this, roomItemsList);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(itemsAdapter);





    }
}