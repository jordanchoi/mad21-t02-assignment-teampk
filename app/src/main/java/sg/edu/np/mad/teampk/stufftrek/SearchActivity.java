package sg.edu.np.mad.teampk.stufftrek;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {
    ImageButton backBtn;
    TextView searchTitle;
    TextView resultsNumText;
    EditText searchQueryField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Widgets in the Internal ActionBar
        backBtn = findViewById(R.id.searchBackBtn);

        // Find the widgets in the Internal ActionBar by ID
        searchTitle = findViewById(R.id.searchTitleTV);
        resultsNumText = findViewById(R.id.resultsNumTV);
        searchQueryField = findViewById(R.id.searchET);

        // Set the texts of the respective widgets
        searchTitle.setText("Search");
        resultsNumText.setText("");
        searchQueryField.setText("Enter Item Name or Category Name");

        // OnClickListener for back button in the Internal actionbar
        backBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V)
            {
                finish();
            }
        });
    }
}