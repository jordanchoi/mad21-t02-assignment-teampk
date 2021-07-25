package sg.edu.np.mad.teampk.stufftrek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class UnassignedItemsActivity extends AppCompatActivity {

    TextView unassignedTitle;
    TextView unassignedDesc;
    TextView unassignedItemsText;
    TextView noItemTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // Receive Intent
        Intent receiveIntent = getIntent();

        // Toolbar for LocationActivity
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        ActionBar tb = getSupportActionBar();
        tb.setHomeAsUpIndicator(R.drawable.ic_back);
        tb.setDisplayHomeAsUpEnabled(true);
        tb.setTitle("Unassigned Items");

        // Get the widgets in the activity by id.
        unassignedTitle = findViewById(R.id.sharedPageTitleTv);
        unassignedDesc = findViewById(R.id.sharedPageDescTv);
        unassignedItemsText = findViewById(R.id.sharedComponentTv);

        // Set the texts of the widgets
        unassignedTitle.setText("Unassigned Items");
        unassignedDesc.setText("These items are not assigned to a location as their containers or locations has been deleted.");
        unassignedItemsText.setText("Your Unassigned Items");

        // Construct DBHandler to retrieve DB information.
        DBHandler db = new DBHandler(this, null, null, 1);

        // Call GetAllItemFromContainer to retrieve all items in container
        ArrayList<Item> itemList = db.GetAllUnassignedItem();
        // RV for Items
        RecyclerView sharedRv = findViewById(R.id.sharedRv);
        ItemsWithPathAdapter adapter = new ItemsWithPathAdapter(this,itemList);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        sharedRv.setLayoutManager(lm);
        sharedRv.setAdapter(adapter);
        // Handler for no items found
        noItemTV = findViewById(R.id.sharedNoItemsTv);
        if (itemList.size() == 0)
        {
            noItemTV.setText("You have no items unassigned");
        }
        else
        {
            noItemTV.setVisibility(View.GONE);
        }
    }





    // ActionBar items
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}