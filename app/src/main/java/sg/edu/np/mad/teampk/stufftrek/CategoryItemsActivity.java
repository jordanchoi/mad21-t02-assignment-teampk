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

public class CategoryItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_items);

        // Receive Intent
        Intent receiveIntent = getIntent();
        String categoryName = receiveIntent.getStringExtra("catName");
        Integer categoryID = receiveIntent.getIntExtra("catID", 0);


        // Toolbar for CategoryItemsActivity
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        ActionBar tb = getSupportActionBar();
        if(tb != null) {
            tb.setHomeAsUpIndicator(R.drawable.ic_back);
            tb.setDisplayHomeAsUpEnabled(true);
            tb.setTitle(categoryName);
        }

        // Construct DBHandler to retrieve DB information.
        DBHandler db = new DBHandler(this, null, null, 1);

        // Call GetAllItemFromCategory to retrieve all items for a certain category
        ArrayList<Item> itemList = db.GetAllItemFromCategory(categoryID);
        // RV for Items
        RecyclerView itemrv = findViewById(R.id.itemsRv);
        ItemsWithPathAdapter adapter = new ItemsWithPathAdapter(this, itemList);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        itemrv.setLayoutManager(lm);
        itemrv.setAdapter(adapter);
        // Handler for no items found
        TextView noItemTV = findViewById(R.id.noItemsTv);
        if (itemList.size() == 0) {
            noItemTV.setVisibility(View.VISIBLE);
        } else {
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