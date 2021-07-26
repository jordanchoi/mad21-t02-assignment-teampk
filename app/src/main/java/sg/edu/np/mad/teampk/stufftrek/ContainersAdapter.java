package sg.edu.np.mad.teampk.stufftrek;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContainersAdapter extends RecyclerView.Adapter<ContainersAdapter.ContainersViewHolder>{

    Context context;
    ArrayList<Container> containerList;
    int locationId;
    int roomId;

    public ContainersAdapter(Context c, ArrayList<Container> cList, int locId, int rmId)
    {
        context = c;
        containerList = cList;
        locationId = locId;
        roomId = rmId;
    }

    @NonNull
    @Override
    public ContainersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_containers, parent, false);

        return new ContainersViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ContainersViewHolder holder, int position) {
        Container container = containerList.get(position);
        holder.containerName.setText(container.Name);
        // hidden for manipulation from contextual menu
        holder.containerId.setText("" + container.getContainerID());
        // code to change the container imageButton image resource
        if (container.Picture != null)
        {
            holder.containerBtn.setImageBitmap(BitmapFactory.decodeFile(container.Picture));
        }
        // Intent to respective containerActivity when imageButton is clicked.
        holder.containerBtn.setOnClickListener(v -> {
            Intent itemsActivity = new Intent(context, ItemsActivity.class);
            Bundle fkInfo = new Bundle();
            fkInfo.putInt("ContainerID", container.getContainerID());
            fkInfo.putString("ContainerName", container.Name);
            fkInfo.putInt("LocationID", locationId);
            fkInfo.putInt("RoomID", roomId);
            fkInfo.putInt("ContainerCatID", container.getContainerCategoryID());
            itemsActivity.putExtras(fkInfo);
            context.startActivity(itemsActivity);
        });

    }

    @Override
    public int getItemCount() {
        return containerList.size();
    }

    public class ContainersViewHolder extends RecyclerView.ViewHolder implements  View.OnCreateContextMenuListener {

        TextView containerName;
        ImageButton containerBtn;
        TextView containerId;
        int cId;

        public ContainersViewHolder(@NonNull View itemView) {
            super(itemView);

            containerName = itemView.findViewById(R.id.containerNameTv);
            containerBtn = itemView.findViewById(R.id.containerIb);
            containerId=itemView.findViewById(R.id.containerId);
            containerBtn.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Edit = menu.add(Menu.NONE, 1, 1, "Edit");
            MenuItem Delete = menu.add(Menu.NONE, 2, 2, "Delete");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);
        }
        //OnMenuItemClick Listener to handle each of the selection for the context menu
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                cId = Integer.parseInt(String.valueOf(containerId.getText()));
                switch (item.getItemId()) {
                    case 1:
                        // codes here to intent
                        Intent updateContainerActivity = new Intent(context, UpdateContainerActivity.class);
                        Bundle containerInformation = new Bundle();

                        containerInformation.putInt("ContainerId",cId);
                        containerInformation.putInt("RoomId", roomId);

                        updateContainerActivity.putExtras(containerInformation);
                        context.startActivity(updateContainerActivity);

                        break;

                    case 2:
                        //Do stuff
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Confirm Delete").setMessage("You are about to delete a top-level location!\nAll rooms, containers, containers location will be deleted.\nAny items within this location will be unassigned from its location.").setCancelable(false).setPositiveButton("Delete", (dialogInterface, i) -> {
                            DBHandler db = new DBHandler(context, null, null, 1);
                            db.DeleteContainer(cId);
                            containerList.remove(getArrayPosition(cId));
                            notifyDataSetChanged();
                            db.close();
                        }).setNegativeButton("Cancel", (dialogInterface, i) -> {
                            // cancels the dialog
                            dialogInterface.cancel();
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                        break;
                }
                return true;
            }
        };



        public int getArrayPosition(int containerId)
        {
            int arrayPos = -1;

            for (int i = 0; i < containerList.size(); i++)
            {
                Container c = containerList.get(i);
                if (c.getContainerID() == containerId)
                {
                    arrayPos = i;
                    break;
                }
            }
            return arrayPos;
        }
    }

}
