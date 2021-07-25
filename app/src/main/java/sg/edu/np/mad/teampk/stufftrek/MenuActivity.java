// StuffTrek - MenuActivity by Jordan, TeamPK - Ngee Ann Polytechnic.
// Modified by Dong En for Firebase Authentication

package sg.edu.np.mad.teampk.stufftrek;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    // For Firebase
    private FirebaseAuth mAuth;

    // Initialization of items within the layout.
    TextView appTitle;
    TextView pageDesc;

    Button locationBtn;
    Button searchBtn;
    Button categoryBtn;
    Button itemsBtn;
    Button unassignedBtn;
    Button settingsBtn;

    TextView locationTV;
    TextView searchTV;
    TextView categoryTV;
    TextView itemsTV;
    TextView unassignedTV;
    TextView settingsTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // For Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Finding and assigning the respective items in the layout with their ids.
            // (TextView) - Titles
        appTitle = findViewById(R.id.pageTitleTv);
        pageDesc = findViewById(R.id.pageDescTv);

            // (TextView) - Menu Text
        locationTV = findViewById(R.id.locationTv);
        searchTV = findViewById(R.id.searchTv);
        categoryTV = findViewById(R.id.categoryTv);
        itemsTV = findViewById(R.id.itemTv);
        unassignedTV = findViewById(R.id.unassignedTv);
        settingsTV = findViewById(R.id.settingsTv);

            // (Button) - Menu Items
        locationBtn = findViewById(R.id.locationBtn);
        searchBtn = findViewById(R.id.searchBtn);
        categoryBtn = findViewById(R.id.categoryBtn);
        itemsBtn = findViewById(R.id.itemsBtn);
        unassignedBtn = findViewById(R.id.unassignedBtn);
        settingsBtn = findViewById(R.id.settingsBtn);

        // Set the texts of the respective (TextView)
            // (TextView) - Titles
        appTitle.setText(R.string.app_name);
        pageDesc.setText(R.string.app_slogan);

            // (TextView) - Menu Items
        locationTV.setText(R.string.location);
        searchTV.setText(R.string.search);
        categoryTV.setText(R.string.category);
        itemsTV.setText(R.string.all_items);
        unassignedTV.setText(R.string.unassigned_items);
        settingsTV.setText(R.string.settings);

        // Set onClickListener all of the buttons, Handled in the Switch Case within onClick method.
        locationBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        categoryBtn.setOnClickListener(this);
        itemsBtn.setOnClickListener(this);
        unassignedBtn.setOnClickListener(this);
        settingsBtn.setOnClickListener(this);
    }

    // onStart method by Dong En for Firebase Authentication
    @Override
    protected void onStart() {
        super.onStart();

    }

    // Switch Case within the method handles each respective onClick for the buttons to navigate to the respective intent.
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.locationBtn:
                Intent locationActivity = new Intent(MenuActivity.this, LocationActivity.class);
                startActivity(locationActivity);
                break;
            case R.id.searchBtn:
                Intent searchActivity = new Intent(MenuActivity.this, SearchActivity.class);
                startActivity(searchActivity);
                break;
            case R.id.categoryBtn:
                Intent categoryActivity = new Intent(MenuActivity.this, CategoryActivity.class);
                startActivity(categoryActivity);
                break;
            case R.id.itemsBtn:
                Intent allItemsActivity = new Intent(MenuActivity.this, AllItemsActivity.class);
                startActivity(allItemsActivity);
                break;
            case R.id.unassignedBtn:
                Intent unassignedItemsActivity = new Intent(MenuActivity.this, UnassignedItemsActivity.class);
                startActivity(unassignedItemsActivity);
                break;
            case R.id.settingsBtn:
                Intent settingsActivity = new Intent(MenuActivity.this, SettingsActivity.class);
                startActivity(settingsActivity);
                break;
        }
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
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}