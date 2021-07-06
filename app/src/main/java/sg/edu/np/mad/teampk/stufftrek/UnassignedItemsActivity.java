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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unassigned_items);

        // Receive Intent
        Intent receiveIntent = getIntent();

        // Action Bar Customization
        ActionBar actionBar = getSupportActionBar();
        // actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7CCBCE")));
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);
        View view = getSupportActionBar().getCustomView();

        // Remove the space at the start and end of the customized action bar
        Toolbar parent =(Toolbar) view.getParent();
        parent.setPadding(0,0,0,0); //for tab otherwise give space in tab
        parent.setContentInsetsAbsolute(0,0);

        // Widgets in the ActionBar
        ImageButton backBtn = view.findViewById(R.id.backBtn);
        ImageButton rightBtn = view.findViewById(R.id.rightBtn);
        TextView title = view.findViewById(R.id.abTitleTV);

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
    }
}