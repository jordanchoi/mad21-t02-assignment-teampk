package sg.edu.np.mad.teampk.stufftrek;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ItemsWithPathAdapter extends RecyclerView.Adapter<ItemsWithPathViewHolder>{

    // For layout
    Context context;

    // For the constructor parameter
    ArrayList<Item> allItemsList;

    // Constructor for ItemsWithPathAdapter
    public ItemsWithPathAdapter(Context ctx, ArrayList<Item> iLists)
    {
        context = ctx;
        allItemsList = iLists;
    }

    @NonNull
    @Override
    public ItemsWithPathViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_items_with_path, parent, false);
        return new ItemsWithPathViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsWithPathViewHolder holder, int position) {
        Item item = allItemsList.get(position);
        holder.itemsNameText.setText(item.Name);
        holder.itemsCatText.setText(item.CategoryName);
        holder.itemsQtyText.setText("Qty: " + item.Quantity);
        holder.itemsImage.setImageResource(R.drawable.ic_launcher_foreground); // supposed to be item pic

        String locName = item.LocationName;
        String roomName = item.RoomName;
        String containerName = item.ContainerName;

        if (item.Picture != null)
        {
            holder.itemsImage.setImageBitmap(BitmapFactory.decodeFile(item.Picture));
        }

        if (item.RoomName != null ) {
            if (item.RoomName.length() > 0) {
                locName = locName + " > ";
            }
        }
        else
        {
            roomName = "";
        }
        if (item.ContainerName != null ) {
            if (item.ContainerName.length() > 0 | item.ContainerName == null) {
                roomName = roomName + " > ";
            }
        }
        else
        {
            containerName = "";
        }

        if (item.LocationName == null) {
            if (context instanceof AllItemsActivity || context instanceof SearchActivity)
            {
                holder.itemsPathText.setTextColor(Color.RED);
                holder.itemsPathText.setText("Item is unassigned, no location found.");
            }
            else
            {
                holder.itemsPathText.setText("");
            }
        }
        else
        {
            holder.itemsPathText.setText(locName + roomName + containerName);
        }
    }

    @Override
    public int getItemCount() {
        return allItemsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
