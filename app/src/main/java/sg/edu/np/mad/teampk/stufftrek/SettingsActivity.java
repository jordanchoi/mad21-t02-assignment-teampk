package sg.edu.np.mad.teampk.stufftrek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    //Button
    Button signOutBtn;
    Button backupBtn;
    Button loadBackupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //Auth
        mAuth=FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        //Firebase cloud storage
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        backupRef = storageRef.child("user/"+currentUser.getUid()+"/backup.db");
        //buttons
        signOutBtn = findViewById(R.id.signOutBtn);
        backupBtn=findViewById(R.id.backupBtn);
        loadBackupBtn=findViewById(R.id.loadBackupBtn);
        signOutBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V)
            {
                signOut();
                Intent loginActivity = new Intent(SettingsActivity.this, FirebaseSignInActivity.class);
                startActivity(loginActivity);
            }
        });
        backupBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V)
            {
                Uri file = Uri.fromFile(new File("/data/data/sg.edu.np.mad.teampk.stufftrek/databases/stufftrekDB.db"));
                UploadTask uploadTask = backupRef.putFile(file);

                // Register observers to listen for when the download is done or if it fails
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        System.out.println("Firebase backup fail");
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        System.out.println("Firebase backup success");
                    }
                });
            }
        });
        loadBackupBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V)
            {
                File localFile = new File("/data/data/sg.edu.np.mad.teampk.stufftrek/databases/stufftrekDB.db");


                backupRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        System.out.println("Firebase load backup success");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        System.out.println("Firebase load backup fail");
                    }
                });
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