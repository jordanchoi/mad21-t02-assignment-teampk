package sg.edu.np.mad.teampk.stufftrek;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
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


//        // Set Title in the Actionbar
//        ActionBarActivity.abTitle.setText("Unassigned Items");

//        // Hide the right button as it is not required in this activity. Users not allowed to create items from this activity.
//        rightBtn.setVisibility(View.GONE);

        // Get the widgets in the activity by id.
        unassignedTitle = findViewById(R.id.sharedPageTitleTV);
        unassignedDesc = findViewById(R.id.sharedPageDescTV);
        unassignedItemsText = findViewById(R.id.sharedComponentTV);

        // Set the texts of the widgets
        unassignedTitle.setText("Unassigned Items");
        unassignedDesc.setText("These items are not assigned to a location as their containers or locations has been deleted.");
        unassignedItemsText.setText("Your Unassigned Items");
    }
}