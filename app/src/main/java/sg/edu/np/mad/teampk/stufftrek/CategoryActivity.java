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
import android.graphics.Color;
import android.os.Bundle;
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
import java.util.Iterator;

public class CategoryActivity extends AppCompatActivity {
    TextView categoryTitle;
    TextView categoryDesc;
    ArrayList<Category> categoryList;
    TextView existCatText;
    TextView noCatText;
    RecyclerView rv;
    ConstraintLayout mainLayout;

    TextView createTitle;
    TextView editTitle;
    EditText createField;
    EditText editField;
    TextView errorMsgText;
    ImageButton dialogCancelBtn;
    Button dialogAddBtn;
    Button dialogEditBtn;

    CategoryAdapter catAdapter = null;
    DBHandler db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_page);

        // Toolbar for LocationActivity
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);

        ActionBar tb = getSupportActionBar();
        if(tb != null) {
            tb.setHomeAsUpIndicator(R.drawable.ic_back);
            tb.setDisplayHomeAsUpEnabled(true);
            tb.setTitle("Manage Categories");
        }

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
        // Sort the categoryList by alphabetical order
        sortList();

        rv = findViewById(R.id.sharedRv);
        catAdapter = new CategoryAdapter(this, categoryList);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(catAdapter);

        // initializes the swipe to delete feature
        enableSwipeToDelete();
        enableSwipeToEdit();

        // Get the widgets in the activity by id.
        categoryTitle = findViewById(R.id.sharedPageTitleTv);
        categoryDesc = findViewById(R.id.sharedPageDescTv);
        existCatText = findViewById(R.id.sharedComponentTv);
        noCatText = findViewById(R.id.sharedNoItemsTv);
        mainLayout = findViewById(R.id.categoryMainLayout);

        // Set the texts of the widgets
        categoryTitle.setText(R.string.categories);
        categoryDesc.setText(R.string.manageCat);
        existCatText.setText(R.string.existingCat);

        if (categoryList.size() == 0)
        {
            noCatText.setText(R.string.no_category);
            noCatText.setVisibility(View.VISIBLE);
        }
        else
        {
            noCatText.setVisibility(View.GONE);
        }
    }

    // Swipe to delete function
    private void enableSwipeToDelete() {
        CategorySwipeDeleteCallback swipeToDeleteCallback = new CategorySwipeDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                // Gets the position of the deleted item
                final int position = viewHolder.getAdapterPosition();

                final Category item = categoryList.get(position);

                // Shows confirmation box if deleted category has items tagged to it
                if(item.getCount() >= 1) {
                    new AlertDialog.Builder(CategoryActivity.this)
                            .setTitle("Delete Category")
                            .setMessage("The category \"" + item.Name + "\" has " + item.getCount() + " item(s) tagged to it.\nAre you sure you want to delete it?")
                            .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                                catAdapter.removeItem(position);
                                sortList();
                                catAdapter.notifyDataSetChanged();

                                Snackbar snackbar = Snackbar.make(mainLayout, item.Name + " was removed from the list.", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    catAdapter.notifyDataSetChanged();
                // Deleted category has no items tagged to it
                } else {
                    // Deletes category from database
                    catAdapter.removeItem(position);
                    // Sort the categoryList by alphabetical order
                    sortList();
                    catAdapter.notifyDataSetChanged();

                    Snackbar snackbar = Snackbar.make(mainLayout, item.Name + " was removed from the list.", Snackbar.LENGTH_LONG);
                    // Adds a undo button in case user accidentally deletes
                    snackbar.setAction("UNDO", view -> {
                        // Re-add the deleted item to the database
                        catAdapter.restoreItem(item);
                        // Sort the categoryList by alphabetical order
                        sortList();
                        catAdapter.notifyDataSetChanged();
                        rv.scrollToPosition(position);
                    });

                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();
                }
            }
        };

        // Attaches the swipe to delete function to the recycler view
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(rv);
    }

    // Swipe to edit function
    private void enableSwipeToEdit() {
        CategorySwipeEditCallback swipeToEditCallback = new CategorySwipeEditCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                // Gets the position of the edited item
                final int position = viewHolder.getAdapterPosition();

                final Category item = categoryList.get(position);

                BottomSheetDialog dialog = new BottomSheetDialog(CategoryActivity.this, R.style.BottomSheetStyle);
                dialog.setContentView(R.layout.dialog_create);

                // Get the respective items in the view
                editTitle = dialog.findViewById(R.id.dialogTitleTV);
                editField = dialog.findViewById(R.id.dialogFieldET);
                dialogCancelBtn = dialog.findViewById(R.id.dialogCancelBtn);
                dialogEditBtn = dialog.findViewById(R.id.dialogAddBtn);

                // Set the respective texts of the items in the view
                editTitle.setText(R.string.category_name);
                editField.setText(item.Name);
                dialogEditBtn.setText(R.string.category_update);

                dialogCancelBtn.setOnClickListener(view -> dialog.cancel());

                dialogEditBtn.setOnClickListener(view -> {
                    // retrieve the category name that the user entered
                    String categoryName = editField.getText().toString();

                    // If user did not enter anything
                    if(categoryName.length() == 0)
                    {
                        errorMsgText = dialog.findViewById(R.id.errorMsgTV);
                        errorMsgText.setText(R.string.cat_empty_error);
                        errorMsgText.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        item.Name = categoryName;
                        db.UpdateCategory(item);
                        sortList();
                        catAdapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                });

                Window window = dialog.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialog.show();
                catAdapter.notifyDataSetChanged();
            }
        };

        // Attaches the swipe to edit function to the recycler view
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToEditCallback);
        itemTouchhelper.attachToRecyclerView(rv);
    }

    // Sort the categoryList by alphabetical order
    private void sortList() {
        Collections.sort(categoryList, (lhs, rhs) -> lhs.Name.toLowerCase().compareTo(rhs.Name.toLowerCase()));
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
            // Add Button is selected, creates new BottomSheetDialog to allow users to create a new Category
            case R.id.mAdd:
                BottomSheetDialog dialog = new BottomSheetDialog(CategoryActivity.this, R.style.BottomSheetStyle);
                dialog.setContentView(R.layout.dialog_create);

                // Get the respective items in the view
                createTitle = dialog.findViewById(R.id.dialogTitleTV);
                createField = dialog.findViewById(R.id.dialogFieldET);
                dialogCancelBtn = dialog.findViewById(R.id.dialogCancelBtn);
                dialogAddBtn = dialog.findViewById(R.id.dialogAddBtn);

                // Set the respective texts of the items in the view
                createTitle.setText(R.string.category_name);
                dialogAddBtn.setText(R.string.category_add);

                dialogCancelBtn.setOnClickListener(view -> dialog.cancel());

                dialogAddBtn.setOnClickListener(view -> {
                    // retrieve the category name that the user entered
                    String categoryName = createField.getText().toString();

                    // If user did not enter anything
                    if(categoryName.length() == 0)
                    {
                        errorMsgText = dialog.findViewById(R.id.errorMsgTV);
                        errorMsgText.setText(R.string.cat_empty_error);
                        errorMsgText.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        // remove the no categories text that was shown before category was created
                        if (categoryList.size() == 0)
                        {
                            noCatText.setVisibility(View.GONE);
                        }

                        // Creates a new category class and adds it to the database
                        Category cat = new Category(categoryName);
                        cat.setCategoryID(db.AddCategory(cat));
                        categoryList.add(cat);
                        sortList();
                        catAdapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                });

                Window window = dialog.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialog.show();
            }

        return super.onOptionsItemSelected(item);
    }
}