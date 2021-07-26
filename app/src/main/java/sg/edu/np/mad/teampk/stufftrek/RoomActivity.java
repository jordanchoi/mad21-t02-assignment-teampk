// StuffTrek - RoomActivity by Jordan, TeamPK - Ngee Ann Polytechnic.
// Modified by Dong En for Edit & Delete of Container, Container Category and Items

package sg.edu.np.mad.teampk.stufftrek;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class RoomActivity extends AppCompatActivity {
    // Initialization of variables required for Camera Functionality & Storage of Captured Image
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    String picturePath = null;

    // Initialization of items within the layout.
    // #TextView
    TextView itemsTitle;
    TextView containersTitle;
    TextView noContainersText;
    TextView noItemsText;
    // #Others
    ImageView roomImage;
    RecyclerView rv;
    RecyclerView ccRv;
    // #Toolbar
    Toolbar toolbar;
    ActionBar tb;


    // Initialization of items within the BottomSheetDialog Layout
    TextView createTitle;
    EditText createField;
    TextView errorMsgText;
    ImageButton dialogCancelBtn;
    Button dialogAddBtn;

    // Initialization of items within the inflated BottomSheetDialog Layout
    DBHandler db = null;
    ContainersCategoryAdapter ccAdapter = null;
    ItemsWithPathAdapter itemsAdapter = null;
    ArrayList<ContainerCategory> containerCategoriesList;
    ArrayList<Item> roomItemsList;

    // Context Information
    Room currentRoom = null;
    String roomName;
    int roomId;
    int locationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        // Receive Intent
        Intent receiveIntent = getIntent();
        roomId = receiveIntent.getIntExtra("RoomID", -1);
        locationId = receiveIntent.getIntExtra("LocationID", -1);
        roomName = receiveIntent.getStringExtra("RoomName");

        // Toolbar/Actionbar Codes - Finding and assigning the toolbar by ID. Set it to the ActionBar of this activity.
        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        tb = getSupportActionBar();
        if (tb != null)
        {
            tb.setHomeAsUpIndicator(R.drawable.ic_back);
            tb.setDisplayHomeAsUpEnabled(true);
            tb.setTitle(roomName);
        }

        // Finding and assigning the respective items in the layout with their ids.
        roomImage = findViewById(R.id.roomIv);
        itemsTitle = findViewById(R.id.itemsTitleTv);
        containersTitle = findViewById(R.id.containerTitleTv);
        noContainersText = findViewById(R.id.noContainersCatTv);
        noItemsText = findViewById(R.id.noRoomItemTv);
        ccRv = findViewById(R.id.containersRv);
        rv = findViewById(R.id.itemsRv);

        // Set the Title Texts within the Activity
        itemsTitle.setText(R.string.items);
        containersTitle.setText(R.string.containers);

        // Construct DBHandler to retrieve DB information.
        db = new DBHandler(this, null, null, 1);

        // Get current room information from Database & set image if picture exists.
        currentRoom = db.GetRoomWithID(roomId);
        if (currentRoom.Picture != null)
        {
            roomImage.setScaleType(ImageView.ScaleType.FIT_XY);
            roomImage.setImageBitmap(BitmapFactory.decodeFile(currentRoom.Picture));
        }

        // RecyclerView for Container Category, get all container categories within the room from database.
        containerCategoriesList = db.GetAllContainerCategoryFromRoom(roomId);
        ccAdapter = new ContainersCategoryAdapter(this, containerCategoriesList, locationId, roomId);
        LinearLayoutManager ccLm = new LinearLayoutManager(this);
        ccRv.setLayoutManager(ccLm);
        ccRv.setAdapter(ccAdapter);

        // RecyclerView for Items. Get all items within the room from database.
        roomItemsList = db.GetAllItemFromRoom(roomId);
        itemsAdapter = new ItemsWithPathAdapter(this, roomItemsList);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(itemsAdapter);

        db.close();
        checkEmptyCC();
        checkEmptyI();

        // onClickListener for roomImage ImageView. Call method to check and request for Camera & Storage permission and call method to start camera/select image.
        roomImage.setOnClickListener(view -> {
            if(checkAndRequestPermissions(RoomActivity.this)){
                selectImage(RoomActivity.this);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Call GetAllItemFromRoom(roomId) from DB and reassign to the Adapter's List and Notify Dataset Changed to handle users return from Create/Edit items.
        roomItemsList = db.GetAllItemFromRoom(roomId);
        // Call GetAllContainerCategoryFromRoom(roomId) from DB and reassign to the Adapter's List and Notify Dataset Changed to handle users return from Create/Edit container categories.
        containerCategoriesList = db.GetAllContainerCategoryFromRoom(roomId);
        db.close();

        checkEmptyCC();
        checkEmptyI();

        itemsAdapter.allItemsList = roomItemsList;
        ccAdapter.containerCategoriesList = containerCategoriesList;
        if (ccAdapter.ca != null)
        {
            ccAdapter.ca.notifyDataSetChanged();
        }
        ccAdapter.notifyDataSetChanged();
        itemsAdapter.notifyDataSetChanged();


    }

    // Check empty to check if any container categories exists and perform the necessary output depending on the conditions
    public void checkEmptyCC() {
        if (containerCategoriesList.size() == 0)
        {
            noContainersText.setVisibility(View.VISIBLE);
        }
        else
        {
            noContainersText.setVisibility(View.GONE);
        }
    }

    // Check empty to check if any items exists and perform the necessary output depending on the conditions
    public void checkEmptyI() {
        if (roomItemsList.size() == 0)
        {
            noItemsText.setVisibility(View.VISIBLE);

        }
        else
        {
            noItemsText.setVisibility(View.GONE);

        }
    }

    // Inflate overflow options menu to ActionBar
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    // Handles respective selection of ActionBar menu items.
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Selected Back - Finish current activity and return to stack.
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        // Selected to Add New Container Category
            case R.id.addContainerCat:
                BottomSheetDialog dialog = new BottomSheetDialog(RoomActivity.this, R.style.BottomSheetStyle);
                dialog.setContentView(R.layout.dialog_create);

                // Get the respective items in the BottomSheetDialog layout.
                createTitle = dialog.findViewById(R.id.dialogTitleTV);
                createField = dialog.findViewById(R.id.dialogFieldET);
                dialogCancelBtn = dialog.findViewById(R.id.dialogCancelBtn);
                dialogAddBtn = dialog.findViewById(R.id.dialogAddBtn);
                errorMsgText = dialog.findViewById(R.id.errorMsgTV);

                // Set the respective texts of the items in the view
                createTitle.setText(R.string.container_cat_input);
                dialogAddBtn.setText(R.string.container_cat_create_title);
                createField.setHint(R.string.container_cat_prompt);

                dialogCancelBtn.setOnClickListener(view -> dialog.cancel());

                dialogAddBtn.setOnClickListener(view -> {
                    String newContainerCat = createField.getText().toString();

                    if(newContainerCat.length() == 0)
                    {
                        errorMsgText.setText(R.string.container_cat_input_error);
                        errorMsgText.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        if (containerCategoriesList.size() == 0)
                        {
                            noContainersText.setVisibility(View.GONE);
                        }
                        // Create new ContainerCategory object and adds it to the Database
                        ContainerCategory cc = new ContainerCategory(newContainerCat, roomId);
                        cc.setContainerCategoryID(db.AddContainerCategory(cc));
                        db.close();

                        // Adds to the containerCategoriesList and notify DataSetChanged to the adapter to display.
                        containerCategoriesList.add(cc);
                        ccAdapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                });

                Window window = dialog.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialog.show();
                break;

            case R.id.addContainer:
                // Selected to Add Container - Create new intent and pack required information into bundle along with the intent to create container.
                Intent createContainerActivity = new Intent(RoomActivity.this, CreateContainerActivity.class);

                Bundle roomInformation = new Bundle();
                roomInformation.putInt("RoomID", roomId);
                roomInformation.putString("RoomName", roomName);
                createContainerActivity.putExtras(roomInformation);

                startActivity(createContainerActivity);
                break;

            case R.id.addItem:
                // Selected to Add Item - Create new intent and pack required information into bundle along with the intent to create item.
                Intent createItemActivity = new Intent(RoomActivity.this, CreateItemActivity.class);

                Bundle roomInformationToItem = new Bundle();
                roomInformationToItem.putInt("RoomID", roomId);
                roomInformationToItem.putString("RoomName", roomName);
                roomInformationToItem.putInt("LocationID", locationId);
                createItemActivity.putExtras(roomInformationToItem);

                startActivity(createItemActivity);
                break;
        }
        return (super.onOptionsItemSelected(item));
    }

    // Camera and File Storage Permission
    public static boolean checkAndRequestPermissions(final Activity context) {
        int ExtStorePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (cameraPermission != PackageManager.PERMISSION_GRANTED)
        {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (ExtStorePermission != PackageManager.PERMISSION_GRANTED)
        {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!listPermissionsNeeded.isEmpty())
        {
            ActivityCompat.requestPermissions(context, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    // Handle permissions result
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_ID_MULTIPLE_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(RoomActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Camera permissions is required to capture container's image. Please enable them if you wish to use this feature.", Toast.LENGTH_SHORT).show();
            } else if (ContextCompat.checkSelfPermission(RoomActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Storage permissions is required to save container's image. Please enable them if you wish to use this feature.", Toast.LENGTH_SHORT).show();
            } else {
                selectImage(RoomActivity.this);
            }
        }
    }

    // Method to allow user to select existing image from album or capture new image from camera.
    private void selectImage(Context context){
        final CharSequence[] optionsMenu = {"Take Photo", "Choose from Gallery", "Exit" }; // create a menuOption Array
        // create a dialog for showing the optionsMenu
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // set the items in builder
        builder.setItems(optionsMenu, (dialogInterface, i) -> {
            if(optionsMenu[i].equals("Take Photo")){
                // Open the camera and get the photo
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);
            }
            else if(optionsMenu[i].equals("Choose from Gallery")){
                // choose from  external storage
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);
            }
            else if (optionsMenu[i].equals("Exit")) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    // Handle the captured image or inserted existing image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        // saves the image and assign the path to picturePath, sets to the room object and imageview. Updates in DB.
                        picturePath = saveImage(selectedImage);
                        currentRoom.Picture = picturePath;
                        roomImage.setImageBitmap(BitmapFactory.decodeFile(currentRoom.Picture));
                        db.UpdateRoomPhoto(roomId, picturePath);
                        roomImage.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null)
                        {
                            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                picturePath = cursor.getString(columnIndex);
                                // Sets to the room object and imageview. Updates in DB.
                                roomImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                db.UpdateRoomPhoto(roomId, picturePath);
                                cursor.close();
                                roomImage.setScaleType(ImageView.ScaleType.FIT_XY);
                            }
                        }
                    }
                    break;
            }
        }
    }

    // Method to save image, returns the saved path
    public String saveImage(Bitmap image)
    {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("images" + File.pathSeparator + "rooms", Context.MODE_PRIVATE);
        String filename = roomName + "_" + roomId + "_" + System.currentTimeMillis() + ".jpg";

        if (!directory.exists()) {
            directory.mkdirs();
        }

        File myPath = new File(directory, filename);

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(myPath);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (Exception e) {
            Log.e("SAVE_IMAGE", e.getMessage(), e);
        }

        return myPath.getAbsolutePath();
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
    protected void onRestart() {
        super.onRestart();
    }
}