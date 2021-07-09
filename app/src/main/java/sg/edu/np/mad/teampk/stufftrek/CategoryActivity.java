package sg.edu.np.mad.teampk.stufftrek;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class CategoryActivity extends ActionBarActivity {
    TextView categoryTitle;
    TextView categoryDesc;
    TextView existCatText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // Receive Intent
        Intent receiveIntent = getIntent();

        // Set Title in the Actionbar
        ActionBarActivity.abTitle.setText("Manage Categories");

        // rightBtn.setImageResource(R.drawable.ic_more); // for future usage

        // OnClickListener for the right button of the ActionBar
        rightBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V)
            {
                /* TO REMOVE THE COMMENT WHEN CreateCategoryActivity is created.
                Intent createLocationActivity = new Intent(LocationActivity.this, CreateCategoryActivity.class);
                startActivity(createCategoryActivity);
                */
            }
        });

        // Get the widgets in the activity by id.
        categoryTitle = findViewById(R.id.sharedPageTitleTV);
        categoryDesc = findViewById(R.id.sharedPageDescTV);
        existCatText = findViewById(R.id.sharedComponentTV);

        // Set the texts of the widgets
        categoryTitle.setText("Categories");
        categoryDesc.setText("Manage your categories and assign a category to an item for better organization.");
        existCatText.setText("Existing Categories");
    }
}