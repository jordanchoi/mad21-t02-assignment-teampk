package sg.edu.np.mad.teampk.stufftrek;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class RoomActivity extends AppCompatActivity {
    TextView itemsTitle;
    TextView containersTitle;
    TextView noContainersText;
    TextView noItemsText;

    TextView createTitle;
    EditText createField;
    TextView errorMsgText;
    ImageButton dialogCancelBtn;
    Button dialogAddBtn;

    DBHandler db = null;
    ContainersCategoryAdapter ccAdapter = null;
    ArrayList<ContainerCategory> containerCategoriesList;

    int roomId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        // Receive Intent
        Intent receiveIntent = getIntent();
        roomId = receiveIntent.getIntExtra("RoomID", -1);
        String roomName = receiveIntent.getStringExtra("RoomName");

        // Toolbar for LocationActivity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        ActionBar tb = getSupportActionBar();
        tb.setHomeAsUpIndicator(R.drawable.ic_back);
        tb.setDisplayHomeAsUpEnabled(true);
        tb.setTitle(roomName);

        // Respective texts within the activity
        itemsTitle = findViewById(R.id.itemsTitleTV);
        containersTitle = findViewById(R.id.containerTitleTV);
        noContainersText = findViewById(R.id.noContainersText);
        noItemsText = findViewById(R.id.noItemsText);
        itemsTitle.setText("Items");
        containersTitle.setText("Containers");

        // Construct DBHandler to retrieve DB information.
        db = new DBHandler(this, null, null, 1);

        // Containers RecyclerView
        // Call DBHandler method to retrieve all container categories within the room
        containerCategoriesList = db.GetAllContainerCategoryFromRoom(roomId);

        RecyclerView ccRv = findViewById(R.id.containersRV);
        ccAdapter = new ContainersCategoryAdapter(this, containerCategoriesList);
        LinearLayoutManager ccLm = new LinearLayoutManager(this);
        ccRv.setLayoutManager(ccLm);
        ccRv.setAdapter(ccAdapter);

        if (containerCategoriesList.size() == 0)
        {
            noContainersText.setText("No containers has been created in this room");
        }
        else
        {
            noContainersText.setVisibility(View.GONE);
        }


        // Items RecyclerView

        // Call DBHandler method to retrieve all items within the room
        ArrayList<Item> roomItemsList = db.GetAllItemFromRoom(roomId);

        RecyclerView rv = findViewById(R.id.itemsRV);
        ItemsWithPathAdapter itemsAdapter = new ItemsWithPathAdapter(this, roomItemsList);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(itemsAdapter);

        if (roomItemsList.size() == 0)
        {
            noItemsText.setText("No items are stored in this room");
        }
        else
        {
            noItemsText.setVisibility(View.GONE);
        }
    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;

            case R.id.addContainerCat:
                // codes here - bottomsheetdialog
                BottomSheetDialog dialog = new BottomSheetDialog(RoomActivity.this, R.style.BottomSheetStyle);
                dialog.setContentView(R.layout.dialog_create);

                // Get the respective items in the view
                createTitle = dialog.findViewById(R.id.dialogTitleTV);
                createField = dialog.findViewById(R.id.dialogFieldET);
                dialogCancelBtn = dialog.findViewById(R.id.dialogCancelBtn);
                dialogAddBtn = dialog.findViewById(R.id.dialogAddBtn);

                // Set the respective texts of the items in the view
                createTitle.setText("Container Category Name: ");
                dialogAddBtn.setText("Create New Container Category");

                dialogCancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

                dialogAddBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String newContainerCat = createField.getText().toString();

                        if(newContainerCat.length() == 0)
                        {
                            errorMsgText = dialog.findViewById(R.id.errorMsgTV);
                            errorMsgText.setText("Container Category's name cannot be empty!");
                            errorMsgText.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            if (containerCategoriesList.size() == 0)
                            {
                                noContainersText.setVisibility(View.GONE);
                            }

                            ContainerCategory cc = new ContainerCategory(newContainerCat, roomId);
                            cc.setContainerCategoryID(db.AddContainerCategory(cc));
                            containerCategoriesList.add(cc);
                            ccAdapter.notifyDataSetChanged();
                            dialog.cancel();
                        }
                    }
                });

                Window window = dialog.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialog.show();
                return (true);

            case R.id.addContainer:
                // codes here to intent
                return (true);

            case R.id.addItem:
                // codes here to intent;
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }
}