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
import android.content.DialogInterface;
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
    // for camera and file storage
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    String picturePath = null;

    TextView itemsTitle;
    TextView containersTitle;
    TextView noContainersText;
    TextView noItemsText;
    ImageView roomImage;

    TextView createTitle;
    EditText createField;
    TextView errorMsgText;
    ImageButton dialogCancelBtn;
    Button dialogAddBtn;

    DBHandler db = null;
    ContainersCategoryAdapter ccAdapter = null;
    ItemsWithPathAdapter itemsAdapter = null;
    ArrayList<ContainerCategory> containerCategoriesList;

    Room currentRoom = null;
    int roomId;
    int locationId;
    String roomName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        // Receive Intent
        Intent receiveIntent = getIntent();
        roomId = receiveIntent.getIntExtra("RoomID", -1);
        locationId = receiveIntent.getIntExtra("LocationID", -1);
        roomName = receiveIntent.getStringExtra("RoomName");

        // Toolbar for LocationActivity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        ActionBar tb = getSupportActionBar();
        tb.setHomeAsUpIndicator(R.drawable.ic_back);
        tb.setDisplayHomeAsUpEnabled(true);
        tb.setTitle(roomName);

        // Respective items within the view
        itemsTitle = findViewById(R.id.itemsTitleTV);
        containersTitle = findViewById(R.id.containerTitleTV);
        noContainersText = findViewById(R.id.noContainersText);
        noItemsText = findViewById(R.id.noItemsText);
        roomImage = findViewById(R.id.roomIV);
        itemsTitle.setText("Items");
        containersTitle.setText("Containers");

        // Construct DBHandler to retrieve DB information.
        db = new DBHandler(this, null, null, 1);

        // load image from db if exist
        currentRoom = db.GetRoomWithID(roomId);

        if (currentRoom.Picture != null)
        {
            roomImage.setScaleType(ImageView.ScaleType.FIT_XY);
            roomImage.setImageBitmap(BitmapFactory.decodeFile(currentRoom.Picture));
        }

        // Containers RecyclerView
        // Call DBHandler method to retrieve all container categories within the room
        containerCategoriesList = db.GetAllContainerCategoryFromRoom(roomId);

        RecyclerView ccRv = findViewById(R.id.containersRV);
        ccAdapter = new ContainersCategoryAdapter(this, containerCategoriesList, locationId, roomId);
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
        itemsAdapter = new ItemsWithPathAdapter(this, roomItemsList);
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

        // ImageView triggers camera setonclicklistener
        roomImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkAndRequestPermissions(RoomActivity.this)){
                    selectImage(RoomActivity.this);
                }
            }
        });
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
        ccAdapter.notifyDataSetChanged();
        itemsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
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
                Intent createContainerActivity = new Intent(RoomActivity.this, CreateContainerActivity.class);
                Bundle roomInformation = new Bundle();

                roomInformation.putInt("RoomID", roomId);
                roomInformation.putString("RoomName", roomName);

                createContainerActivity.putExtras(roomInformation);
                startActivity(createContainerActivity);

                return (true);

            case R.id.addItem:
                // codes here to intent;
                Intent createItemActivity = new Intent(RoomActivity.this, CreateItemActivity.class);
                Bundle roomInformationToItem = new Bundle();
                roomInformationToItem.putInt("RoomID", roomId);
                roomInformationToItem.putString("RoomName", roomName);
                roomInformationToItem.putInt("LocationID", locationId);
                createItemActivity.putExtras(roomInformationToItem);
                startActivity(createItemActivity);
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    // Camera and File Storage Permission
    // function to check permission
    public static boolean checkAndRequestPermissions(final Activity context) {
        int WExtstorePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (cameraPermission != PackageManager.PERMISSION_GRANTED)
        {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (WExtstorePermission != PackageManager.PERMISSION_GRANTED)
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

    // Handled permission Result
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
                if (ContextCompat.checkSelfPermission(RoomActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Camera permissions is required to capture container's image. Please enable them if you wish to use this feature.", Toast.LENGTH_SHORT).show();
                } else if (ContextCompat.checkSelfPermission(RoomActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Storage permissions is required to save container's image. Please enable them if you wish to use this feature.", Toast.LENGTH_SHORT).show();
                } else {
                    selectImage(RoomActivity.this);
                }
                break;
        }
    }

    // function to let's the user to choose image from camera or gallery
    private void selectImage(Context context){
        final CharSequence[] optionsMenu = {"Take Photo", "Choose from Gallery", "Exit" }; // create a menuOption Array
        // create a dialog for showing the optionsMenu
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // set the items in builder
        builder.setItems(optionsMenu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(optionsMenu[i].equals("Take Photo")){
                    // Open the camera and get the photo
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                }
                else if(optionsMenu[i].equals("Choose from Gallery")){
                    // choose from  external storage
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);
                }
                else if (optionsMenu[i].equals("Exit")) {
                    dialogInterface.cancel();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        picturePath = saveImage(selectedImage);
                        currentRoom.Picture = picturePath;
                        roomImage.setImageBitmap(BitmapFactory.decodeFile(currentRoom.Picture));
                        db.UpdateRoomPhoto(roomId, picturePath);
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
                                roomImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }
                    }
                    break;
            }
        }
    }

    public String saveImage(Bitmap image)
    {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("images" + File.pathSeparator + "rooms", Context.MODE_PRIVATE);

        String filename = roomName + "_" + roomId + "_" + System.currentTimeMillis() + ".jpg";

        if (!directory.exists()) {
            directory.mkdirs();
        }

        File mypath = new File(directory, filename);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (Exception e) {
            Log.e("SAVE_IMAGE", e.getMessage(), e);
        }

        return mypath.getAbsolutePath();
    }


}