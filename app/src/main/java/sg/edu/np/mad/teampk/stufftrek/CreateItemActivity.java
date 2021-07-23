package sg.edu.np.mad.teampk.stufftrek;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import android.os.AsyncTask;
import android.os.Build;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class CreateItemActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Initialize variables for Camera & File Storage
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    String picturePath = null;

    // Initialize variables of items within the view
    TextView createItemTitle;
    TextView createItemDesc;
    ImageButton cameraBtn;
    TextView cameraDesc;
    TextView itemNameText;
    EditText inputItemName;
    TextView nameErrorMsg;
    TextView itemQtyText;
    EditText inputItemQty;
    TextView qtyErrorMsg;
    TextView itemCatText;
    Spinner inputItemCatSpinner;
    Button createItemBtn;

    // Initialize DBHandler for usage outside of onCreate
    DBHandler db = null;

    // Initialize intent information for item creation
    Integer locationId = null;
    Integer roomId = null;
    Integer containerCatId = null;
    Integer containerId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        // Receive Intent
        Intent receiveIntent = getIntent();
        locationId = receiveIntent.getIntExtra("LocationID", -1);
        roomId = receiveIntent.getIntExtra("RoomID", -1);
        containerCatId = receiveIntent.getIntExtra("ContainerCatID", -1);
        containerId = receiveIntent.getIntExtra("ContainerID", -1);

        // Toolbar for LocationActivity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        // Toolbar Settings
        ActionBar tb = getSupportActionBar();
        tb.setHomeAsUpIndicator(R.drawable.ic_back);
        tb.setDisplayHomeAsUpEnabled(true);
        tb.setTitle("Create Item");

        // Retrieving the respective UI items by their ids.
        createItemTitle = findViewById(R.id.createItemTitleTV);
        createItemDesc = findViewById(R.id.createItemDescTV);
        cameraBtn = findViewById(R.id.captureItemBtn);
        cameraDesc = findViewById(R.id.captureItemDescTV);
        itemNameText = findViewById(R.id.itemNamePromptTV);
        inputItemName = findViewById(R.id.itemNameET);
        nameErrorMsg = findViewById(R.id.iNameErrorMsgTV);
        itemQtyText = findViewById(R.id.itemQtyPromptTV);
        inputItemQty = findViewById(R.id.itemQtyET);
        qtyErrorMsg = findViewById(R.id.iQtyErrorMsg);
        itemCatText = findViewById(R.id.itemCatPromptTV);
        inputItemCatSpinner = findViewById(R.id.itemCatSpinner);
        createItemBtn = findViewById(R.id.createItemBtn);

        // Set the texts of the respective UI items.
        createItemTitle.setText("Create Item");
        createItemDesc.setText("Create physical items and record its storage location. Never lose your items again.");
        cameraDesc.setText("Add an image of your item. Let us figure out what it is and automatically fill its name.");
        itemNameText.setText("Item Name");
        inputItemName.setHint("Enter new item name");
        itemQtyText.setText("Quantity");
        inputItemQty.setText("1");
        itemCatText.setText("Item Category");
        createItemBtn.setText("Create New Item");


        // Construct DBHandler for database data retrieval
        db = new DBHandler(this, null, null, 1);

        // Codes for the item category spinners
        // Get all the items categories from DBHandler
        ArrayList<Category> categoriesList = db.GetAllCategories();

        // Initialize a string arraylist to load into the spinner
        ArrayList<String> itemCatSpinnerItems = new ArrayList<String>();

        // Set onItemSelectedListener for the category spinner
        inputItemCatSpinner.setOnItemSelectedListener(this);

        // For loop to add all the item categories name to the spinner arraylist (conversion)
        for (Category cat : categoriesList)
        {
            itemCatSpinnerItems.add(cat.Name);
        }

        // Set the adapter for the spinner.
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, itemCatSpinnerItems);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputItemCatSpinner.setAdapter(dataAdapter);

        // Set onClickListener for cameraBtn to capture image or select existing image, request permission if not provided yet
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkAndRequestPermissions(CreateItemActivity.this)){
                    selectImage(CreateItemActivity.this);
                }
            }
        });

        createItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validate inputs
                String itemName = inputItemName.getText().toString();
                Integer itemQty = Integer.parseInt(String.valueOf(inputItemQty));
                if (itemName.length() == 0)
                {
                    nameErrorMsg.setText("Name cannot be empty!");
                    nameErrorMsg.setVisibility(View.VISIBLE);
                }
                else if (itemQty < 1)
                {
                    qtyErrorMsg.setText("Item quantity cannot be less than 1");
                    qtyErrorMsg.setVisibility(View.VISIBLE);
                }
                else
                {
                    String selectedCat = itemCatSpinnerItems.get(inputItemCatSpinner.getSelectedItemPosition());
                    Integer catId = null;

                    for (Category c : categoriesList)
                    {
                        if (selectedCat == c.Name)
                        {
                            catId = c.getCategoryID();
                        }
                    }


                    Item newItem = new Item(itemName, itemQty, picturePath);
                    // change foreign keys to null if it is -1
                    // locationId cannot be -1

                    if (roomId == -1)
                    {
                        roomId = null;
                    }
                    if (containerCatId == -1)
                    {
                        containerCatId = null;
                    }
                    if (containerId == -1)
                    {
                        containerId = null;
                    }

                    try {
                        newItem.setLocationID(locationId);
                        newItem.setContainerCategoryID(containerCatId);
                        newItem.setContainerID(containerId);
                        newItem.setRoomID(roomId);
                        newItem.setCategoryID(catId);
                        newItem.setItemID(db.AddItem(newItem));
                        finish();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    // Handling Actionbar Menu Items
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // Methods for Item Category Spinners
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    // Methods for Camera and File Storage
    // Check & Obtain the necessary permissions from user
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

    // Handling permission results
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
                if (ContextCompat.checkSelfPermission(CreateItemActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)  // camera permission not granted
                {
                    Toast.makeText(getApplicationContext(), "Camera permissions is required to capture container's image. Please enable them if you wish to use this feature.", Toast.LENGTH_SHORT).show();
                }
                else if (ContextCompat.checkSelfPermission(CreateItemActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) // external storage permission not granted
                {
                    Toast.makeText(getApplicationContext(), "Storage permissions is required to save container's image. Please enable them if you wish to use this feature.", Toast.LENGTH_SHORT).show();
                }
                else {
                    // both permissions granted, call method to display alertdialog for user selection to capture image or select existing image
                    selectImage(CreateItemActivity.this);
                }
                break;
        }
    }

    // method to let the user to capture image from camera or select existing image from gallery through alertdialog
    private void selectImage(Context context) {
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
                        PostImageToImaggaAsync postImageToImaggaAsync = new PostImageToImaggaAsync(picturePath);
                        postImageToImaggaAsync.execute();
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
        File directory = cw.getDir("images" + File.pathSeparator + "items", Context.MODE_PRIVATE);

        String filename = String.valueOf(inputItemName.getText());

        if (filename.length() == 0) // container name not set yet.
        {
            filename = "Item_" + System.currentTimeMillis() + ".jpg";
        }
        else
        {
            filename = "Item_" + filename + "_" + System.currentTimeMillis() + ".jpg";
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String postImageToImagga(String filepath) throws Exception {
        String credentialsToEncode = "acc_e1ed1ff35bf92a5" + ":" + "3b047e6e431794b65fc01306cfdf7c72";
        String basicAuth = Base64.getEncoder().encodeToString(credentialsToEncode.getBytes(StandardCharsets.UTF_8));

        File fileToUpload = new File(filepath);

        String endpoint = "/tags";

        String crlf = "\r\n";
        String twoHyphens = "--";
        String boundary =  "Image Upload";

        URL urlObject = new URL("https://api.imagga.com/v2" + endpoint);
        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
        connection.setRequestProperty("Authorization", "Basic " + basicAuth);
        connection.setUseCaches(false);
        connection.setDoOutput(true);

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("Cache-Control", "no-cache");
        connection.setRequestProperty(
                "Content-Type", "multipart/form-data;boundary=" + boundary);

        DataOutputStream request = new DataOutputStream(connection.getOutputStream());

        request.writeBytes(twoHyphens + boundary + crlf);
        request.writeBytes("Content-Disposition: form-data; name=\"image\";filename=\"" + fileToUpload.getName() + "\"" + crlf);
        request.writeBytes(crlf);


        InputStream inputStream = new FileInputStream(fileToUpload);
        int bytesRead;
        byte[] dataBuffer = new byte[1024];
        while ((bytesRead = inputStream.read(dataBuffer)) != -1) {
            request.write(dataBuffer, 0, bytesRead);
        }

        request.writeBytes(crlf);
        request.writeBytes(twoHyphens + boundary + twoHyphens + crlf);
        request.flush();
        request.close();

        InputStream responseStream = new BufferedInputStream(connection.getInputStream());

        BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));

        String line = "";
        StringBuilder stringBuilder = new StringBuilder();

        while ((line = responseStreamReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        responseStreamReader.close();

        String response = stringBuilder.toString();

        responseStream.close();
        connection.disconnect();
        return response;
    }

    public class PostImageToImaggaAsync extends AsyncTask<Void, Void, Void> {
        String filePath;
        String response;
        String bestMatch;

        public PostImageToImaggaAsync(String filePath) {
            this.filePath = filePath;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                response = postImageToImagga(filePath);
                JSONObject jsonObj = new JSONObject(response);
                JSONArray tagsArr = jsonObj.getJSONObject("result").getJSONArray("tags");
                JSONObject firstMatch = tagsArr.getJSONObject(0);
                bestMatch = firstMatch.getJSONObject("tag").getString("en");
                Log.i("imagga", response);
            }
            catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            if (bestMatch != "")
            {
                bestMatch = bestMatch.substring(0,1).toUpperCase() + bestMatch.substring(1);
            }

            inputItemName.setText(bestMatch);
        }
    }
}