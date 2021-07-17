package sg.edu.np.mad.teampk.stufftrek;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class RoomActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        // Receive Intent
        Intent receiveIntent = getIntent();
        int roomId = receiveIntent.getIntExtra("RoomId", -1);
        String roomName = receiveIntent.getStringExtra("RoomName");

        // Set Title in the Actionbar
        ActionBarActivity.abTitle.setText(roomName);
        // Change the right button of the action bar.
        rightBtn.setImageResource(R.drawable.ic_more); // for future usage
    }
}