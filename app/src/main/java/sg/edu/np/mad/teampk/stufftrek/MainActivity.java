// StuffTrek - MainActivity by Jordan, TeamPK - Ngee Ann Polytechnic.
package sg.edu.np.mad.teampk.stufftrek;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    // Initialization of items within the layout.
    TextView appNameTitle;
    TextView subText;
    TextView creditsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Finding and assigning the respective items in the layout with their ids.
        appNameTitle = findViewById(R.id.firebase_stuffTrekTV);
        subText = findViewById(R.id.sloganTV);
        creditsText = findViewById(R.id.creditsTV);

        // Setting the texts for the respective TextView within the layout.
        appNameTitle.setText(R.string.app_name);
        subText.setText(R.string.app_slogan);
        creditsText.setText("A project by TeamPK - Ngee Ann Polytechnic\n For our MAD Assignment");

        runTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        runTimer();
    }

    // Timer method to MenuActivity Intent after 2 seconds (2000ms)
    private void runTimer()
    {
        Timer endSplash = new Timer();
        endSplash.schedule(new TimerTask(){
            public void run(){
                Intent menuActivity = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(menuActivity);
            }
        }, 2000);
    }

    // Other Unused Activity State Methods
    @Override
    protected void onRestart() {
        super.onRestart();
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
}