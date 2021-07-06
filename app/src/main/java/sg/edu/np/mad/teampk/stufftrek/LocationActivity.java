package sg.edu.np.mad.teampk.stufftrek;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class LocationActivity extends AppCompatActivity {
    ActionBar actionBar;
    View view;
    Toolbar parent;
    ImageButton backBtn;
    ImageButton rightBtn;
    TextView title;
    TextView locationTitle;
    TextView locationDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

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
        parent = (Toolbar) view.getParent();
        parent.setPadding(0,0,0,0); //for tab otherwise give space in tab
        parent.setContentInsetsAbsolute(0,0);

        // Widgets in the ActionBar
        backBtn = view.findViewById(R.id.backBtn);
        rightBtn = view.findViewById(R.id.rightBtn);
        title = view.findViewById(R.id.abTitleTV);

        // Set Title in the Actionbar
        title.setText("Manage Location");

        // rightBtn.setImageResource(R.drawable.ic_more); // for future usage
        // OnClickListener for buttons in the actionbar
        backBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V)
            {
                finish();
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V)
            {
                /* TO REMOVE THE COMMENT WHEN CreateLocationActivity is created.
                Intent createLocationActivity = new Intent(LocationActivity.this, CreateLocationActivity.class);
                startActivity(createLocationActivity);
                */
            }
        });

        // Get the header widgets of the activity - Title and Description TextView
        locationTitle = findViewById(R.id.locationPageTitleTV);
        locationDesc = findViewById(R.id.locationPageDescTV);

        // Set the texts of the respective header widgets of the activity.
        locationTitle.setText("Your Locations");
        locationDesc.setText("Create and view your locations.\nE.g) Houses, Offices");

        /*
        //testing code
        DBHandler dbh = new DBHandler(this,null,null,1);

        Location l = new Location();
        l.Name="LocationTest";
        l.LocationID = dbh.AddLocation(l);
        System.out.println("Testing");
        System.out.println(l.LocationID);
        TextView text1 = (TextView) findViewById(R.id.textViewTest1);
        TextView txt2 = (TextView) findViewById(R.id.textViewTest2);
        text1.setText(l.Name);
        txt2.setText(""+l.LocationID);

         */
    }
}