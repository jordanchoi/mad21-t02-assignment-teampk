package sg.edu.np.mad.teampk.stufftrek;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    TextView appNameTitle;
    TextView subText;
    TextView creditsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide the Action Bar
        getSupportActionBar().hide();

        // Find the respective widgets in the view by id.
        appNameTitle = findViewById(R.id.stuffTrekTV);
        subText = findViewById(R.id.sloganTV);
        creditsText = findViewById(R.id.creditsTV);

        // Set the dynamic texts.
        appNameTitle.setText("StuffTrek");
        subText.setText("Track and organize your stuff efficiently.");
        creditsText.setText("A project by TeamPK\n For our MAD Assignment");

        runTimer();
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
        runTimer();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

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
}