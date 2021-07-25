package sg.edu.np.mad.teampk.stufftrek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class AllItemsActivity extends AppCompatActivity {

    // Initialization of items within the layout.
    // (Toolbar)
    Toolbar toolbar;
    ActionBar tb;

    // (TextView)
    TextView itemsTitle;
    TextView itemsDesc;
    TextView allItemsText;
    TextView noItemsText;

    // Initialization of objects required for RecyclerView, Adapter and Database
    ArrayList<Item> allItemsList;
    DBHandler db = null;
    RecyclerView rv;
    ItemsWithPathAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_page);

        // Toolbar/Actionbar Codes - Finding and assigning the toolbar by ID. Set it to the ActionBar of this activity.
        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        tb = getSupportActionBar();
        if (tb != null) {
            tb.setHomeAsUpIndicator(R.drawable.ic_back);
            tb.setDisplayHomeAsUpEnabled(true);
            tb.setTitle(R.string.all_items);
        }

        // Finding and assigning the respective items in the layout with their ids.
            // (TextView)
        itemsTitle = findViewById(R.id.sharedPageTitleTv);
        itemsDesc = findViewById(R.id.sharedPageDescTv);
        allItemsText = findViewById(R.id.sharedComponentTv);
        noItemsText = findViewById(R.id.sharedNoItemsTv);
            // (RecyclerView)
        rv = findViewById(R.id.sharedRv);

        // Set the texts of the respective (TextView)
        itemsTitle.setText(R.string.all_items);
        itemsDesc.setText("List and details of all created items.");
        allItemsText.setText("Your Items");

        // Construct DBHandler to retrieve DB information and call GetAllItems() to get the list of all items, assigns to allItemsList..
        db = new DBHandler(this, null, null, 1);
        allItemsList = db.GetAllItem();
        db.close();

        // Initializing Adapter, LinearLayoutManager required for RecyclerView and set it to the RV.
        itemsAdapter = new ItemsWithPathAdapter(this, allItemsList);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(itemsAdapter);

        // Calls checkEmpty() to check if any Location exists and perform the necessary output depending on the conditions
        checkEmpty();
    }

    // Method to handle ActionBar Items Selection
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Check empty to check if any Location exists and perform the necessary output depending on the conditions
    public void checkEmpty() {
        if (allItemsList.size() == 0)
        {
            noItemsText.setText(R.string.no_item);
            noItemsText.setVisibility(View.VISIBLE);
        }
        else
        {
            noItemsText.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        allItemsList = db.GetAllItem();
        db.close();
        itemsAdapter.allItemsList = allItemsList;
        itemsAdapter.notifyDataSetChanged();
        checkEmpty();
    }

    // Other unused activity state methods.
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
    protected void onRestart() {
        super.onRestart();
    }
}