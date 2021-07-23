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
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

//import static sg.edu.np.mad.teampk.stufftrek.ActionBarActivity.rightBtn;

public class ItemsActivity extends AppCompatActivity {

    int ContainerID;
    int containerCatId;
    int roomId;
    int locationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        // Receive Intent
        Intent receiveIntent = getIntent();
        String ContainerName = receiveIntent.getStringExtra("ContainerName");
        ContainerID = receiveIntent.getIntExtra("ContainerID",-1);
        containerCatId = receiveIntent.getIntExtra("ContainerCatID", -1);
        roomId = receiveIntent.getIntExtra("RoomID", -1);
        locationId = receiveIntent.getIntExtra("LocationID", -1);

        // Toolbar for LocationActivity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        ActionBar tb = getSupportActionBar();
        tb.setHomeAsUpIndicator(R.drawable.ic_back);
        tb.setDisplayHomeAsUpEnabled(true);
        tb.setTitle(ContainerName);

        // Construct DBHandler to retrieve DB information.
        DBHandler db = new DBHandler(this, null, null, 1);

        // Call GetAllItemFromContainer to retrieve all items in container
        ArrayList<Item> itemList = db.GetAllItemFromContainer(ContainerID);
        // RV for Items
        RecyclerView itemrv = findViewById(R.id.itemsRV);
        ItemsWithPathAdapter adapter = new ItemsWithPathAdapter(this,itemList);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        itemrv.setLayoutManager(lm);
        itemrv.setAdapter(adapter);
        // Handler for no items found
        TextView noItemTV = findViewById(R.id.noItemsTV);
        if (itemList.size() == 0)
        {
            noItemTV.setText("You have no items created");
        }
        else
        {
            noItemTV.setVisibility(View.GONE);
        }
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
                containerInformation.putInt("ContainerID", ContainerID);
                containerInformation.putInt("ContainerCatID", containerCatId);
                containerInformation.putInt("LocationID", locationId);
                containerInformation.putInt("RoomID", roomId);
                startActivity(createItemActivity);
        }
        return super.onOptionsItemSelected(item);
    }

}