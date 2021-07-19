package sg.edu.np.mad.teampk.stufftrek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;

public class CategoryActivity extends AppCompatActivity {
    TextView categoryTitle;
    TextView categoryDesc;
    ArrayList<Category> categoryList;
    TextView existCatText;
    TextView noCatText;
    RecyclerView rv;
    ConstraintLayout mainLayout;

    TextView createTitle;
    EditText createField;
    TextView errorMsgText;
    ImageButton dialogCancelBtn;
    Button dialogAddBtn;

    CategoryAdapter catAdapter = null;
    DBHandler db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // Receive Intent
        Intent receiveIntent = getIntent();

        // Toolbar for LocationActivity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        ActionBar tb = getSupportActionBar();
        tb.setHomeAsUpIndicator(R.drawable.ic_back);
        tb.setDisplayHomeAsUpEnabled(true);
        tb.setTitle("Manage Categories");

        // Construct DBHandler to retrieve DB information.
        db = new DBHandler(this, null, null, 1);
        // Call GetAllCategories() from DBHandler to retrieve ALL categories.
        categoryList = db.GetAllCategories();
        // Loop through categoryList to remove the "Unassigned" field
        for (Iterator<Category> c = categoryList.iterator(); c.hasNext(); ) {
            Category value = c.next();
            if (value.Name.equals("Unassigned")) {
                c.remove();
            }
        }
        // Sorts the list in alphabetical order
        sortList();

        rv = findViewById(R.id.sharedRv);
        catAdapter = new CategoryAdapter(this, categoryList);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(catAdapter);

        // initializes the swipe to delete feature
        enableSwipeToDelete();

        // Get the widgets in the activity by id.
        categoryTitle = findViewById(R.id.sharedPageTitleTV);
        categoryDesc = findViewById(R.id.sharedPageDescTV);
        existCatText = findViewById(R.id.sharedComponentTV);
        noCatText = findViewById(R.id.sharedNoItemsTV);
        mainLayout = findViewById(R.id.categoryMainLayout);

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
    }

    private void enableSwipeToDelete() {
        CategorySwipeDeleteCallback swipeToDeleteCallback = new CategorySwipeDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                final int position = viewHolder.getAdapterPosition();
                final Category item = categoryList.get(position);

                if(item.Name.equals("Unassigned")) {
                    Snackbar snackbar = Snackbar
                            .make(mainLayout, item.Name + " cannot be deleted.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    catAdapter.notifyDataSetChanged();
                } else if(item.getCount() >= 1) {
                    new AlertDialog.Builder(CategoryActivity.this)
                            .setTitle("Delete Category")
                            .setMessage("The category \"" + item.Name + "\" has " + item.getCount() + " items tagged to it.\nAre you sure you want to delete it?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    db.DeleteCategory(item.getCategoryID());
                                    catAdapter.removeItem(position);
                                    sortList();
                                    catAdapter.notifyDataSetChanged();

                                    Snackbar snackbar = Snackbar.make(mainLayout, item.Name + " was removed from the list.", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    catAdapter.notifyDataSetChanged();
                } else {
                    db.DeleteCategory(item.getCategoryID());
                    catAdapter.removeItem(position);
                    sortList();
                    catAdapter.notifyDataSetChanged();

                    Snackbar snackbar = Snackbar.make(mainLayout, item.Name + " was removed from the list.", Snackbar.LENGTH_LONG);
                    snackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            catAdapter.restoreItem(item);
                            sortList();
                            catAdapter.notifyDataSetChanged();
                            rv.scrollToPosition(position);
                        }
                    });

                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();
                }
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(rv);
    }

    private void sortList() {
        Collections.sort(categoryList, new Comparator<Category>() {
            @Override
            public int compare(Category lhs, Category rhs) {
                return lhs.Name.compareTo(rhs.Name);
            }
        });
    }

    // Inflate Menu for LocationActivity into the ActionBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu_add, menu);
        return true;
    }

    // Actionbar Menu Items
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            // Add Button is selected, creates new BottomSheetDialog to allow users to create a new Location
            case R.id.mAdd:
                BottomSheetDialog dialog = new BottomSheetDialog(CategoryActivity.this);
                dialog.setContentView(R.layout.dialog_create);

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

        return super.onOptionsItemSelected(item);
    }
}