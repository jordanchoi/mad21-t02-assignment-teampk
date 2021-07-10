package sg.edu.np.mad.teampk.stufftrek;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class LocationActivity extends ActionBarActivity {
    TextView locationTitle;
    TextView locationDesc;
    ArrayList<Location> locationList;
    TextView noLocationText;

    TextView createTitle;
    EditText createField;
    TextView errorMsgText;
    ImageButton dialogCancelBtn;
    Button dialogAddBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        // Receive Intent
        Intent receiveIntent = getIntent();

        // Set Title in the Actionbar
        ActionBarActivity.abTitle.setText("Manage Location");

        // Construct DBHandler to retrieve DB information.
        DBHandler db = new DBHandler(this, null, null, 1);
        // Call GetAllLocation() from DBHandler to retrieve ALL locations.
        locationList = db.GetAllLocation();

        // Get the header widgets of the activity - Title and Description TextView
        locationTitle = findViewById(R.id.locationPageTitleTV);
        locationDesc = findViewById(R.id.locationPageDescTV);
        noLocationText = findViewById(R.id.noLocationTV);

        RecyclerView rv = findViewById(R.id.locationRv);
        LocationAdapter locAdapter = new LocationAdapter(this, locationList);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(locAdapter);

        // Set the texts of the respective header widgets of the activity.
        locationTitle.setText("Your Locations");
        locationDesc.setText("Create and view your locations.\nE.g) Houses, Offices");

        if (locationList.size() == 0)
        {
            noLocationText.setText("You do not have any locations created.");
            noLocationText.setVisibility(View.VISIBLE);
        }
        else
        {
            noLocationText.setVisibility(View.GONE);
        }

        ActionBarActivity.rightBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                BottomSheetDialog dialog = new BottomSheetDialog(LocationActivity.this);
                dialog.setContentView(LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_create, findViewById(R.id.content), false));

                // Get the respective items in the view
                createTitle = dialog.findViewById(R.id.dialogTitleTV);
                createField = dialog.findViewById(R.id.dialogFieldET);
                dialogCancelBtn = dialog.findViewById(R.id.dialogCancelBtn);
                dialogAddBtn = dialog.findViewById(R.id.dialogAddBtn);

                // Set the respective texts of the items in the view
                createTitle.setText("Location Name: ");
                dialogAddBtn.setText("Add Location");

                dialogCancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

                dialogAddBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String locationName = createField.getText().toString();

                        if(locationName.length() == 0)
                        {
                            errorMsgText = dialog.findViewById(R.id.errorMsgTV);
                            errorMsgText.setText("Location name cannot be empty!");
                            errorMsgText.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            if (locationList.size() == 0)
                            {
                                noLocationText.setVisibility(View.GONE);
                            }

                            Location loc = new Location(locationName);
                            loc.setLocationID(db.AddLocation(loc));
                            locationList.add(loc);
                            locAdapter.notifyDataSetChanged();
                            dialog.cancel();
                        }
                    }
                });

                Window window = dialog.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialog.show();
            }
        });
    }

}