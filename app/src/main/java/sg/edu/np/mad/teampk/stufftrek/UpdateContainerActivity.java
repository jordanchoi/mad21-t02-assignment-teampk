package sg.edu.np.mad.teampk.stufftrek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class UpdateContainerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // for camera and file storage
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    String picturePath = null;

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
    int cId;
    int indexCC;
    String roomName;
    Container c;
    Room r;

    // Database variables
    DBHandler db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_container);

        // Receive Intent
        Intent receiveIntent = getIntent();

        cId = receiveIntent.getIntExtra("ContainerId", -1);
        roomId = receiveIntent.getIntExtra("RoomId",-1);
        db = new DBHandler(this, null, null, 1);
        r = db.GetRoomWithID(roomId);
        c = db.GetContainerWithId(cId);
//        //set image
//        cameraBtn.setScaleType(ImageView.ScaleType.FIT_XY);
//        cameraBtn.setImageBitmap(BitmapFactory.decodeFile(c.Picture));

        // Toolbar for LocationActivity
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        // Toolbar Settings
        ActionBar tb = getSupportActionBar();
        tb.setHomeAsUpIndicator(R.drawable.ic_back);
        tb.setDisplayHomeAsUpEnabled(true);
        tb.setTitle("Update Container");

        // Retrieve the respective UI items by their id.
        createContainerTitle = findViewById(R.id.createContainerTitleTv);
        createContainerDesc = findViewById(R.id.createContainerDescTv);
        cameraBtn = findViewById(R.id.captureBtn);
        cameraDesc = findViewById(R.id.captureDescTv);
        containerNameText = findViewById(R.id.containerNamePromptTv);
        inputContainerName = findViewById(R.id.containerNameEt);
        containerCatText = findViewById(R.id.containerCatPromptTv);
        categorySpinner = findViewById(R.id.containerCatSpinner);
        createContainerBtn = findViewById(R.id.createContainerBtn);
        errorMsg = findViewById(R.id.cNameErrorMsgTv);

        // Set the respective texts within the view
        createContainerTitle.setText("Update Container");
        createContainerDesc.setText("This is a container inside your room");
        cameraDesc.setText("Add an image of the container for easy reference");
        inputContainerName.setHint("Enter the new container name");
        inputContainerName.setText(c.Name);
        createContainerBtn.setText("Update Container");

        // Initialize DBHandler to retrieve the container categories within the room

        ArrayList<ContainerCategory> containerCategoriesList = db.GetAllContainerCategoryFromRoom(roomId);

        // ArrayList for the Spinner
        ArrayList<String> containerCatSpinnerItems = new ArrayList<String>();
        containerCatSpinnerItems.add("Please select a container category");

        categorySpinner.setOnItemSelectedListener(this);
        ContainerCategory c1 = db.GetContainerCategoryWithID(c.getContainerCategoryID());

        // For loop to add all the container categories name within the room to the arraylist
        for (ContainerCategory cc : containerCategoriesList)
        {
            containerCatSpinnerItems.add(cc.Name);
            if(cc.getContainerCategoryID()==c1.getContainerCategoryID()){
                indexCC=containerCategoriesList.indexOf(cc);
            }

        }

        // Set the adapter for the spinner.
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, containerCatSpinnerItems);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(dataAdapter);

        //dk y nid plus 1
        categorySpinner.setSelection(indexCC+1);

        // OnClickListener for create button
        createContainerBtn.setOnClickListener(new View.OnClickListener()
        {
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
                    c.Name=newContainerName;
                    c.Picture=picturePath;

                    db.UpdateContainer(c);

                    finish();
                }
            }
        });

        // Triggers the camera
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkAndRequestPermissions(UpdateContainerActivity.this)){
                    selectImage(UpdateContainerActivity.this);
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
            listPermissionsNeeded
                    .add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
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
                if (ContextCompat.checkSelfPermission(UpdateContainerActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Camera permissions is required to capture container's image. Please enable them if you wish to use this feature.", Toast.LENGTH_SHORT).show();
                } else if (ContextCompat.checkSelfPermission(UpdateContainerActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Storage permissions is required to save container's image. Please enable them if you wish to use this feature.", Toast.LENGTH_SHORT).show();
                } else {
                    selectImage(UpdateContainerActivity.this);
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
                        cameraBtn.setImageBitmap(BitmapFactory.decodeFile(picturePath));
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
                                cameraBtn.setImageBitmap(BitmapFactory.decodeFile(picturePath));
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
        File directory = cw.getDir("images" + File.pathSeparator + "containers", Context.MODE_PRIVATE);

        String filename = String.valueOf(inputContainerName.getText());

        if (filename.length() == 0) // container name not set yet.
        {
            filename = roomName + "_" + roomId + "_" + System.currentTimeMillis() + ".jpg";
        }
        else
        {
            filename = roomName + "_" + roomId + "_" + filename + "_" + System.currentTimeMillis() + ".jpg";
        }

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