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

    ActionBar actionBar;
    View view;
    Toolbar parent;
    ImageButton backBtn;
    ImageButton rightBtn;
    TextView title;

    TextView unassignedTitle;
    TextView unassignedDesc;
    TextView unassignedItemsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // Receive Intent
        Intent receiveIntent = getIntent();

        // Action Bar Customization
        actionBar = getSupportActionBar();
        // actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7CCBCE")));
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);
        view = getSupportActionBar().getCustomView();

        // Remove the space at the start and end of the customized action bar
        parent =(Toolbar) view.getParent();
        parent.setPadding(0,0,0,0); //for tab otherwise give space in tab
        parent.setContentInsetsAbsolute(0,0);

        // Widgets in the ActionBar
        backBtn = view.findViewById(R.id.backBtn);
        rightBtn = view.findViewById(R.id.rightBtn);
        title = view.findViewById(R.id.abTitleTV);

        // Set Title in the Actionbar
        title.setText("Unassigned Items");

        // Hide the right button as it is not required in this activity. Users not allowed to create items from this activity.
        rightBtn.setVisibility(View.GONE);

        // OnClickListener for buttons in the actionbar
        backBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V)
            {
                finish();
            }
        });

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