package sg.edu.np.mad.teampk.stufftrek;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class AllItemsActivity extends ActionBarActivity {
    TextView itemsTitle;
    TextView itemsDesc;
    TextView allItemsText;

    TextView noItemsText;

    ArrayList<Item> allItemsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category); // can do this?

        // Receive Intent
        Intent receiveIntent = getIntent();

        // Set Title in the Actionbar
        ActionBarActivity.abTitle.setText("All Items");

        // Hide the right button as it is not required in this activity. Users not allowed to create items from this activity.
        ActionBarActivity.rightBtn.setVisibility(View.GONE);

        // Get the widgets in the activity by id.
        itemsTitle = findViewById(R.id.sharedPageTitleTV);
        itemsDesc = findViewById(R.id.sharedPageDescTV);
        allItemsText = findViewById(R.id.sharedComponentTV);
        noItemsText = findViewById(R.id.sharedNoItemsTV);
        // Set the texts of the widgets
        itemsTitle.setText("All Items");
        itemsDesc.setText("List and detail of all added items");
        allItemsText.setText("Your Items");

        // Construct DBHandler to retrieve DB information.
        DBHandler db = new DBHandler(this, null, null, 1);
        // Call GetAllLocation() from DBHandler to retrieve ALL locations.
        allItemsList = db.GetAllItem();

        RecyclerView rv = findViewById(R.id.sharedRv);
        ItemsWithPathAdapter itemsAdapter = new ItemsWithPathAdapter(this, allItemsList);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(itemsAdapter);

        if (allItemsList.size() == 0)
        {
            noItemsText.setText("You have no items created");
        }
        else
        {
            noItemsText.setVisibility(View.GONE);
        }

    }
}