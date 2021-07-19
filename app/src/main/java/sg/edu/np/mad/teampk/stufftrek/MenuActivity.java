package sg.edu.np.mad.teampk.stufftrek;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    // Activity Header
    TextView appTitle;
    TextView pageDesc;

    // Menu Buttons
    Button locationBtn;
    Button searchBtn;
    Button categoryBtn;
    Button itemsBtn;
    Button unassignedBtn;

    // Menu Texts
    TextView locationTV;
    TextView searchTV;
    TextView categoryTV;
    TextView itemsTV;
    TextView unassignTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

//        // Hide the Action Bar
//        getSupportActionBar().hide();

        // Get the activity header by their id
        appTitle = findViewById(R.id.pageTitleTV);
        pageDesc = findViewById(R.id.pageDescTV);

        // Set the respective texts of the header widgets
        appTitle.setText("StuffTrek");
        pageDesc.setText("Record your items' location where you stored them. Find them with ease");

        // Get menu buttons by their id
        locationBtn = findViewById(R.id.locationBtn);
        searchBtn = findViewById(R.id.searchBtn);
        categoryBtn = findViewById(R.id.categoryBtn);
        itemsBtn = findViewById(R.id.itemsBtn);
        unassignedBtn = findViewById(R.id.unassignBtn);

        // Get menu texts by their id
        locationTV = findViewById(R.id.locationTV);
        searchTV = findViewById(R.id.searchTV);
        categoryTV = findViewById(R.id.categoryTV);
        itemsTV = findViewById(R.id.itemTV);
        unassignTV = findViewById(R.id.unassignTV);

        // Set the texts of the respective menu items
        locationTV.setText("Location");
        searchTV.setText("Search");
        categoryTV.setText("Category");
        itemsTV.setText("All Items");
        unassignTV.setText("Unassigned Items");



        // OnClickListener for the menu to move to the respective activities through intent
        locationBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V)
            {
                Intent locationActivity = new Intent(MenuActivity.this, LocationActivity.class);
                startActivity(locationActivity);
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V)
            {
                Intent searchActivity = new Intent(MenuActivity.this, SearchActivity.class);
                startActivity(searchActivity);
            }
        });

        categoryBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V)
            {
                Intent categoryActivity = new Intent(MenuActivity.this, CategoryActivity.class);
                startActivity(categoryActivity);
            }
        });

        itemsBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V)
            {
                Intent allItemsActivity = new Intent(MenuActivity.this, AllItemsActivity.class);
                startActivity(allItemsActivity);
            }
        });

        unassignedBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V)
            {
                Intent unassignedItemsActivity = new Intent(MenuActivity.this, UnassignedItemsActivity.class);
                startActivity(unassignedItemsActivity);
            }
        });
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
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}