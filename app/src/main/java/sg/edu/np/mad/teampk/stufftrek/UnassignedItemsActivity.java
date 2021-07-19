package sg.edu.np.mad.teampk.stufftrek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class UnassignedItemsActivity extends AppCompatActivity {

    TextView unassignedTitle;
    TextView unassignedDesc;
    TextView unassignedItemsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // Receive Intent
        Intent receiveIntent = getIntent();

        // Toolbar for LocationActivity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        ActionBar tb = getSupportActionBar();
        tb.setHomeAsUpIndicator(R.drawable.ic_back);
        tb.setDisplayHomeAsUpEnabled(true);
        tb.setTitle("Unassigned Items");

        // Get the widgets in the activity by id.
        unassignedTitle = findViewById(R.id.sharedPageTitleTV);
        unassignedDesc = findViewById(R.id.sharedPageDescTV);
        unassignedItemsText = findViewById(R.id.sharedComponentTV);

        // Set the texts of the widgets
        unassignedTitle.setText("Unassigned Items");
        unassignedDesc.setText("These items are not assigned to a location as their containers or locations has been deleted.");
        unassignedItemsText.setText("Your Unassigned Items");
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