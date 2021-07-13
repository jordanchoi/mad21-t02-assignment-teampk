package sg.edu.np.mad.teampk.stufftrek;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import static sg.edu.np.mad.teampk.stufftrek.ActionBarActivity.rightBtn;

public class ItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        // Receive Intent
        Intent receiveIntent = getIntent();
        String ContainerName = receiveIntent.getStringExtra("ContainerName");
        Integer ContainerID = receiveIntent.getIntExtra("ContainerID",0);

        // Set Title in the Actionbar
        ActionBarActivity.abTitle.setText(ContainerName);

        rightBtn.setImageResource(R.drawable.ic_more); // for future usage
        rightBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V)
            {
                /* TO REMOVE THE COMMENT WHEN CreateLocationActivity is created.
                Intent createLocationActivity = new Intent(LocationActivity.this, CreateLocationActivity.class);
                startActivity(createLocationActivity);
                */
            }
        });


        // Construct DBHandler to retrieve DB information.
        DBHandler db = new DBHandler(this, null, null, 1);

        // Call GetAllItemFromContainer to retrieve all items in container
        ArrayList<Item> itemList = db.GetAllItemFromContainer(ContainerID);
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