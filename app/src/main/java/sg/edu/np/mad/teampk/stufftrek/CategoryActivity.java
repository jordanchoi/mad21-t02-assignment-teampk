package sg.edu.np.mad.teampk.stufftrek;

import androidx.annotation.NonNull;
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

public class CategoryActivity extends ActionBarActivity {
    ConstraintLayout mainLayout;
    TextView categoryTitle;
    TextView categoryDesc;
    ArrayList<Category> categoryList;
    TextView existCatText;
    TextView noCatText;
    RecyclerView rv;

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

        // Set Title in the Actionbar
        ActionBarActivity.abTitle.setText("Manage Categories");

        // Construct DBHandler to retrieve DB information.
        db = new DBHandler(this, null, null, 1);
        // Call GetAllCategories() from DBHandler to retrieve ALL locations.
        categoryList = db.GetAllCategories();
        sortList();

        rv = findViewById(R.id.sharedRv);
        catAdapter = new CategoryAdapter(this, categoryList);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(catAdapter);

        enableSwipeToDeleteAndUndo();

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
                            sortList();
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

    private void enableSwipeToDeleteAndUndo() {
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
}