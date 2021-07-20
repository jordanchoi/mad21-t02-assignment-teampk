package sg.edu.np.mad.teampk.stufftrek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CreateContainerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // UI Items
    TextView createContainerTitle;
    TextView createContainerDesc;
    ImageButton cameraBtn;
    TextView cameraDesc;
    TextView containerNameText;
    EditText inputContainerName;
    TextView containerCatText;
    Spinner categorySpinner;
    Button createContainerBtn;
    TextView errorMsg;

    // Receive Intent Information
    int roomId;
    String roomName;

    // Database variables
    DBHandler db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_container);

        // Receive Intent
        Intent receiveIntent = getIntent();
        roomId = receiveIntent.getIntExtra("RoomID", -1);
        roomName = receiveIntent.getStringExtra("RoomName");

        // Toolbar for LocationActivity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        ActionBar tb = getSupportActionBar();
        tb.setHomeAsUpIndicator(R.drawable.ic_back);
        tb.setDisplayHomeAsUpEnabled(true);
        tb.setTitle("Create Container");

        // Retrieve the respective UI items by their id.
        createContainerTitle = findViewById(R.id.createContainerTitleTV);
        createContainerDesc = findViewById(R.id.createContainerDescTV);
        cameraBtn = findViewById(R.id.captureBtn);
        cameraDesc = findViewById(R.id.captureDescTV);
        containerNameText = findViewById(R.id.containerNamePromptTV);
        inputContainerName = findViewById(R.id.containerNameET);
        containerCatText = findViewById(R.id.containerCatPromptTV);
        categorySpinner = findViewById(R.id.containerCatSpinner);
        createContainerBtn = findViewById(R.id.createContainerBtn);
        errorMsg = findViewById(R.id.cNameErrorMsgTV);

        // Set the respective texts within the view
        createContainerTitle.setText("Create Container");
        createContainerDesc.setText("Create containers within a room to contain items for better organization.");
        cameraDesc.setText("Add an image of the container for easy reference");
        containerNameText.setText("Container Name");
        inputContainerName.setText("Enter the new container name");
        containerCatText.setText("Container Category");
        createContainerBtn.setText("Create New Container");

        // onclicklistener for EditText to reset the placeholder text - improve UX
        inputContainerName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                inputContainerName.setText("");
            }
        });

        // Initialize DBHandler to retrieve the container categories within the room
        db = new DBHandler(this, null, null, 1);
        ArrayList<ContainerCategory> containerCategoriesList = db.GetAllContainerCategoryFromRoom(roomId);

        // ArrayList for the Spinner
        ArrayList<String> containerCatSpinnerItems = new ArrayList<String>();

        categorySpinner.setOnItemSelectedListener(this);

        // For loop to add all the container categories name within the room to the arraylist
        for (ContainerCategory cc : containerCategoriesList)
        {
            containerCatSpinnerItems.add(cc.Name);
        }

        // Set the adapter for the spinner.
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, containerCatSpinnerItems);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(dataAdapter);

        // OnClickListener for create button
        createContainerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String selectedCat = containerCatSpinnerItems.get(categorySpinner.getSelectedItemPosition());
                Integer containerCatId = null;

                for (ContainerCategory cc : containerCategoriesList)
                {
                    if (selectedCat == cc.Name)
                    {
                        containerCatId = cc.getContainerCategoryID();
                        break;
                    }
                }
                String newContainerName = String.valueOf(inputContainerName.getText());

                if (newContainerName.length() == 0)
                {
                    errorMsg.setText("Container name cannot be empty!");
                    errorMsg.setVisibility(View.VISIBLE);
                }
                else
                {
                    Container newContainer = new Container(newContainerName, null, containerCatId);
                    newContainer.setContainerID(db.AddContainer(newContainer));
                    finish();
                }
            }
        });


    }

    // Actionbar Menu Items
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // methods for spinner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // nothing to be done onItemSelected for spinner presently.
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // nothing to be done onNothingSelected for spinner presently.
    }
}