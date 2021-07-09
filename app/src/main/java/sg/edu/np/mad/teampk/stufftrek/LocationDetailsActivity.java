package sg.edu.np.mad.teampk.stufftrek;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LocationDetailsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);

        // Receive Intent
        Intent receiveIntent = getIntent();
        String LocationName = receiveIntent.getStringExtra("LocationName");

        // Set Title in the Actionbar
        ActionBarActivity.abTitle.setText(LocationName);

        // rightBtn.setImageResource(R.drawable.ic_more); // for future usage
        ActionBarActivity.rightBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V)
            {
                /* TO REMOVE THE COMMENT WHEN CreateLocationActivity is created.
                Intent createLocationActivity = new Intent(LocationActivity.this, CreateLocationActivity.class);
                startActivity(createLocationActivity);
                */
            }
        });
    }
}