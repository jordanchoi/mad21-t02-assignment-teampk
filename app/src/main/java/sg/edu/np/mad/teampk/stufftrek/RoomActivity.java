package sg.edu.np.mad.teampk.stufftrek;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RoomActivity extends ActionBarActivity {
    TextView itemsTitle;
    TextView containersTitle;
    TextView noContainersText;
    TextView noItemsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        // Receive Intent
        Intent receiveIntent = getIntent();
        int roomId = receiveIntent.getIntExtra("RoomID", -1);
        String roomName = receiveIntent.getStringExtra("RoomName");

        // Set Title in the Actionbar
        ActionBarActivity.abTitle.setText(roomName);
        // Change the right button of the action bar.
        rightBtn.setImageResource(R.drawable.ic_more); // for future usage

        // Respective texts within the activity
        itemsTitle = findViewById(R.id.itemsTitleTV);
        containersTitle = findViewById(R.id.containerTitleTV);
        noContainersText = findViewById(R.id.noContainersText);
        noItemsText = findViewById(R.id.noItemsText);
        itemsTitle.setText("Items");
        containersTitle.setText("Containers");

        // Construct DBHandler to retrieve DB information.
        DBHandler db = new DBHandler(this, null, null, 1);

        // Containers RecyclerView
        // Call DBHandler method to retrieve all container categories within the room
        ArrayList<ContainerCategory> containerCategoriesList = db.GetAllContainerCategoryFromRoom(roomId);

        RecyclerView ccRv = findViewById(R.id.containersRV);
        ContainersCategoryAdapter ccAdapter = new ContainersCategoryAdapter(this, containerCategoriesList);
        LinearLayoutManager ccLm = new LinearLayoutManager(this);
        ccRv.setLayoutManager(ccLm);
        ccRv.setAdapter(ccAdapter);

        if (containerCategoriesList.size() == 0)
        {
            noContainersText.setText("No containers has been created in this room");
        }
        else
        {
            noContainersText.setVisibility(View.GONE);
        }


        // Items RecyclerView

        // Call DBHandler method to retrieve all items within the room
        ArrayList<Item> roomItemsList = db.GetAllItemFromRoom(roomId);

        RecyclerView rv = findViewById(R.id.itemsRV);
        ItemsWithPathAdapter itemsAdapter = new ItemsWithPathAdapter(this, roomItemsList);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(itemsAdapter);

        if (roomItemsList.size() == 0)
        {
            noItemsText.setText("No items are stored in this room");
        }
        else
        {
            noItemsText.setVisibility(View.GONE);
        }





    }
}