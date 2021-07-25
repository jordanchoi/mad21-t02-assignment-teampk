package sg.edu.np.mad.teampk.stufftrek;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemsWithPathAdapter extends RecyclerView.Adapter<ItemsWithPathAdapter.ItemsWithPathViewHolder>{

    DBHandler db;
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
        holder.itemId.setText(""+item.getItemID());
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

    public class ItemsWithPathViewHolder extends RecyclerView.ViewHolder implements  View.OnCreateContextMenuListener {
        public TextView itemsNameText;
        public TextView itemsCatText;
        public TextView itemsQtyText;
        public TextView itemsPathText;
        public ImageView itemsImage;
        public ConstraintLayout itemsContainer;
        public TextView itemId;
        int iId;
        public ItemsWithPathViewHolder(@NonNull View itemView) {
            super(itemView);

            itemsNameText = itemView.findViewById(R.id.itemNameTv);
            itemsCatText = itemView.findViewById(R.id.itemCatTv);
            itemsQtyText = itemView.findViewById(R.id.itemQtyTv);
            itemsPathText = itemView.findViewById(R.id.itemPathTv);
            itemsImage = itemView.findViewById(R.id.itemImgIv);
            itemsContainer = itemView.findViewById(R.id.itemsContainer);
            itemId=itemView.findViewById(R.id.itemId);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Edit = menu.add(Menu.NONE, 1, 1, "Edit");
            MenuItem Delete = menu.add(Menu.NONE, 2, 2, "Delete");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);
        }
        //ADD AN ONMENUITEM LISTENER TO EXECUTE COMMANDS ONCLICK OF CONTEXT MENU TASK
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                iId = Integer.parseInt(String.valueOf(itemId.getText()));
                switch (item.getItemId()) {
                    case 1:
                        // codes here to intent
                        db = new DBHandler(context, null, null, 1);
                        Intent updateItemActivity = new Intent(context,UpdateItemActivity.class);
                        Bundle itemInformationBundle = new Bundle();
                        Item i = db.GetItemWithID(iId);
                        itemInformationBundle.putInt("ItemId",iId);
                        if(i.getRoomID()!=null){
                            itemInformationBundle.putInt("RoomID", i.getRoomID());
                            itemInformationBundle.putString("RoomName", db.GetRoomWithID(i.getRoomID()).Name);
                        }
                        if(i.getLocationID()!=null){
                            itemInformationBundle.putInt("LocationID", i.getLocationID());
                        }


                        updateItemActivity.putExtras(itemInformationBundle);
                        context.startActivity(updateItemActivity);
                        break;

                    case 2:
                        //Do stuff
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Confirm Delete").setMessage("You are about to delete a top-level location!\nAll rooms, containers, containers location will be deleted.\nAny items within this location will be unassigned from its location.").setCancelable(false).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                db = new DBHandler(context, null, null, 1);
                                db.DeleteItem(iId);
                                allItemsList.remove(getArrayPosition(iId));
                                notifyDataSetChanged();
                                db.close();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // cancels the dialog
                                dialogInterface.cancel();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                        break;
                }
                return true;
            }
        };

        public int getArrayPosition(int itemId)
        {
            int arrayPos = -1;

            for (int i = 0; i < allItemsList.size(); i++)
            {
                Item it = allItemsList.get(i);
                if (it.getItemID()==itemId)
                {
                    arrayPos = i;
                    break;
                }
            }
            return arrayPos;
        }
    }
}
