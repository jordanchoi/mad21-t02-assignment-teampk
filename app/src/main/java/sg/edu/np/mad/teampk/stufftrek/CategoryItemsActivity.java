package sg.edu.np.mad.teampk.stufftrek;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoryItemsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_items);

        // Receive Intent
        Intent receiveIntent = getIntent();
        String categoryName = receiveIntent.getStringExtra("catName");
        Integer categoryID = receiveIntent.getIntExtra("catID", 0);

        // Set Title in the Actionbar
        ActionBarActivity.abTitle.setText(categoryName);

        ActionBarActivity.rightBtn.setImageResource(R.drawable.ic_more); // for future usage
        ActionBarActivity.rightBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V)
            {
                //To be added
            }
        });


        // Construct DBHandler to retrieve DB information.
        DBHandler db = new DBHandler(this, null, null, 1);

        // Call GetAllItemFromCategory to retrieve all items for a certain category
        ArrayList<Item> itemList = db.GetAllItemFromCategory(categoryID);
        // RV for Items
        RecyclerView itemrv = findViewById(R.id.itemsRV);
        ItemsWithPathAdapter adapter = new ItemsWithPathAdapter(this,itemList);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        itemrv.setLayoutManager(lm);
        itemrv.setAdapter(adapter);
        // Handler for no items found
        TextView noItemTV = findViewById(R.id.noItemsTV);
        if (itemList.size() == 0)
        {
            noItemTV.setText("You have no items created");
        }
        else
        {
            noItemTV.setVisibility(View.GONE);
        }


    }

}