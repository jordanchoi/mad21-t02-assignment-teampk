package sg.edu.np.mad.teampk.stufftrek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class SettingsActivity extends AppCompatActivity {
    StorageReference storageRef;
    FirebaseStorage storage;
    StorageReference backupRef;
    //Auth
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    Boolean loggedIn;
    //Button
    Button signOutBtn;
    Button backupBtn;
    Button loadBackupBtn;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //Auth
        mAuth=FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if(currentUser==null){
            loggedIn=false;
        }else{
            loggedIn=true;
            //Firebase cloud storage
                    storage = FirebaseStorage.getInstance();
            storageRef = storage.getReference();
            backupRef = storageRef.child("user/"+currentUser.getUid()+"/backup.db");
        }
        //buttons
        signOutBtn = findViewById(R.id.signOutBtn);
        backupBtn=findViewById(R.id.backupBtn);
        loadBackupBtn=findViewById(R.id.loadBackupBtn);
        //alert dialog for not logged in
        builder = new AlertDialog.Builder(this);
        //Setting message manually and performing action on button click
        builder.setMessage("Not logged in, would you like to log in?")
                .setTitle("Error")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        Intent i = new Intent(SettingsActivity.this,FirebaseSignInActivity.class);
                        startActivity(i);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();

                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();

        //Button handlers
        signOutBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V)
            {
                if(loggedIn){
                    signOut();
                    Intent loginActivity = new Intent(SettingsActivity.this, FirebaseSignInActivity.class);
                    startActivity(loginActivity);
                }else{
                    alert.show();
                }

            }
        });
        backupBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V)
            {
                if(loggedIn){
                    Uri file = Uri.fromFile(new File("/data/data/sg.edu.np.mad.teampk.stufftrek/databases/stufftrekDB.db"));
                    UploadTask uploadTask = backupRef.putFile(file);

                    // Register observers to listen for when the download is done or if it fails
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getApplicationContext(),"Firebase backup fail",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                            Toast.makeText(getApplicationContext(),"Firebase backup success",Toast.LENGTH_SHORT).show();

                        }
                    });
                }else{
                    alert.show();
                }

            }
        });
        loadBackupBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V)
            {
                if(loggedIn){
                    File localFile = new File("/data/data/sg.edu.np.mad.teampk.stufftrek/databases/stufftrekDB.db");


                    backupRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(),"Firebase load backup success",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getApplicationContext(),"Firebase load backup fail",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    alert.show();
                }

            }
        });
    }
    public void signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
        // [END auth_fui_signout]
    }

}