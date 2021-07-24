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
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class AllItemsActivity extends AppCompatActivity {
    TextView itemsTitle;
    TextView itemsDesc;
    TextView allItemsText;

    TextView noItemsText;

    ArrayList<Item> allItemsList;
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
        tb.setTitle("All Items");

        // Get the widgets in the activity by id.
        itemsTitle = findViewById(R.id.sharedPageTitleTV);
        itemsDesc = findViewById(R.id.sharedPageDescTV);
        allItemsText = findViewById(R.id.sharedComponentTV);
        noItemsText = findViewById(R.id.sharedNoItemsTV);
        // Set the texts of the widgets
        itemsTitle.setText("All Items");
        itemsDesc.setText("List and detail of all added items");
        allItemsText.setText("Your Items");

        // Construct DBHandler to retrieve DB information.
        DBHandler db = new DBHandler(this, null, null, 1);
        // Call GetAllLocation() from DBHandler to retrieve ALL locations.
        allItemsList = db.GetAllItem();

        RecyclerView rv = findViewById(R.id.sharedRv);
        ItemsWithPathAdapter itemsAdapter = new ItemsWithPathAdapter(this, allItemsList);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(itemsAdapter);

        if (allItemsList.size() == 0)
        {
            noItemsText.setText("You have no items created");
        }
        else
        {
            noItemsText.setVisibility(View.GONE);
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