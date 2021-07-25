package sg.edu.np.mad.teampk.stufftrek;

import androidx.annotation.NonNull;
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

public class ItemsActivity extends AppCompatActivity {

    // Context Information
    int containerID;
    int containerCatId;
    int roomId;
    int locationId;

    TextView noItemTV;
    // Database Variables
    DBHandler db = null;

    // Required for RecyclerView
    ItemsWithPathAdapter adapter = null;
    RecyclerView itemrv;

    ArrayList<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        // Receive Intent
        Intent receiveIntent = getIntent();
        String ContainerName = receiveIntent.getStringExtra("ContainerName");
        containerID = receiveIntent.getIntExtra("ContainerID", -1);
        containerCatId = receiveIntent.getIntExtra("ContainerCatID", -1);
        roomId = receiveIntent.getIntExtra("RoomID", -1);
        locationId = receiveIntent.getIntExtra("LocationID", -1);

        // Toolbar for LocationActivity
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        ActionBar tb = getSupportActionBar();
        tb.setHomeAsUpIndicator(R.drawable.ic_back);
        tb.setDisplayHomeAsUpEnabled(true);
        tb.setTitle(ContainerName);

        // Construct DBHandler to retrieve DB information.
        db = new DBHandler(this, null, null, 1);

        // Call GetAllItemFromContainer to retrieve all items in container
        itemList = db.GetAllItemFromContainer(containerID);
        // RV for Items
        itemrv = findViewById(R.id.itemsRv);
        adapter = new ItemsWithPathAdapter(this, itemList);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        itemrv.setLayoutManager(lm);
        itemrv.setAdapter(adapter);
        // Handler for no items found
        noItemTV = findViewById(R.id.noItemsTv);
        checkEmptyI();
    }
    // Check empty to check if any Location exists and perform the necessary output depending on the conditions
    public void checkEmptyI() {
        if (itemList.size() == 0)
        {
            noItemTV.setText("You have no items created");
            noItemTV.setVisibility(View.VISIBLE);
        }
        else
        {
            noItemTV.setVisibility(View.GONE);

        }
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        itemList = db.GetAllItemFromContainer(containerID);
        adapter.allItemsList = itemList;
        adapter.notifyDataSetChanged();
        checkEmptyI();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    // Inflate Menu for LocationActivity into the ActionBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu_add, menu);
        return true;
    }

    // Actionbar Menu Items
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            // Add Button is selected, intent to createitemactivity to allow users to create a new Item
            case R.id.mAdd:
                Intent createItemActivity = new Intent(ItemsActivity.this, CreateItemActivity.class);
                Bundle containerInformation = new Bundle();
                containerInformation.putInt("ContainerID", containerID);
                containerInformation.putInt("ContainerCatID", containerCatId);
                containerInformation.putInt("LocationID", locationId);
                containerInformation.putInt("RoomID", roomId);
                createItemActivity.putExtras(containerInformation);
                startActivity(createItemActivity);
        }
        return super.onOptionsItemSelected(item);
    }

}