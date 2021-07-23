package sg.edu.np.mad.teampk.stufftrek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class SettingsActivity extends AppCompatActivity {

    Button signOutBtn;
    Button backupBtn;
    Button loadBackupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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

            }
        });
        loadBackupBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V)
            {

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