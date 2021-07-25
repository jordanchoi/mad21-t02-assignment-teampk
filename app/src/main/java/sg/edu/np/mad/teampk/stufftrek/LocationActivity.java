// StuffTrek - LocationActivity by Jordan, TeamPK - Ngee Ann Polytechnic.
package sg.edu.np.mad.teampk.stufftrek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class LocationActivity extends AppCompatActivity {
    // Initialization of items within the layout.

        // #TextView
    TextView locationTitle;
    TextView locationDesc;
    TextView noLocationText;

        // #Toolbar
    Toolbar toolbar;
    ActionBar tb;

        // Initialization of items required for (RecyclerView)
    DBHandler db;
    RecyclerView rv;
    LocationAdapter locAdapter;
    ArrayList<Location> locationList;

        // Initialization of items within the inflated BottomSheetDialog Layout
    TextView createTitle;
    EditText createField;
    TextView errorMsgText;
    ImageButton dialogCancelBtn;
    Button dialogAddBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        // Toolbar/Actionbar Codes - Finding and assigning the toolbar by ID. Set it to the ActionBar of this activity.
        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        tb = getSupportActionBar();
        if (tb != null) {
            tb.setHomeAsUpIndicator(R.drawable.ic_back);
            tb.setDisplayHomeAsUpEnabled(true);
            tb.setTitle("Manage Location");
        }

        // Finding and assigning the respective items in the layout with their ids.
        locationTitle = findViewById(R.id.locationPageTitleTv);
        locationDesc = findViewById(R.id.locationPageDescTv);
        noLocationText = findViewById(R.id.noLocationTv);
            // #RecyclerView
        rv = findViewById(R.id.locationRv);

        // Construct DBHandler to retrieve DB information and call GetAllLocation() to retrieve ALL locations.
        db = new DBHandler(this, null, null, 1);
        locationList = db.GetAllLocation();
        db.close();


        // Initializing Adapter, LinearLayoutManager required for RecyclerView and set it to the RV.
        locAdapter = new LocationAdapter(this, locationList);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(locAdapter);

        checkEmpty();
    }

    // Required for ActionBar and handling of menu items
        // Inflate Add Button Menu (R.menu.actionbar_menu_add) to the ActionBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu_add, menu);
        return true;
    }
        // Switch case to handle selection of each menu items.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            // 'Back' is selected. Finish the current activity and return to stack.
            case android.R.id.home:
                this.finish();
                break;
            // Add Button is selected, creates new BottomSheetDialog to allow users to create a new Location
            case R.id.mAdd:
                BottomSheetDialog dialog = new BottomSheetDialog(LocationActivity.this, R.style.BottomSheetStyle);
                dialog.setContentView(R.layout.dialog_create);

                // Get the respective items in the BottomSheetDialog layout.
                createTitle = dialog.findViewById(R.id.dialogTitleTV);
                createField = dialog.findViewById(R.id.dialogFieldET);
                dialogCancelBtn = dialog.findViewById(R.id.dialogCancelBtn);
                dialogAddBtn = dialog.findViewById(R.id.dialogAddBtn);

                // Set the respective texts of the items in the view
                createTitle.setText(R.string.location_input);
                dialogAddBtn.setText(R.string.location_create);

                dialogCancelBtn.setOnClickListener(view -> dialog.cancel());

                dialogAddBtn.setOnClickListener(view -> {
                    String locationName = createField.getText().toString();

                    if(locationName.length() == 0)
                    {
                        errorMsgText = dialog.findViewById(R.id.errorMsgTV);
                        if (errorMsgText != null)
                        {
                            errorMsgText.setText(R.string.location_create_error);
                            errorMsgText.setVisibility(View.VISIBLE);
                        }
                    }
                    else
                    {
                        if (locationList.size() == 0)
                        {
                            noLocationText.setVisibility(View.GONE);
                        }

                        Location loc = new Location(locationName);
                        loc.setLocationID(db.AddLocation(loc));
                        db.close();
                        locationList.add(loc);
                        locAdapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                });

                Window window = dialog.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void checkEmpty() {
        if (locationList.size() == 0)
        {
            noLocationText.setText(R.string.no_location);
            noLocationText.setVisibility(View.VISIBLE);
        }
        else
        {
            noLocationText.setVisibility(View.GONE);
        }
    }

    // Other unused activity state methods.
    @Override
    protected void onResume() {
        super.onResume();
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
    protected void onRestart() {
        super.onRestart();
    }
}