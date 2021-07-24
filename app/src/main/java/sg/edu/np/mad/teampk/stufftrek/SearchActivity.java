package sg.edu.np.mad.teampk.stufftrek;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    ImageButton backBtn;
    TextView searchTitle;
    TextView resultsNumText;
    SearchView searchQueryField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Widgets in the Internal ActionBar
        backBtn = findViewById(R.id.searchBackBtn);

        // Find the widgets in the Internal ActionBar by ID
        searchTitle = findViewById(R.id.searchTitleTV);
        resultsNumText = findViewById(R.id.resultsNumTV);
        searchQueryField = findViewById(R.id.searchSV);

        // Set the texts of the respective widgets
        searchTitle.setText("Search");

        // Set the on click listener for the search view
        searchQueryField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Opens the keyboard to start the search function
                searchQueryField.onActionViewExpanded();
            }
        });

        // if search bar is not empty
        if (searchQueryField != null) {
            searchQueryField.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return false;
                }
            });
        }

        // OnClickListener for back button in the Internal actionbar
        backBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V)
            {
                finish();
            }
        });
    }

    //Searches for items and sets into list for selection
    private void search(String str) {
        // Create a new list to store the search results
        ArrayList<Item> searchList = new ArrayList<>();

        if (!str.isEmpty()) {
            // Construct DBHandler to retrieve DB information.
            DBHandler db = new DBHandler(this, null, null, 1);

            // Call GetAllItemFromContainer to retrieve all items in container
            ArrayList<Item> itemList = db.GetAllItem();

            for (Item i : itemList) {
                // converts string and username to lowercase to avoid mismatch
                if(i.Name.toLowerCase().contains(str.toLowerCase())){
                    searchList.add(i);
                }
            }
        }

        // Set the text for the number of results
        resultsNumText.setText(searchList.size() + " results");

        // Construct new adapter for search results and attaches it to the recycler view
        ItemsWithPathAdapter adp = new ItemsWithPathAdapter(this, searchList);
        RecyclerView itemrv = findViewById(R.id.searchResultsRV);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        itemrv.setLayoutManager(lm);
        itemrv.setAdapter(adp);
    }
}