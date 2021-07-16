package sg.edu.np.mad.teampk.stufftrek;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Locale;

public class CategoryActivity extends ActionBarActivity {
    TextView categoryTitle;
    TextView categoryDesc;
    ArrayList<Category> categoryList;
    TextView existCatText;
    TextView noCatText;

    TextView createTitle;
    EditText createField;
    TextView errorMsgText;
    ImageButton dialogCancelBtn;
    Button dialogAddBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // Receive Intent
        Intent receiveIntent = getIntent();

        // Set Title in the Actionbar
        ActionBarActivity.abTitle.setText("Manage Categories");

        // Construct DBHandler to retrieve DB information.
        DBHandler db = new DBHandler(this, null, null, 1);
        // Call GetAllCategories() from DBHandler to retrieve ALL locations.
        categoryList = db.GetAllCategories();

        RecyclerView rv = findViewById(R.id.sharedRv);
        CategoryAdapter catAdapter = new CategoryAdapter(this, categoryList);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(catAdapter);

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
        noCatText = findViewById(R.id.sharedNoItemsTV);

        // Set the texts of the widgets
        categoryTitle.setText("Categories");
        categoryDesc.setText("Manage your categories and assign a category to an item for better organization.");
        existCatText.setText("Existing Categories");

        if (categoryList.size() == 0)
        {
            noCatText.setText("You do not have any categories.");
            noCatText.setVisibility(View.VISIBLE);
        }
        else
        {
            noCatText.setVisibility(View.GONE);
        }

        ActionBarActivity.rightBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                BottomSheetDialog dialog = new BottomSheetDialog(CategoryActivity.this);
                dialog.setContentView(LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_create, findViewById(R.id.content), false));

                // Get the respective items in the view
                createTitle = dialog.findViewById(R.id.dialogTitleTV);
                createField = dialog.findViewById(R.id.dialogFieldET);
                dialogCancelBtn = dialog.findViewById(R.id.dialogCancelBtn);
                dialogAddBtn = dialog.findViewById(R.id.dialogAddBtn);

                // Set the respective texts of the items in the view
                createTitle.setText("Category Name: ");
                dialogAddBtn.setText("Add Category");

                dialogCancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

                dialogAddBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String categoryName = createField.getText().toString();

                        if(categoryName.length() == 0)
                        {
                            errorMsgText = dialog.findViewById(R.id.errorMsgTV);
                            errorMsgText.setText("Category name cannot be empty!");
                            errorMsgText.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            if (categoryList.size() == 0)
                            {
                                noCatText.setVisibility(View.GONE);
                            }

                            Category cat = new Category(categoryName);
                            cat.setCategoryID(db.AddCategory(cat));
                            categoryList.add(cat);
                            catAdapter.notifyDataSetChanged();
                            dialog.cancel();
                        }
                    }
                });

                Window window = dialog.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialog.show();
            }
        });
    }
}