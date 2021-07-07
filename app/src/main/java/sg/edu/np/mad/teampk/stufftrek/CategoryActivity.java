package sg.edu.np.mad.teampk.stufftrek;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class CategoryActivity extends AppCompatActivity {

    ActionBar actionBar;
    View view;
    Toolbar parent;
    ImageButton backBtn;
    ImageButton rightBtn;
    TextView title;

    TextView categoryTitle;
    TextView categoryDesc;
    TextView existCatText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // Receive Intent
        Intent receiveIntent = getIntent();

        // Action Bar Customization
        actionBar = getSupportActionBar();
        // actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7CCBCE")));
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);
        view = getSupportActionBar().getCustomView();

        // Remove the space at the start and end of the customized action bar
        parent =(Toolbar) view.getParent();
        parent.setPadding(0,0,0,0); //for tab otherwise give space in tab
        parent.setContentInsetsAbsolute(0,0);

        // Widgets in the ActionBar
        backBtn = view.findViewById(R.id.backBtn);
        rightBtn = view.findViewById(R.id.rightBtn);
        title = view.findViewById(R.id.abTitleTV);

        // Set Title in the Actionbar
        title.setText("Manage Categories");

        // rightBtn.setImageResource(R.drawable.ic_more); // for future usage
        // OnClickListener for buttons in the actionbar
        backBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V)
            {
                finish();
            }
        });

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
        categoryTitle = findViewById(R.id.catPageTitleTV);
        categoryDesc = findViewById(R.id.catPageDescTV);
        existCatText = findViewById(R.id.existingCatTV);

        // Set the texts of the widgets
        categoryTitle.setText("Categories");
        categoryDesc.setText("Manage your categories and assign a category item for better organization.");
        existCatText.setText("Existing Categories");
    }
}